package com.itay.cn.controller;

import com.itay.cn.ai.agent.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/high/api")
public class HighApiController {

    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("a01")
    public String a01(@RequestParam(value = "prompt",defaultValue = "帮我介绍一下蔡徐坤") String prompt) {
        return chatAssistant.chat(prompt);
    }
}
