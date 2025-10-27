package com.itay.cn.controller;

import com.itay.cn.ai.agent.LawAssistant;
import com.itay.cn.ai.agent.WeatherAssistant;
import com.itay.cn.entity.LawPrompt;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class ChatPromptController
{
    @Resource
    private LawAssistant lawAssistant;

    @GetMapping(value = "/chatprompt/test1")
    public String test1()
    {
        String chat = lawAssistant.chat("什么是知识产权？",2000);
        System.out.println(chat);

        String chat2 = lawAssistant.chat("什么是java？",2000);
        System.out.println(chat2);

        String chat3 = lawAssistant.chat("介绍下西瓜和芒果",2000);
        System.out.println(chat3);

        String chat4 = lawAssistant.chat("飞机发动机原理",2000);
        System.out.println(chat4);

        return "success : "+ new Date() +"<br> \n\n chat: "+chat+"<br> \n\n chat2: "+chat2;
    }

    /**
     * TRIPS协议（与贸易有关的知识产权协议）：
     * 这是世界贸易组织（WTO）成员间的一个重要协议，
     * 它规定了最低标准的知识产权保护要求，并适用于所有WTO成员。
     * @return
     */
    @GetMapping(value = "/chatprompt/test2")
    public String test2()
    {
        LawPrompt prompt = new LawPrompt();

        prompt.setLegal("知识产权");
        prompt.setQuestion("TRIPS协议?");

        String chat = lawAssistant.chat(prompt);

        System.out.println(chat);

        return "success : "+ new Date() + "<br> \n\n chat: "+chat;
    }

    @Resource(name = "qwen")
    private ChatModel chatModel;

    /**
     * @Description: 单个参数可以使用{{it}》”占位符或者”{{参数名}”，如果为其他字符，系统不能自动识别会报错。
     * @Auther: zzyybs@126.com
     */
    @GetMapping(value = "/chatprompt/test3")
    public String test3()
    {
        // 默认 PromptTemplate 构造使用 it 属性作为默认占位符

        String role = "外科医生";
        String question = "牙疼";

        /*String role = "财务会计";
        String question = "人民币大写";*/

        //1 构造PromptTemplate模板
        PromptTemplate template = PromptTemplate.from("你是一个{{it}}助手,{{question}}怎么办");
        //2 由PromptTemplate生成Prompt
        Prompt prompt = template.apply(Map.of("it",role,"question",question));
        //3 Prompt提示词变成UserMessage
        UserMessage userMessage = prompt.toUserMessage();
        //4 调用大模型
        ChatResponse chatResponse = chatModel.chat(userMessage);

        //4.1 后台打印
        System.out.println(chatResponse.aiMessage().text());
        //4.2 前台返回
        return "success : "+ new Date() +"<br> \n\n chat: "+chatResponse.aiMessage().text();
    }

    @Resource
    private WeatherAssistant weatherAssistant;

    @GetMapping(value = "/chatprompt/test4")
    public String test4(@RequestParam("city") String city)
    {
        String chat = weatherAssistant.chatWeatherTool(city);
        System.out.println(chat);
        return "success : "+ new Date() +"<br> \n\n chat: "+chat;
    }

}