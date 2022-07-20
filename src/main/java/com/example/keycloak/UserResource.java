package com.example.keycloak;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Map;

@Path("users")
public class UserResource {

    //这块不知道为什么json解析报错，以后要拆成微服务
    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    Logger log;

    @LoggerName("test")
    Logger testLoggger;

    @LoggerName("kkk")
    Logger kkkLoggger;

    @RolesAllowed("niuma_user")
    @GET
    @Path("me")
    public Map me() {
        return Map.of("username", securityIdentity.getPrincipal().getName());
    }

    //这块不知道为什么json解析报错，以后要拆成微服务
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("all")
    @RolesAllowed("niuma_user")
    public String allInfo() {
        return jwt.toString();
    }

    @GET
    @Path("admin")
    @RolesAllowed("niuma_user")
    public Map name() {
        return Map.of("subject", jwt.getSubject(),
                "preferred_username", jwt.getClaim("preferred_username"));
    }

    @Inject
    UserInfo userInfo;

    @GET
    @Path("info")
    @RolesAllowed("niuma_user")
    public Map<String, String> info() {
        return Map.of("sub", userInfo.getString("sub"),
                "email", userInfo.getString("email"));
    }


}
