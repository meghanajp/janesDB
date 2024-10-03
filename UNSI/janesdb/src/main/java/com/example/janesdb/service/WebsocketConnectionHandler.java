package com.example.janesdb.service;
import com.example.janesdb.bo.ServerConnectionDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class WebsocketConnectionHandler extends TextWebSocketHandler {

    @Autowired
    DataManagementService dataManagementService;


    public void dispatchDataToService(ServerConnectionDetails serverConnectionDetails){
        dataManagementService.serverConnectionMethod(serverConnectionDetails);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//         log.info("Received Sensor Connection  Data : " + message.getPayload());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServerConnectionDetails serverConnectionDetails= objectMapper.readValue(message.getPayload().toString(), new TypeReference<ServerConnectionDetails>() {});
            dispatchDataToService(serverConnectionDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Sensor Connection Data Client: Connected to Sensor Connection  Data Server");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Sensor Connection Data Server: Error occurred while sending message " + exception.getMessage());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Sensor Connection  Data Server : Connection closed");
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
