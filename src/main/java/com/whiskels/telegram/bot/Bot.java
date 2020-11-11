package com.whiskels.telegram.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

// Slf4j annotation (Lombok)enables logging in this class
@Slf4j
// Component annotation (TelegramBots Spring Boot Starter) lets us use Bot as Spring Bean
@Component
// RequiredArgsConstructor annotation (Lombok) generates constructor with all final fields
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    // Value annotation is used to get variable values from application.yaml
    @Value("${bot.name}")
    // Getter annotation (Lombok) generates getter for annotated field
    @Getter
    private String botUsername;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    private final UpdateReceiver updateReceiver;

    // Main bot method that is inherited from TelegramLongPollingBot.class
    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);

        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    // Simple checking for Telegram API Exceptions
    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("oops");
        }
    }
}
