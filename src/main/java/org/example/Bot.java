package org.example;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.security.Key;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;

    // Конструктор с токеном и username
    public Bot(String botToken, String botUsername) {
        super(botToken);  // Передаём токен в родительский класс
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;  // Возвращаем сохранённый username
    }

    @Override
    public void onUpdateReceived(Update update) {
      //String text =   update.getMessage().getText();
      //  System.out.println(text);

        if (update.hasMessage()) {
            switch (update.getMessage().getText()){
                case "/start" -> {
                    try {
                        sendMessage(update.getMessage().getFrom().getId(),"" +
                                "Hi!This is test!", "Кнопка1","" +
                                "Кнопка2");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
                case "/stop" -> {}
                case "/help" -> {}

            }
        }else if (update.hasCallbackQuery()) {
            switch (update.getCallbackQuery().getData()){
                case "test1" -> {
                    try {
                        sendMessage(update.getCallbackQuery().getFrom().getId(),
                                "command1 completed");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "test2" -> {
                    try {
                        sendMessage(update.getCallbackQuery().getFrom().getId(),
                                "command2 completed","кнопка 3",
                                "кнопка 4");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }


            }




        }


    }
    public void sendMessage(long id, String text) throws TelegramApiException {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(text);
//        sendMessage.setChatId(String.valueOf(id));
//        execute(sendMessage);
        SendMessage sendMessage = SendMessage.builder()
                .text(text)
                .chatId(id)
                .build();
            sendApiMethod(sendMessage);
    }
    public  InlineKeyboardMarkup sendCallBackQuery( String NameButton1,
                                                    String NameButton2){
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text(NameButton1)
                .callbackData("test1")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text(NameButton2).callbackData("test2").build();
      //  KeyboardButton keyboardButton =
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(button1, button2))
                .build();
        return keyboard;
            }
            public void sendMessage(long id, String text, String nameButton1,
                                    String nameButton2) throws TelegramApiException {
               InlineKeyboardMarkup keyboardMarkup =
                       sendCallBackQuery(nameButton1,nameButton2);
               SendMessage sendMessage = SendMessage.builder()
                       .chatId(id)
                       .text(text)
                       .replyMarkup(keyboardMarkup)
                       .build();
            sendApiMethod(sendMessage);
            }
}
// Изменение1