package be.ugent.devops.services.logic.http;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class AuthCheckerFilter {

    private static final String KEY_HEADER = "X-SECURE-KEY";

    @ConfigProperty(name = "secure.endpoints", defaultValue="false")
    boolean secureEndpointsEnabled;

    @ConfigProperty(name = "secure.key", defaultValue = "default-key")
    String secureKey;

    @ServerRequestFilter(preMatching = true)
    public void allowFilter(ContainerRequestContext requestContext) {
        if (secureEndpointsEnabled && !secureKey.equals(requestContext.getHeaderString(KEY_HEADER))) {
            throw new ForbiddenException();
        }
    }

}