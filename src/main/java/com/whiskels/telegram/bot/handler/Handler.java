package com.whiskels.telegram.bot.handler;

import com.whiskels.telegram.bot.State;
import com.whiskels.telegram.model.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);

    State operatedBotState();

    List<String> operatedCallBackQuery();
}
