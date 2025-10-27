package com.itay.cn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/multiModel/api")
public class MultiModelController {

    @Resource(name = "qwen")
    private ChatModel qwen;

    @Resource(name = "deepseek")
    private ChatModel deepseek;

    @GetMapping("/qwen")
    public String qwen(@RequestParam(value = "question",defaultValue = "蔡徐坤会不会打篮球？")
                           String question) {
        return qwen.chat(question);
    }

    @GetMapping("/deepseek")
    public String deepseek(@RequestParam(value = "question",defaultValue = "蔡徐坤会不会打篮球？")
                               String question) {
        return deepseek.chat(question);
    }

}