package com.example.filter;

import io.quarkus.vertx.http.runtime.filters.Filters;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.filter
 * @Author: vergil young
 * @CreateTime: 2022-07-25  14:44
 * @Description: 自定义filter实现请求log打印，功能不如quarkus自带配置好用
 */
@ApplicationScoped
public class RequestFilter {

    Logger requestLogger = LoggerFactory.getLogger(RequestFilter.class);

    public void filters(@Observes Filters filters) {
        filters.register(
                routingContext -> {
                    /**filter自定义**/
                    HttpServerRequest request = routingContext.request();
                    requestLogger.info("REQUEST: url=>{},httpMethod=>{},ip=>{},httpParams=>{}",
                            request.uri(), request.method(), request.remoteAddress(), buildHttpParams(request));
                    //没这句话会卡死整个filter
                    routingContext.next();
                },10
        );
    }

    private String buildHttpParams(HttpServerRequest req){
        StringBuffer paramBuf = new StringBuffer();
        paramBuf.append("{");
        MultiMap params = req.params();
        for(String name:params.names()){
            List<String> list = params.getAll(name);
            paramBuf.append(name).append(":");
            if(list.size()==1){
                paramBuf.append(list.size());
            }else {
                list.forEach(paramBuf::append);
            }
        }
        paramBuf.append("}");
        return new String(paramBuf);
    }
}
