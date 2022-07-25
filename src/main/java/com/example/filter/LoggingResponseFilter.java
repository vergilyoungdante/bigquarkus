package com.example.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.filter
 * @Author: vergil young
 * @CreateTime: 2022-07-25  21:55
 * @Description: response log
 */
@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {

    Logger responseLogger = LoggerFactory.getLogger("response");
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseLogger.info(responseContext.getStatus() + " " + new ObjectMapper().writeValueAsString(responseContext.getEntity()));
        for (String key : responseContext.getHeaders().keySet()) {
            responseLogger.info("[REST Logging] " +key + ": " + responseContext.getHeaders().get(key));
        }
    }
}
