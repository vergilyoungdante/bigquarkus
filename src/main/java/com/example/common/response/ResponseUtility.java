package com.example.common.response;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.common.response
 * @Author: vergil young
 * @CreateTime: 2022-07-20  14:10
 * @Description: 公共response
 */
public class ResponseUtility {

    private ResponseUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static Response createdOK(Object object) {
        return Response.ok(object).status(CREATED).build();
    }

    public static Response getOK(Object object) {
        return Response.ok(object).status(OK).build();
    }

    public static Response deleted(Boolean flag) {
        if (Boolean.TRUE.equals(flag)) {
            return Response.ok().status(NO_CONTENT).build();
        } else {
            return Response.serverError().build();
        }
    }
}
