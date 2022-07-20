package com.example.rest;

import com.example.persistence.CloudComputerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;


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
    @Path("count")
    public Long count() {
        return cloudComputerRepository.count();
    }

//    @PUT
//    public Uni<Response> save(CloudComputer cloudComputer) {
//        if (cloudComputer == null || cloudComputer.getId() != null) {
//            throw new WebApplicationException("id在请求中设置错误", 422);
//        }
//
//        return cloudComputerRepository.persistAndFlush(cloudComputer)
//                .replaceWith(Response.ok(cloudComputer)
//                        .status(CREATED)::build);
//    }
//
//    @DELETE
//    public Uni<Response> delete(Long id) {
//        if (id == null) {
//            throw new WebApplicationException("id为空", 422);
//        }
//        return cloudComputerRepository.deleteById(id)
//                .replaceWith(Response.ok()
//                        .status(ACCEPTED)::build);
//    }


}
