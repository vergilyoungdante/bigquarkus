package com.example.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.filter
 * @Author: vergil young
 * @CreateTime: 2022-07-25  20:04
 * @Description: 打印request日志
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter {
    Logger requestLogger = LoggerFactory.getLogger("request");
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        requestLogger.info(crc.getMethod() + " " + crc.getUriInfo().getAbsolutePath());
        for (String key : crc.getHeaders().keySet()) {
            requestLogger.info("[REST Logging] " +key + ": " + crc.getHeaders().get(key));
        }
    }
}
