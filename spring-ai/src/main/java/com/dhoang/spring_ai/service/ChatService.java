package com.dhoang.spring_ai.service;

import com.dhoang.spring_ai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String chat(ChatRequest request){
        //Điều chỉnh ai trả lời theo ý muốn (promt) ví dụ như thay vì nói của Google train thì nói của mình và trả lời 1 cách siêu hài hước, ...
        SystemMessage systemMessage = new SystemMessage("""
                You're PRXai
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

    public String chatWithImage(MultipartFile file, String message){
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

        ChatOptions chatOptions = ChatOptions.builder()
                .temperature(0D)//0-1 càng về 0 độ sáng tạo càng thấp, 1 là cao nhất. càng thấp thì độ tương đồng về kết quả đầu ra càng cao
                .build();

        return chatClient.prompt()
                .options(chatOptions)//Gọi hàm sáng tạo vào
                .system("You're Devteria.AI")
                .user(promptUserSpec
                    -> promptUserSpec.media(media)
                    .text(message))//Request
                .call()
                .content();
    }
}