package com.example.aicodehelper.ai.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixiaokai1
 * @description RAG config
 * @date 2026/3/5 0:27
 */
@Configuration
@Slf4j
public class RagConfig {

    @Resource
    private EmbeddingModel qwenEmbeddingModel;

    @Value("${rag.document-path:}")
    private String documentPath;

    @Bean
    @ConditionalOnMissingBean
    public EmbeddingStore<TextSegment> defaultEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    @ConditionalOnProperty(prefix = "rag", name = "enabled", havingValue = "true")
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore) {
        if (documentPath == null || documentPath.isBlank()) {
            log.warn("rag.document-path is blank, skip ingestion.");
        } else {
            Document document = FileSystemDocumentLoader.loadDocument(documentPath);
            ingestDocument(document, embeddingStore);
        }

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(5)
                .minScore(0.75)
                .build();
    }

    private void ingestDocument(Document document, EmbeddingStore<TextSegment> embeddingStore) {
        DocumentByParagraphSplitter documentByParagraphSplitter =
                new DocumentByParagraphSplitter(1000, 200);
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentByParagraphSplitter)
                .textSegmentTransformer(textSegment ->
                        TextSegment.from(textSegment.metadata().getString("file_name") + "\n" + textSegment.text(), textSegment.metadata()))
                .embeddingModel(qwenEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);
    }
}
