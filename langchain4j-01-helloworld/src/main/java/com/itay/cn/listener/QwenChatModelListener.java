package com.itay.cn.listener;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class QwenChatModelListener implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        // onRequest配置kv键值对，用于上下文传参
        String uuidValue = UUID.randomUUID().toString();
        requestContext.attributes().put("TraceID", uuidValue);
        log.info("请求参数：{}，TraceID：{}", requestContext, uuidValue);
        ChatModelListener.super.onRequest(requestContext);
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        Object o = responseContext.attributes().get("TraceID");
        log.info("响应参数：{}，TraceID：{}", responseContext, o);
        ChatModelListener.super.onResponse(responseContext);
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("错误参数：{}", errorContext);
        ChatModelListener.super.onError(errorContext);
    }
}
