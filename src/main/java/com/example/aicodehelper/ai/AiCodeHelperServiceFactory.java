package com.example.aicodehelper.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixiaokai1
 * @description AI Service创建工厂
 * @date 2026/3/4 17:15
 */
@Configuration
public class AiCodeHelperServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        //会话记忆
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(8);
        //构造Ai Service
        AiCodeHelperService aiCodeHelperService = AiServices.builder(AiCodeHelperService.class)
                .chatModel(qwenChatModel)
                .chatMemory(chatMemory)     //会话记忆
                .build();
        /**
         调用 AiServices.create
         方法就可以创建出AIService的实现类了,背后的原理是利用Jaava反射机制创建了
         一个实现接口的代理对象,代理对象负责输入和输出的转换,比如把String类型的用户消息参数转为
         UserMessage类型并调用ChatModel,再将Al返回的AiMessage类型转换为String类型作为返回值。
         **/
//        AiCodeHelperService aiCodeHelperService = AiServices.create(AiCodeHelperService.class, qwenChatModel);
        return aiCodeHelperService;
    }

}
