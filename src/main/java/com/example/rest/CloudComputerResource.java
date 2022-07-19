package com.example.rest;

import com.example.domain.CloudComputer;
import com.example.persistence.CloudComputerRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.rest
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:47
 * @Description: TODO
 */
@Path("computer")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CloudComputerResource {

    @Inject
    private CloudComputerRepository cloudComputerRepository;

    @GET
    public Uni<Long> count(){
        return cloudComputerRepository.count();
    }

    @POST
    public Uni<Response> save(CloudComputer cloudComputer){
        if(cloudComputer==null||cloudComputer.getId()!=null){
            throw new WebApplicationException("id在请求中设置错误", 422);
        }
        //这块怎么存不上呀？？？
        return cloudComputerRepository.persist(cloudComputer)
                .replaceWith(Response.ok(cloudComputer)
                        .status(CREATED)::build);
    }


}
