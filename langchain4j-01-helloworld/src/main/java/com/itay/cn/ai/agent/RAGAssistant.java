package com.itay.cn.ai.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;


@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwen",
        chatMemoryProvider = "redisChatMemory",
        contentRetriever = "contentRetrieverRAG"

)
public interface RAGAssistant {

    @SystemMessage("你可以结合RAG中的文档，回答一下用户提问的问题，并且给出示例,内容尽量在100字以上，500字以内")
    @UserMessage("我提出的问题是，{{it}}，请你回答我一下")
    String chat(String message);
}
