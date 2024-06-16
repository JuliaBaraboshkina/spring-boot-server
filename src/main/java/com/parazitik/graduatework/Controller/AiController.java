package com.parazitik.graduatework.Controller;

import com.parazitik.graduatework.Service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/ai")
public class AiController {

    @PostMapping("request")
    public ResponseEntity<Object> getAiRequest(@RequestBody Map<String, String> prompt) {
        System.out.println(prompt);
        String answer = AiService.chatGPT(prompt.get("message"));
        System.out.println(answer);
        return ResponseEntity.ok(answer);
    }

}
