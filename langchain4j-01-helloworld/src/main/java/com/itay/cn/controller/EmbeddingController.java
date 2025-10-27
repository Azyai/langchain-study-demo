package com.itay.cn.controller;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import com.itay.cn.ai.EmbeddingLLMModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@RestController
@Slf4j
public class EmbeddingController {

    @Autowired
    @Qualifier("text-embedding-v3")
    private EmbeddingModel embeddingModel;

    @Resource
    private QdrantClient qdrantClient;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 文本向量化测试，查看形成向量后的文本
     */
    @GetMapping(value = "/embedding/embed")
    public String embed() throws IOException {
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
        return "成功";
    }



    /**
     * 新建向量数据库实例和创建索引：test-qdrant
     * 类似于MySQL create database test-qdrant;
     */
    @GetMapping(value = "/embedding/createCollection")
    public void createCollection()
    {
        var vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                .setSize(1024)
                .build();
        qdrantClient.createCollectionAsync("test_111", vectorParams);
    }

    /*
    往向量数据库新增文本记录
    */
    @GetMapping(value = "/embedding/add")
    public String add()
    {
        String prompt = """
                咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        TextSegment segment1 = TextSegment.from(prompt);
        segment1.metadata().put("author", "aayy");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        String result = embeddingStore.add(embedding1, segment1);

        System.out.println(result);

        return result;
    }

    @GetMapping(value = "/embedding/query1")
    public void query1(){
        Embedding queryEmbedding = embeddingModel.embed("咏鸡说的是什么").content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);
        System.out.println(searchResult.matches().get(0).embedded().text());
    }

    @GetMapping(value = "/embedding/query2")
    public void query2(){
        Embedding queryEmbedding = embeddingModel.embed("咏鸡").content();

        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("author").isEqualTo("aayy2"))
                .maxResults(1)
                .build();

        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);

        System.out.println(searchResult.matches().get(0).embedded().text());
    }

    @GetMapping(value = "/embedding/query3")
    public void query3(){
        Embedding queryEmbedding = embeddingModel.embed("咏鸡").content();

        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("author").isEqualTo("aayy"))
                .maxResults(1)
                .build();

        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);

        System.out.println(searchResult.matches().get(0).embedded().text());
    }


}
