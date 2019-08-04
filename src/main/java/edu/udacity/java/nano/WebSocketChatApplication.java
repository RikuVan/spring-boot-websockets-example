package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class WebSocketChatApplication extends Throwable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Map> login(@RequestParam("username") String username, HttpServletRequest request) {
        LOGGER.info("User attempted login: {}", username);
        if (username == null || username.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        request.getSession().setAttribute("username", username);
        Map resp = new HashMap();
        resp.put("redirectUrl", "http://localhost:8080/chat-room?username=" + username);
        return ResponseEntity.ok(resp);
    }

    /**
     * Chatroom Page
     */

    @GetMapping("/chat-room")
    public ModelAndView chat(@RequestParam("username") String username) {
        LOGGER.info("User entered chat room: {}", username);
        return new ModelAndView("/chat", "username", username);
    }
}
