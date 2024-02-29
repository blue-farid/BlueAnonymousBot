package com.blue_farid.blue_anonymous_bot.job;

import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.Role;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.BlueAnonymousBot;
import com.blue_farid.blue_anonymous_bot.utils.metric.MetricUtil;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportJob {

    private final BlueAnonymousBot bot;
    private final ClientService clientService;
    private final MetricUtil metricUtil;

    @Async
    @Scheduled(cron = "0 0 16 * * ?")
    public void dailyReport() {
        metricUtil.incrementJob("daily_report", "total");

        List<Client> clientList = clientService.getAllNewJoiners();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("New Joiners Report");
        stringBuilder.append("\n");
        for (Client client : clientList) {
            stringBuilder.append(client.getClientInfo());
            stringBuilder.append("\n");
            stringBuilder.append("-------------------------------------");
            stringBuilder.append("\n");
        }

        List<Client> clients = clientService.getClientByRole(new Role().setValue("ROLE_REPORT"));
        clients.forEach(c -> {
            // Send report via the Telegram bot using SendDocument
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(c.getId());
            sendMessage.setText(stringBuilder.toString());
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                metricUtil.incrementJob("daily_report", "failed");
                e.printStackTrace();
            }
        });

        metricUtil.incrementJob("daily_report", "success");
    }
}
