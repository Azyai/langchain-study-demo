package com.itay.cn.ai.config;

import com.itay.cn.config.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIMemoryConfig {

    @Bean(name = "messageWindowChatMemory")
    public ChatMemoryProvider messageWindowChatMemory(){
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .build();
    }

    @Bean(name = "tokenWindowChatMemory")
    public ChatMemoryProvider tokenWindowChatMemory(){
        TokenCountEstimator tokenCountEstimator = new OpenAiTokenCountEstimator("gpt-4");
        return memoryId -> TokenWindowChatMemory.builder()
                .id(memoryId)
                .maxTokens(1000,tokenCountEstimator)
                .build();
    }


    @Autowired
    private RedisChatMemoryStore redisChatMemoryStore;

    @Bean(name = "redisChatMemory")
    public ChatMemoryProvider redisChatMemory(){
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }

}