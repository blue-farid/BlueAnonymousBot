package com.blue_farid.blue_anonymous_bot.controller;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@RestController
@RequestMapping("/api/blue-anonymous-bot/stream")
public class FileStreamingController {

    @GetMapping("/log")
    @SneakyThrows
    public ResponseEntity<StreamingResponseBody> streamLog() {
        InputStream inputStream = new FileInputStream("/var/log/blue-anonymous-bot/logging.log");
        return getStreamingResponseEntity(inputStream);
    }

    @GetMapping("/chat")
    @SneakyThrows
    public ResponseEntity<StreamingResponseBody> streamChat(
            @RequestParam String first,
            @RequestParam String second
    ) {

        InputStream inputStream = new FileInputStream("/var/log/blue-anonymous-bot/SendMessage/"
                .concat(first).concat("-").concat(second).concat(".txt"));
        return getStreamingResponseEntity(inputStream);
    }


    private ResponseEntity<StreamingResponseBody> getStreamingResponseEntity(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return ResponseEntity.ok().body(outputStream -> {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                    writer.flush();
                }
            }
        });
    }
}
