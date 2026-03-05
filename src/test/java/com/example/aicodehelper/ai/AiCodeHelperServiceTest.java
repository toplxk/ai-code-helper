package com.example.aicodehelper.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lixiaokai1
 * @description
 * @date 2026/3/4 23:34
 */
@SpringBootTest
class AiCodeHelperServiceTest {

    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void charWithMemory() {
        String message = aiCodeHelperService.chat("你好，我是程序员leeyker");
        message = aiCodeHelperService.chat("我叫什么来着");
        System.out.println(message);
    }

    @Test
    void chatForRepot() {
        AiCodeHelperService.Report report = aiCodeHelperService.chatForRepot("你好，我是程序员leeyker，学习编程两年半，请帮成制定学习报告");
        System.out.println(report);
    }

    @Test
    void charWithTools() {
        String message = aiCodeHelperService.chat("有哪些常见的计算机网络面试题");
        System.out.println(message);
    }
}