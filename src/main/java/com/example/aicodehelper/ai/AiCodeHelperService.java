package com.example.aicodehelper.ai;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;

import java.util.List;

/**
 * AI Service
 *
 */
public interface AiCodeHelperService {

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String message);

    @SystemMessage(fromResource = "system-prompt.txt")
    Report chatForRepot(String message);

    //学习报告
    record Report(String name, List<String> suggestionList){};

    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String message);
}
