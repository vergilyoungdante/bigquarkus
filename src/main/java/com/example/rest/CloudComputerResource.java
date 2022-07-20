package com.example.rest;

import com.example.common.response.ResponseUtility;
import com.example.domain.CloudComputer;
import com.example.persistence.CloudComputerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


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

    @PUT
    @Transactional
    public Response save(CloudComputer cloudComputer) {
        if (cloudComputer == null || cloudComputer.getId() != null) {
            throw new WebApplicationException("id在请求中设置错误", 422);
        }
        cloudComputerRepository.persist(cloudComputer);
        return ResponseUtility.createdOK(cloudComputer);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id")Long id) {
        if (id == null) {
            throw new WebApplicationException("id为空", 422);
        }
        Boolean flag = cloudComputerRepository.deleteById(id);

        return ResponseUtility.deleted(flag);
    }


}
