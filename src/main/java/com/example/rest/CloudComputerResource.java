package com.example.rest;

import com.example.common.page.MyPage;
import com.example.common.response.ResponseUtility;
import com.example.domain.CloudComputer;
import com.example.persistence.CloudComputerRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;


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
    CloudComputerRepository cloudComputerRepository;

    @GET
    @Path("count")
    @Transactional
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
    public Response delete(@PathParam("id") Long id) {
        if (id == null) {
            throw new WebApplicationException("id为空", 422);
        }
        Boolean flag = cloudComputerRepository.deleteById(id);

        return ResponseUtility.deleted(flag);
    }

    @POST
    @Transactional
    public Response change(CloudComputer cloudComputer) {
        if (cloudComputer == null || cloudComputer.getId() == null) {
            throw new WebApplicationException("id为空", 422);
        }
        CloudComputer change = cloudComputerRepository.findById(cloudComputer.getId());
        change.setCost(cloudComputer.getCost());
        change.setCpuPower(cloudComputer.getCpuPower());
        change.setMemory(cloudComputer.getMemory());
        change.setName(cloudComputer.getName());
        change.setNetworkBandwidth(cloudComputer.getNetworkBandwidth());

        return ResponseUtility.getOK(change);
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response getSingle(@PathParam("id") Long id) {
        Optional<CloudComputer> optional = cloudComputerRepository.findByIdOptional(id);
        if (optional.isPresent()) {
            return ResponseUtility.getOK(optional.get());
        }

        throw new WebApplicationException("没有这个电脑", 422);
    }

    @POST
    @Path("page")
    @Transactional
    public Response getPage(@QueryParam("index") Integer index,@QueryParam("size") Integer size,CloudComputer cloudComputer){

        MyPage<CloudComputer> result = cloudComputerRepository.JpaSpecificationPage(cloudComputer,new Page(index,size));

        return ResponseUtility.getOK(result);
    }
}
