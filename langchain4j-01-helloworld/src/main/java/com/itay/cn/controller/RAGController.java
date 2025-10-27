package com.itay.cn.controller;

import com.itay.cn.ai.agent.RAGAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RAGController {

    @Resource
    private RAGAssistant ragAssistant;


    @GetMapping(value = "/rag/chat")
    public String chat(@RequestParam(value = "message") String message){
        return ragAssistant.chat(message);
    }
}
