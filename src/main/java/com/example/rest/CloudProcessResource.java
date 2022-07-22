package com.example.rest;

import com.example.common.page.MyPage;
import com.example.common.response.ResponseUtility;
import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import com.example.persistence.CloudProcessRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.rest
 * @Author: vergil young
 * @CreateTime: 2022-07-20  20:19
 * @Description: TODO
 */
@Path("process")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CloudProcessResource {

    @Inject
    CloudProcessRepository cloudProcessRepository;

    @GET
    @Path("count")
    @Transactional
    public Long count() {
        return cloudProcessRepository.count();
    }

    @PUT
    @Transactional
    public Response save(CloudProcess cloudProcess) {
        if (cloudProcess == null || cloudProcess.getId() != null) {
            throw new WebApplicationException("id在请求中设置错误", 422);
        }
        cloudProcessRepository.persist(cloudProcess);
        return ResponseUtility.createdOK(cloudProcess);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        if (id == null) {
            throw new WebApplicationException("id为空", 422);
        }
        Boolean flag = cloudProcessRepository.deleteById(id);

        return ResponseUtility.deleted(flag);
    }

    @POST
    @Transactional
    public Response change(CloudProcess cloudProcess) {
        if (cloudProcess == null || cloudProcess.getId() == null) {
            throw new WebApplicationException("id为空", 422);
        }
        CloudProcess change = cloudProcessRepository.findById(cloudProcess.getId());
        change.setRequiredCpuPower(cloudProcess.getRequiredCpuPower());
        change.setRequiredMemory(cloudProcess.getRequiredMemory());
        change.setName(cloudProcess.getName());
        change.setRequiredNetworkBandwidth(cloudProcess.getRequiredNetworkBandwidth());

        return ResponseUtility.getOK(change);
    }

    @GET
    @Path("{id}")
    @Transactional
    public Response getSingle(@PathParam("id") Long id) {
        Optional<CloudProcess> optional = cloudProcessRepository.findByIdOptional(id);
        if (optional.isPresent()) {
            return ResponseUtility.getOK(optional.get());
        }

        throw new WebApplicationException("没有这个应用", 422);
    }

    @POST
    @Path("page")
    @Transactional
    public Response getPage(@QueryParam("index") Integer index, @QueryParam("size") Integer size, CloudProcess cloudProcess){

        MyPage<CloudProcess> result = cloudProcessRepository.JpaSpecificationPage(cloudProcess,new Page(index,size));

        return ResponseUtility.getOK(result);
    }
}
