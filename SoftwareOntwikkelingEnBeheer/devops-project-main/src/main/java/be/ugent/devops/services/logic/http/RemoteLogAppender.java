package be.ugent.devops.services.logic.http;


import com.google.common.collect.EvictingQueue;
import io.quarkus.logging.LoggingFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Path("/logs")
@LoggingFilter(name = "remote-log-filter")
public class RemoteLogAppender implements Filter {

    private static final int BUFFER_SIZE = 500;
    private static final int STACKTRACE_DEPTH = 3;
    private static final EvictingQueue<String> buffer = EvictingQueue.create(BUFFER_SIZE);

    @GET
    public List<String> fetchLogs() {
        List<String> output = null;
        synchronized (buffer) {
            output = List.copyOf(buffer);
            buffer.clear();
        }
        return output;
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        synchronized (buffer) {
            var messageFormat = String.format("%s %s [%s] %s", record.getInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), record.getLevel(), record.getLoggerName(), record.getMessage());
            buffer.add(String.format(messageFormat, record.getParameters()));
            var throwable = record.getThrown();
            if (throwable != null) {
                buffer.add(throwable.getClass().getName() + ": " + throwable.getMessage());
                if (throwable.getStackTrace().length > 0) {
                    var stacktrace = throwable.getStackTrace();
                    for (int i = 0; i < STACKTRACE_DEPTH; i++) {
                        buffer.add("\t" + stacktrace[i].toString());
                    }
                }
            }
        }
        return true;
    }
}
