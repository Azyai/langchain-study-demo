package com.itay.cn.ai.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

// 因为在配置文件中同时配置了多个大语言模型，所以要在这里明确EXPLICTT指定模型的beanName（方法名）
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemoryProvider = "tokenWindowChatMemory",
        streamingChatModel = "qwenStreamingChatModel")
public interface StreamMemoryAssistant {

    String chat(String prompt);

    String chatWithChatMemory(@MemoryId Long userId, @UserMessage String prompt);

    Flux<String> chatFlux(@MemoryId Long userId, @UserMessage String prompt);
}
