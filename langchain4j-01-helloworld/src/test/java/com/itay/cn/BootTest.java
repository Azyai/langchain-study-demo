package com.itay.cn;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BootTest {

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;


    // 这个在yml中配置好了阿里百炼的text-embedding-v3
    @Autowired 
    @Qualifier("text-embedding-v3") 
    private EmbeddingModel embeddingModel;

    @Test
    public void testUploadKnowledgeLibrary(){
        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        // 并使用默认的文档解析器对文档进行解析
        Path filePath = Paths.get("D://Project//Java2025//langchain4j//langchain4j-01-helloworld//src//main//resources//static//alibaba-java.docx");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        List<Document> documents = Arrays.asList(document);

        DocumentByCharacterSplitter documentByCharacterSplitter =
                new DocumentByCharacterSplitter(500,10 );

        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(documentByCharacterSplitter)
                .build()
                .ingest(documents);
    }
}
