package com.itay.cn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/low/api")
public class LowApiController {

    @Resource(name = "qwen")
    private ChatModel qwenModel;

    @Resource(name = "deepseek")
    private ChatModel deepseekModel;

    @GetMapping("/a01")
    public String a01(@RequestParam(value = "prompt",defaultValue = "帮我介绍一下蔡徐坤") String prompt) {
        return qwenModel.chat(prompt);
    }

    @GetMapping("/a02")
    public String a02(@RequestParam(value = "prompt",defaultValue = "帮我介绍一下蔡徐坤") String prompt) {
        ChatResponse chatResponse = qwenModel.chat(UserMessage.from(prompt));

        String result = chatResponse.aiMessage().text();
        System.out.println("deepseekModel 回复: " + result);

        TokenUsage tokenUsage = chatResponse.tokenUsage();
        System.out.println("deepseekModel 消耗的token数量: " + tokenUsage);

        result += "\n" + tokenUsage;

        return result;
    }
                
}
