package com.example.aicodehelper.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lixiaokai1
 * @description
 * @date 2026/3/4 23:01
 */
@SpringBootTest
class AiCodeHelperServiceFactoryTest {

    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void aiCodeHelperService() {
        String message = aiCodeHelperService.chat("你好，我是程序员leeyker");
        System.out.println();
    }
}