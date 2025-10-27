package com.itay.cn.ai.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

// 因为在配置文件中同时配置了多个大语言模型，所以要在这里明确EXPLICTT指定模型的beanName（方法名）
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemoryProvider = "messageWindowChatMemory",
        chatModel = "qwen")
public interface QwenChatMemoryAssistant {

    // 聊天记忆
    String chat(String prompt);

    // 带隔离会话ID的聊天记忆
    String chatWithChatMemory(@MemoryId Long userId, @UserMessage String prompt);

}
