package com.itay.cn.ai;

import com.itay.cn.ai.agent.ChatAssistant;
import com.itay.cn.listener.QwenChatModelListener;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class LLMModel {

    @Bean(name = "qwen")
    public ChatModel chatModelQwen(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("qwen3-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .listeners(List.of(new QwenChatModelListener()))
                .maxRetries(2)
                .timeout(Duration.ofSeconds(60))
                .build();
    }


    @Bean(name = "qwen-vl-max")
    public ChatModel chatModelQwenVlMax(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("qwen-vl-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean
    public WanxImageModel wanxImageModel()
    {
        return WanxImageModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("wan2.5-t2i-preview")
                .build();
    }


    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("deepseek-api"))
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1")
                .build();
    }

    @Bean
    public ChatAssistant chatAssistant(@Qualifier("qwen") ChatModel qwenModel){
        return AiServices.create(ChatAssistant.class, qwenModel);
    }

    @Bean(name = "streamingQwen")
    public StreamingChatModel streamingChatModelQwen(){
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                //.modelName("qwen3-max")
                .modelName("qwen-long")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }



}