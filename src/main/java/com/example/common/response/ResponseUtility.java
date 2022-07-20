package com.example.common.response;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.common.response
 * @Author: vergil young
 * @CreateTime: 2022-07-20  14:10
 * @Description: TODO
 */
public class ResponseUtility {

    public static Response createdOK(Object object){
        return Response.ok(object).status(CREATED).build();
    }

    public static Response deleted(Boolean flag){
        if(Boolean.TRUE.equals(flag)) {
            return Response.ok().status(NO_CONTENT).build();
        }else {
            return Response.serverError().build();
        }
    }
}
