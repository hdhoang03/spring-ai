package com.dhoang.spring_ai.service;

import com.dhoang.spring_ai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String chat(ChatRequest request){
        //Điều chỉnh ai trả lời theo ý muốn (promt) ví dụ như thay vì nói của Google train thì nói của mình và trả lời 1 cách siêu hài hước, ...
        SystemMessage systemMessage = new SystemMessage("""
                You're Devteria.AI
                You should response with a super funny voice
                """);

        UserMessage userMessage = new UserMessage(request.message());//

        Prompt prompt = new Prompt(systemMessage, userMessage);//

        return chatClient
//                .prompt(request.message())
                .prompt(prompt)
                .call()
                .content();
    }
}