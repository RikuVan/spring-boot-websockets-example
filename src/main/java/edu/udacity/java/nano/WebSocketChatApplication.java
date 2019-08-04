package edu.udacity.java.nano;

import edu.udacity.java.nano.chat.GlobalExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@ComponentScan("edu.udacity.java.nano.chat")
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    @Bean
    HandlerExceptionResolver customExceptionResolver () {
        return new GlobalExceptionResolver();
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
        return new ModelAndView("/chat", "username", username);
    }
}
