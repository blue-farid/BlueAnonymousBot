package com.blue_farid.blue_anonymous_bot.utils;

import com.blue_farid.blue_anonymous_bot.model.Client;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final TimeUtils timeUtils;
    private enum FilePath {
        MONITORING("/var/log/blue-anonymous-bot/");
        private String value;


        FilePath(String value) {
            this.value = value;
            formatBasedOnOs();
        }

        public String getValue() {
            return value;
        }

        private void formatBasedOnOs() {
            if (CommonUtils.isBotRunsOnWindows()) {
                this.value = value.replace("/", "\\");
            }
        }
        }

    public void monitorSendMessageToContact(String className, String message, Client client) {
        new Thread(new MonitorMessagesHandler(client, className, message, timeUtils)).start();
    }

    private record MonitorMessagesHandler(Client client, String className,
                                          String message, TimeUtils timeUtils) implements Runnable {

        @Override
        public void run() {
            String path = FilePath.MONITORING.getValue().concat(className.concat("/"));
            long firstId = client.getId();
            long secondId = client.getContactId();
            if (firstId > secondId) {
                long temp = secondId;
                secondId = firstId;
                firstId = temp;
            }
            // create dirs at first .
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            // now create the file.
            path = path.concat(String.valueOf(firstId)).concat("-").concat(String.valueOf(secondId).concat(".txt"));
            file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (FileWriterWithEncoding out = new FileWriterWithEncoding(file, StandardCharsets.UTF_8, true)) {
                String str = timeUtils.getCurrentDateAndTimeString().
                        concat(" " + String.valueOf(client.getId()).concat(":{\n").concat("\t" + message)
                                .concat("\n}\n"));
                out.write(str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
