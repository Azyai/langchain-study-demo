package com.itay.cn.controller;

import com.itay.cn.ai.agent.QwenChatMemoryAssistant;
import com.itay.cn.ai.agent.QwenChatTokenMemoryAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatmemory/api")
public class ChatMemoryController {


    @Resource
    private QwenChatMemoryAssistant qwenChatMemoryAssistant;

    @Resource
    private QwenChatTokenMemoryAssistant qwenChatTokenMemoryAssistant;

    /**
     * @deprecated 聊天记忆
     */
    @GetMapping("/chat1")
    public String chat1(@RequestParam(value = "prompt"
            ,defaultValue = "介绍一下langchain4j")String prompt){
        String ans1 = qwenChatMemoryAssistant.chat(prompt);
        System.out.println(ans1);

        String ans2 = qwenChatMemoryAssistant.chat("我刚刚问了你什么问题？");
        System.out.println(ans2);

        return ans1 + "\n" + ans2;
    }

    /**
     * @deprecated 带隔离会话ID的聊天记忆
     */
    @GetMapping("/chat2")
    public String chat2(@RequestParam(value = "prompt"
            ,defaultValue = "介绍一下langchain4j")String prompt){
        String ans1 = qwenChatMemoryAssistant.chatWithChatMemory(1L, prompt);
        System.out.println(ans1);

        String ans2 = qwenChatMemoryAssistant.chatWithChatMemory(1L, "我刚刚问了你什么问题？");
        System.out.println(ans2);

        String ans3 = qwenChatMemoryAssistant.chatWithChatMemory(2L, "可以介绍一下蔡徐坤吗？");
        System.out.println(ans3);

        String ans4 = qwenChatMemoryAssistant.chatWithChatMemory(2L, "我刚刚问了你什么问题？");
        System.out.println(ans4);

        return ans1 + "\n" + ans2 + "\n" + ans3 + "\n" + ans4;
    }


    @GetMapping("/chat3")
    public String chat3(@RequestParam(value = "prompt"
            ,defaultValue = "介绍一下langchain4j")String prompt){
        String ans1 = qwenChatTokenMemoryAssistant.chatWithChatMemory(1L, prompt);
        System.out.println(ans1);

        String ans2 = qwenChatTokenMemoryAssistant.chatWithChatMemory(1L, "我刚刚问了你什么问题？");
        System.out.println(ans2);

        String ans3 = qwenChatTokenMemoryAssistant.chatWithChatMemory(2L, "可以介绍一下蔡徐坤吗？");
        System.out.println(ans3);

        String ans4 = qwenChatTokenMemoryAssistant.chatWithChatMemory(2L, "我刚刚问了你什么问题？");
        System.out.println(ans4);

        return ans1 + "\n" + ans2 + "\n" + ans3 + "\n" + ans4;
    }

}
