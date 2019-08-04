package edu.udacity.java.nano.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Pair<String, Session>> onlineSessions = new HashMap<>();

    private static void sendMessageToAll(MessageOut msg) {
        ObjectMapper objectMapper = new ObjectMapper();
        onlineSessions.forEach((k,pair) ->  {
            String response = null;
            try {
                response = objectMapper.writeValueAsString(msg);
            } catch(JsonProcessingException e) {
                System.out.println("Failed to convert response to JSON");
            }
            pair.getValue().getAsyncRemote().sendText(response);
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        onlineSessions.put(session.getId(), new Pair<>(username, session));
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageIn msg = null;
        try {
            msg = objectMapper.readValue(jsonStr, MessageIn.class);
        } catch(IOException e) {
            System.out.println("could not parse message from client");
        }
        Pair<String, Session> userAndSession = onlineSessions.get(session.getId());
        String username = userAndSession.getKey();
        int count = onlineSessions.size();
        if (msg != null) {
            MessageOut data = new MessageOut(username, msg.getMsg(), MessageType.SPEAK, count);
            sendMessageToAll(data);
        }
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        onlineSessions.remove(session.getId());
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
