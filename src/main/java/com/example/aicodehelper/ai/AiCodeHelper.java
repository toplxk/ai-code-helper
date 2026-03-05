package com.example.aicodehelper.ai;


import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeHelper {

    @Resource
    private ChatModel qwenChatModel;

    //系统消息预设，真实场景需要认真的写系统消息预设
    private static final String SYSTEM_MESSAGE = """
            你是编程领域的小助手,帮助用户解答编程学习和求职面试相关的问题,并给出建议。重点关注4个方向:
            1.规划清晰的编程学习路线
            2.提供项目学习建议
            3.给出程序员求职全流程指南(比如简历优化、投递技巧)
            4.分享高频面试题和面试技巧
            请用简洁易懂的语言回答,助力用户高效学习与求职。
            """;

    public String chat(String message) {
        //定义系统消息，系统消息只包含一个，如果有多个，那么新的系统消息替换旧的
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        //用户消息
        UserMessage  userMessage = UserMessage.from(message);
        //AI对话
        ChatResponse chatResponse = qwenChatModel.chat(systemMessage, userMessage);
        //拿到AI回复的消息
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("Chat Message: {}", aiMessage.toString());
        //返回AI回复的实际文本
        return aiMessage.text();
    }

    public String chatWithMessage(UserMessage userMessage) {
        //AI对话
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        //拿到AI回复的消息
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("Chat Message: {}", aiMessage.toString());
        //返回AI回复的实际文本
        return aiMessage.text();
    }



}
