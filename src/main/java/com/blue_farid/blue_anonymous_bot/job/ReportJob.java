package com.blue_farid.blue_anonymous_bot.job;

import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.BlueAnonymousBot;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ReportJob {

    private final BlueAnonymousBot bot;
    private final ClientService clientService;

    @Value("${admin.chatId}")
    private String chatId;

    public ReportJob(BlueAnonymousBot bot, ClientService clientService) {
        this.bot = bot;
        this.clientService = clientService;
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void dailyReport() {
        List<Client> clientList = clientService.getAllNewJoiners();

        // Generate PDF report
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("New Joiners Report");
            contentStream.newLine();
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            for (Client client : clientList) {
                contentStream.showText(client.getClientInfo());
                contentStream.newLine();
                contentStream.showText("-------------------------------------");
                contentStream.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File tempFile;
        try {
            tempFile = File.createTempFile("report", ".pdf");
            document.save(tempFile);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        InputFile inputFile = new InputFile(tempFile);

        // Send the PDF report via the Telegram bot using SendDocument
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(inputFile);
        try {
            bot.execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Delete the temporary file
        tempFile.delete();
    }
}
