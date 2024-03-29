package com.example.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.interceptor
 * @Author: vergil young
 * @CreateTime: 2022-07-25  21:40
 * @Description: 这里能打出body里的东西，属实不易
 */
@LogEvent
@Interceptor
public class LogEventInterceptor {
    Logger logger = LoggerFactory.getLogger("interceptor log");


    @AroundInvoke
    public Object logEvent(InvocationContext ctx) throws Exception {
        String clazz = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();
        logger.info("REQUEST: class=>{},httpMethod=>{},httpParams=>{}"
                , clazz,method,buildHttpParams(ctx.getParameters()));
        return ctx.proceed();
    }

    private String buildHttpParams(Object[] req) throws JsonProcessingException {
        StringBuffer paramBuf = new StringBuffer();
        paramBuf.append("{");
        //body总算打印出来了
        String string = new ObjectMapper().writeValueAsString(req);
        paramBuf.append(string);
        paramBuf.append("}");
        return new String(paramBuf);
    }
}
