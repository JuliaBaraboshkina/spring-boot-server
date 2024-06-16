package com.parazitik.graduatework;

import com.parazitik.graduatework.Service.AiService;
import org.junit.jupiter.api.Test;

public class AiTest {

    @Test
    public void testAi(){
        System.out.println(AiService.chatGPT("Привет, chatgpt!"));
    }
}
