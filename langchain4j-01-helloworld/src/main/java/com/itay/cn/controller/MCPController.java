package com.itay.cn.controller;

import com.itay.cn.ai.agent.MCPStdioAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MCPController {

    @Resource
    private MCPStdioAssistant mcpStdioAssistant;

    @GetMapping(value = "/mcp/chat1")
    public Flux<String> chat(@RequestParam(value = "question")
                             String question) {
        return mcpStdioAssistant.chat(question);
    }

}