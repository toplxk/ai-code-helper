package com.example.aicodehelper;

import com.example.aicodehelper.ai.AiCodeHelper;
import com.example.aicodehelper.ai.AiCodeHelperService;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeHelperApplicationTests {

    @Resource
    private AiCodeHelper aiCodeHelper;

    @Test
    void contextLoads() {
    }

    @Test
    void chat() {
        aiCodeHelper.chat("你好，我是程序员leeyker");
    }

    @Test
    void charWithMessage() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("解释图片"),
                ImageContent.from("https://www.baidu.com/img/flexible/logo/pc/result.png")
        );
        aiCodeHelper.chatWithMessage(userMessage);
    }
}
