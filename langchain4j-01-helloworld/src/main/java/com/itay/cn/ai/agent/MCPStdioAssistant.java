package com.itay.cn.ai.agent;

import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface MCPStdioAssistant {
    Flux<String> chat(@UserMessage("{{it}}，问题描述") String question);
}