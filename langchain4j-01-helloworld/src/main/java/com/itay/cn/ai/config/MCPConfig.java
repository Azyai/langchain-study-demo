package com.itay.cn.ai.config;

import com.itay.cn.ai.agent.MCPStdioAssistant;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class MCPConfig {

    @Bean
    public McpTransport stdioMcpTransport() {
        return new StdioMcpTransport.Builder()
                .command(List.of("cmd", "/c", "npx", "-y", "@baidumap/mcp-server-baidu-map"))
                .environment(Map.of("BAIDU_MAP_API_KEY", "o233Y9xr0iSob5XVXxmA6UuPixLuTweV"))
                .build();
    }

    @Bean(value = "streamableHttpMcpTransport")
    public McpTransport streamableHttpMcpTransport() {
        return new StreamableHttpMcpTransport.Builder()
                .url("https://mcp.map.baidu.com/mcp?ak=o233Y9xr0iSob5XVXxmA6UuPixLuTweV")
                .logRequests(true) // if you want to see the traffic in the log
                .logResponses(true)
                .build();
    }

//    @Bean
//    public McpClient mcpClient(McpTransport stdioMcpTransport) {
//        return new DefaultMcpClient.Builder()
//                .transport(stdioMcpTransport)
//                .build();
//    }

    @Bean
    public McpClient mcpClient(@Qualifier("streamableHttpMcpTransport")
                                   McpTransport streamableHttpMcpTransport) {
        return new DefaultMcpClient.Builder()
                .transport(streamableHttpMcpTransport)
                .build();
    }

    @Bean(name = "mcpToolProvider")
    public ToolProvider mcpToolProvider(McpClient mcpClient) {
        return McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
    }

    // 使用 builder 创建 MCP 助手 Bean，并注入远程工具集
    @Bean
    public MCPStdioAssistant mcpStdioAssistant(
            @Qualifier("streamingQwen") StreamingChatModel streamingChatModel,
            ToolProvider mcpToolProvider
    ) {
        return AiServices.builder(MCPStdioAssistant.class)
                .streamingChatModel(streamingChatModel)
                .toolProvider(mcpToolProvider)
                .build();
    }
}