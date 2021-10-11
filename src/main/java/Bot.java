import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {


    public static void main(String[] args)  {
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();

        try {
            bot.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    void sendSt(Message message, int id) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(message.getChatId().toString());
        if (id == 1) {
            sendSticker.setSticker("CAACAgIAAxkBAAEBeetfjbRxeHDUgtscB52l-Z_WxD85wwACzgAD9wLID1yYR-DMq26KGwQ");
        }
        if (id == 2) {
            sendSticker.setSticker("CAACAgIAAxkBAAI3_F-R1sPHIfxUQxB8JP3VRKIaaBU2AAJ-AQACMNSdEQgbqvF7VBGNGwQ");
        }

        if (id == 3) {
            sendSticker.setSticker("CAACAgIAAxkBAAEBeHlfjFBxpvmdl_ij76F8eMwIHefNjwACBQADwDZPE_lqX5qCa011GwQ");
        }

        if (id == 4) {
            sendSticker.setSticker("CAACAgIAAxkBAAKd0l_vGeqx-nkvnmEB7yWGDwJvQgkwAAJbAANBtVYM6qligRDYPFoeBA");
        }


        try {
            sendSticker(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void keyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Курс/Course"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    void sendMsg(Message message, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            keyboard(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void onUpdateReceived(Update update) {
        final Message message = update.getMessage();
        final Model model = new Model();


        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Я умею показывать курс USD и EUR по отношению к BYN:)");
                    break;
                case "/help":
                    sendMsg(message, "Тут давай как нибудь сам:)");
                    break;
                case "Курс/Course":
                    try {
                        sendMsg(message, Course.getCourse(model));
                        sendSt(message, 4);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "hello":
                case "Hello":
                case "Привет":
                case "привет":
                    sendSt(message, 3);
                    break;

                default:
                    try {
                        if (Conversion.getConversion(message.getText(), model) == "Error") {
                            sendMsg(message, "Не правильный формат!");
                            sendSt(message, 1);
                            return;
                        } else sendMsg(message, Conversion.getConversion(message.getText(), model));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }


        } else {
            sendSt(message, 2);
        }

    }

    public String getBotUsername() {
        return "name";
    }

    public String getBotToken() {
        return "token";
    }
}
