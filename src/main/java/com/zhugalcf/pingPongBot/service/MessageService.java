package com.zhugalcf.pingPongBot.service;

import com.zhugalcf.pingPongBot.entity.Game;
import com.zhugalcf.pingPongBot.entity.UserMessage;
import com.zhugalcf.pingPongBot.repository.GameRepository;
import com.zhugalcf.pingPongBot.repository.MessageRepository;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageSource messageSource;
    private final GameRepository gameRepository;
    private String author = "UNKNOWN";
    private int botScore = 0;
    private int playerScore = 0;
    private static final int WIN_SCORE = 11;

    public Mono<Void> getResponse(final Message eventMessage) {
        eventMessage.getAuthor().ifPresent(user -> author = user.getUsername());
        if (eventMessage.getContent().equalsIgnoreCase("!ping")) {
            saveMessage(eventMessage);
            return playGame(eventMessage);
        }
        if (eventMessage.getContent().equalsIgnoreCase("!getMyGames")) {
            saveMessage(eventMessage);
            long userId = eventMessage.getAuthor().get().getId().asLong();
            return buildResponse(eventMessage, userScore(userId));
        }
        return buildResponse(eventMessage, messageSource
                .getMessage("welcomeMessage", new Object[]{author}, null));
    }

    private String userScore(long userId) {
        List<Game> byUserId = gameRepository.findByUserId(userId);
        long winGame = byUserId.stream().filter(Game::isGameWin).count();
        long looseGame = byUserId.stream().filter(game -> !game.isGameWin()).count();
        return String.format("Win game: %d, loose game: %d", winGame, looseGame);
    }

    private void saveMessage(Message message) {
        UserMessage userMessage = new UserMessage();
        long userId = message.getAuthor().get().getId().asLong();
        userMessage.setUserId(userId);
        userMessage.setMessage(message.getContent());
        userMessage.setReceivedDate(LocalDateTime.now());
        messageRepository.save(userMessage);
    }

    private void saveGame(Message message, boolean isPlayerWin) {
        Game game = new Game();
        long userId = message.getAuthor().get().getId().asLong();
        game.setUserId(userId);
        game.setGameWin(isPlayerWin);
        game.setReceivedDate(LocalDateTime.now());
        gameRepository.save(game);
    }

    private Mono<Void> buildResponse(Message eventMessage, String response) {
        Mono<Void> then = Mono.just(eventMessage)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(response))
                .then();
        return then;
    }

    private Mono<Void> playGame(Message eventMessage) {
        int randomAction = (int) (Math.random() * 3) + 1;
        if (randomAction == 1) {
            return buildResponse(eventMessage, messageSource
                    .getMessage("pongCase", null, null));
        }
        if (randomAction == 2) {
            botScore++;
            if (botScore == WIN_SCORE) {
                botScore = 0;
                playerScore = 0;
                saveGame(eventMessage, false);
                return buildResponse(eventMessage, messageSource
                        .getMessage("winBotCase", null, null));
            }
            return buildResponse(eventMessage, messageSource
                    .getMessage("outCase", new Object[]{author, playerScore, botScore}, null));
        } else {
            playerScore++;
            if (playerScore == WIN_SCORE) {
                botScore = 0;
                playerScore = 0;
                saveGame(eventMessage, true);
                return buildResponse(eventMessage, messageSource
                        .getMessage("winPlayerCase", new Object[]{author}, null));

            }
            return buildResponse(eventMessage, messageSource
                    .getMessage("missPointCase", new Object[]{author, playerScore, botScore}, null));
        }
    }
}
