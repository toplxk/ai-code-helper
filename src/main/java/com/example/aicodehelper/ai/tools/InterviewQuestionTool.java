package com.example.aicodehelper.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lixiaokai1
 * @description
 * @date 2026/3/6 1:00
 */
@Slf4j
@Component
public class InterviewQuestionTool {

    private static final String BASE_URL = "https://www.mianshiya.com/search/all?searchText=";

    @Tool("从面试鸭检索与关键词相关的面试题列表，返回可读的题目文本")
    public String searchInterviewQuestions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "关键词不能为空";
        }
        String encodedKeyword = URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8);
        String url = BASE_URL + encodedKeyword;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(10000)
                    .header("Accept-Charset", "UTF-8")
                    .execute();

            Document doc = Jsoup.parse(response.body(), url);
            List<String> selectors = List.of(
                    ".search-item .title",
                    ".search-item-title",
                    ".question-item .title",
                    "a.question-title",
                    "a[href*=\"/question/\"]",
                    "a[href*=\"/interview/\"]"
            );

            Set<String> results = new LinkedHashSet<>();
            for (String selector : selectors) {
                Elements elements = doc.select(selector);
                for (Element element : elements) {
                    String text = element.text().trim();
                    if (!text.isEmpty()) {
                        results.add(text);
                    }
                    if (results.size() >= 20) {
                        break;
                    }
                }
                if (results.size() >= 20) {
                    break;
                }
            }

            if (results.isEmpty()) {
                String title = doc.title();
                if (title != null && !title.isBlank()) {
                    return "未提取到题目列表，页面标题：" + title;
                }
                return "未提取到题目列表";
            }

            return String.join("\n", results);
        } catch (Exception e) {
            log.warn("面试鸭检索失败，keyword={}, url={}", keyword, url, e);
            return "检索失败：" + e.getMessage();
        }
    }
}
