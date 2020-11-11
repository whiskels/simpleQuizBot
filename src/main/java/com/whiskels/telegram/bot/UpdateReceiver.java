package com.whiskels.telegram.bot;

import com.whiskels.telegram.bot.handler.Handler;
import com.whiskels.telegram.model.User;
import com.whiskels.telegram.repository.JpaUserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {
    // Storing available handlers in a list (stolen from Miroha)
    private final List<Handler> handlers;
    // Achieving access to user storage
    private final JpaUserRepository userRepository;

    public UpdateReceiver(List<Handler> handlers, JpaUserRepository userRepository) {
        this.handlers = handlers;
        this.userRepository = userRepository;
    }

    // Analyzing received update
    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        // try-catch in order to return empty list on unsupported command
        try {
            // Checking if Update is a message with text
            if (isMessageWithText(update)) {
                // Getting Message from Update
                final Message message = update.getMessage();
                // Getting chatId
                final int chatId = message.getFrom().getId();

                // Getting user from repository. If user is not presented in repository - create new and return him
                // For this reason we have a one arg constructor in User.class
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                // Looking for suitable handler
                return getHandlerByState(user.getBotState()).handle(user, message.getText());
            // Same workflow but for CallBackQuery
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final int chatId = callbackQuery.getFrom().getId();
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
