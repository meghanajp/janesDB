package com.example.janesdb.service;

import com.example.janesdb.bo.ImageDetailsDto;
import com.example.janesdb.entity.JanesDBEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Component
public class WebsocketConnectionService {

    @PostConstruct
    public void postCreateActivities(){
        checkAndReconnect();
    }

    @Autowired
    WebsocketConnectionHandler websocketConnectionHandler;

    @Value("${AICEConnectionDetailsServer_Uri}")
    private String serverUri;
    private WebSocketSession session;

    public void connectToServer() {
        WebSocketClient client = new StandardWebSocketClient();
        try {
            session = client.doHandshake(websocketConnectionHandler, serverUri).get();

        } catch (Exception e) {
            log.error("** AICE Connection Data Client:Failed to Connect to the  Server**");
        }
    }

    public Void checkAndReconnect(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (session==null || !session.isOpen()) {
                        connectToServer();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 3000); // Delay in milliseconds (0 for immediate start), period in milliseconds (1000 = 1 second)

        return null;
    }

    public ResponseEntity<?> sendImageToAICE(ImageDetailsDto imageDetailsDto) throws IOException {
        if (session==null || !session.isOpen()) {
            ObjectMapper objectMapper = new ObjectMapper();
//            new ByteArrayResource(filename.getBytes());
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(imageDetailsDto)));
            return ResponseEntity.ok("Image Sent Successfully");
        }else{
            throw new RuntimeException("WebSocket session is not open or is null.");
        }
    }

    public ResponseEntity<?> sendLengthToAICE(List<JanesDBEntity> janesDB) throws IOException {
        if (session==null || !session.isOpen()) {
            ObjectMapper objectMapper = new ObjectMapper();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(janesDB)));
            return ResponseEntity.ok("Length Sent Successfully");
        }else{
            throw new RuntimeException("WebSocket session is not open or is null.");
        }
    }
}
