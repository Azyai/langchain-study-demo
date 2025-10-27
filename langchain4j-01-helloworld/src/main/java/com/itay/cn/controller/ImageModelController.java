package com.itay.cn.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/image/api")
public class ImageModelController {

    @Resource(name = "qwen-vl-max")
    private ChatModel qwenImageModel;

    @Value("classpath:static/images/1.jpg")
    private org.springframework.core.io.Resource resource;

    @RequestMapping("/call")
    public String a01() throws IOException {
        byte[] byteArray = resource.getContentAsByteArray();
        String base64 = Base64.getEncoder().encodeToString(byteArray);

        UserMessage userMessage = UserMessage.from(
                TextContent.from("请帮我分析这张图片，并给出图片的描述。"),
                ImageContent.from(base64, "image/jpeg")
        );

        ChatResponse chatResponse = qwenImageModel.chat(userMessage);
        String result = chatResponse.aiMessage().text();
        System.out.println("qwenImageModel 回复: " + result);
        return result;
    }
}
