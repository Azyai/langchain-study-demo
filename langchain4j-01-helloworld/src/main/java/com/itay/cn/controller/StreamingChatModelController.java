package com.itay.cn.controller;

import com.itay.cn.ai.agent.StreamMemoryAssistant;
import com.itay.cn.ai.agent.StreamPersistenceAssistant;
import com.itay.cn.ai.agent.StreamQwenChatAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chatstream")
public class StreamingChatModelController {

    // 注入模型，low-level llm api方式
    @Resource(name = "streamingQwen")
    private StreamingChatModel streamingChatModel;

    // 注入智能体，high-level agent api方式
    @Resource
    private StreamQwenChatAssistant streamQwenChatAssistant;


    // streamingChatModel方式，需要配合Flux.create()使用手动处理
    @GetMapping("/chat1")
    public Flux<String> chat(@RequestParam(value = "prompt",
            defaultValue = "介绍一下蔡徐坤") String  prompt){
        return Flux.create(sink -> {
            streamingChatModel.chat(prompt, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String s) {
                    sink.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    sink.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    sink.error(throwable);
                }
            });
        });
    }

    // streamQwenChatAssistant方式，AiService自动处理流式响应
    @GetMapping("/chat2")
    public Flux<String> chat2(@RequestParam(value = "prompt",
            defaultValue = "介绍一下蔡徐坤") String  prompt){
        return streamQwenChatAssistant.chatFlux(prompt);
    }

    @Resource
    StreamMemoryAssistant qwenStreamChatTokenMemoryAssistant;

    @GetMapping("/chat3")
    public Flux<String> chat3(@RequestParam(value = "prompt",
            defaultValue = "介绍一下蔡徐坤") String  prompt){
        Flux<String> s1 = qwenStreamChatTokenMemoryAssistant.chatFlux(1L, prompt);
        Flux<String> s2 = qwenStreamChatTokenMemoryAssistant.chatFlux(1L, "我刚刚问的什么问题?");
        return Flux.merge(s1, s2);

    }


    @Resource
    StreamPersistenceAssistant streamPersistenceAssistant;

    @GetMapping("/chat4")
    public Flux<String> chat4(@RequestParam(value = "userId") Long userId, @RequestParam(value = "prompt",
            defaultValue = "介绍一下蔡徐坤") String  prompt){
        return streamPersistenceAssistant.chatFlux(userId, prompt);
    }

}
