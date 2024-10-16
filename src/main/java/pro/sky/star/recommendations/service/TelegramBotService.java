package pro.sky.star.recommendations.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Model;
import pro.sky.star.recommendations.model.UserDB;
import pro.sky.star.recommendations.repository.RecommendationsRepository;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotService implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    private final RecommendationService recommendationsService;
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    private TelegramBot telegramBot;

    private final Pattern pattern = Pattern.compile("^/recommend\\s+(.+)$");

    public TelegramBotService(RecommendationService recommendationsService, RecommendationsRepository recommendationsRepository) {
        this.recommendationsService = recommendationsService;
        this.recommendationsRepository = recommendationsRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (Objects.isNull(update.message())) {
                return;
            }
            String updateText = update.message().text();
            Long chatId = update.message().chat().id();
            Matcher matcher = pattern.matcher(updateText);

            try {
                if (Objects.isNull(update.message().text())) {
                    throw new RuntimeException("Нет текста");
                }
            } catch (RuntimeException e) {
                sendMessageInTelegramBot(update.message().chat().id(), "Я работаю только с текстом");
                return;
            }
            if (updateText.equals("/start")) {
                String messageText = "Привет! Для получения информации по банковским продуктам введите \"/recommend <username>\"";
                sendMessageInTelegramBot(chatId, messageText);
            }
            if (updateText.equals("/recommend")) {
                String messageText = "Введите запрос в формате \"/recommend <username>\"";
                sendMessageInTelegramBot(chatId, messageText);
            }
            if (matcher.matches()) {
                String item = matcher.group(1);
                System.out.println(matcher.toString());
                UserDB userDB = null;
                try {
                    userDB = recommendationsRepository.getIdUser(item);
                } catch (Exception e) {
                    sendMessageInTelegramBot(chatId, "Пользователь не найден!");
                    return;
                }

                Model modelJSon = recommendationsService.get(userDB.getId().toString());
                sendMessageInTelegramBot(chatId, "Здравствуйте, " + userDB.getFirstName() + " " + userDB.getLastName());
                sendMessageInTelegramBot(chatId, "Новые продукты для вас: ");

                for (int i = 0; i < modelJSon.getRecommendationList().size(); i++) {
                    sendMessageInTelegramBot(chatId, modelJSon.getRecommendationList().get(i).getName() + '\n' +
                            modelJSon.getRecommendationList().get(i).getDocument());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessageInTelegramBot(Long chatId, String message) {
        SendMessage messageForSend = new SendMessage(chatId, message);
        telegramBot.execute(messageForSend);
    }
}
