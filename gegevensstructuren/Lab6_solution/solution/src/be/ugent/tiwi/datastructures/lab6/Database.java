package be.ugent.tiwi.datastructures.lab6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author sleroux
 */
public class Database {

    protected RandomAccessFile file;

    public class Record {

        public int id;
        public String name;
        public String email;
        public String username;

        @Override
        public String toString() {
            return "Record{" + "id=" + id + ", name=" + name + ", email=" + email + ", username=" + username + '}';
        }

    }

    public Database(String filename) throws FileNotFoundException {
        file = new RandomAccessFile(filename, "r");
    }

    protected Record getRecordFromLine(String line) {
        String[] contents = line.split(",");
        Record r = new Record();
        r.id = Integer.parseInt(contents[0]);
        r.name = contents[1] + " " + contents[2] + " " + contents[3] + " " + contents[4];
        r.email = contents[6];
        r.username = contents[35];
        return r;
    }

    public Record findById(int value) throws IOException {
        file.seek(0l);
        String line = file.readLine();
        while (line != null) {
            Record r = getRecordFromLine(line);
            if (r.id == value)
                return r;
            line = file.readLine();
        }
        
        return null;
    }
}
