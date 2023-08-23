package com.zhugalcf.pingPongBot.service;

import com.zhugalcf.pingPongBot.repository.GameRepository;
import com.zhugalcf.pingPongBot.repository.MessageRepository;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
    @Mock
    MessageSource messageSource;
    @Mock
    GameRepository gameRepository;
    @Mock
    Message eventMessage;
    @Mock
    User author;
    @Mock
    Snowflake snowflake;

    @Test
    void testWhenUserSendPingMessage() {

        when(author.getUsername()).thenReturn("User");
        when(author.getId()).thenReturn(snowflake);
        when(snowflake.asLong()).thenReturn(1L);
        when(eventMessage.getAuthor()).thenReturn(Optional.of(author));
        when(eventMessage.getContent()).thenReturn("!ping");

        messageService.getResponse(eventMessage);
        verify(messageRepository).save(any());
    }

    @Test
    void testWhenUserSendGetMyGamesMessage() {

        when(author.getUsername()).thenReturn("User");
        when(author.getId()).thenReturn(snowflake);
        when(snowflake.asLong()).thenReturn(1L);
        when(eventMessage.getAuthor()).thenReturn(Optional.of(author));
        when(eventMessage.getContent()).thenReturn("!getMyGames");

        messageService.getResponse(eventMessage);
        verify(messageRepository).save(any());
        verify(gameRepository).findByUserId(anyLong());
    }

    @Test
    void testWhenUserSendAnyMessage() {

        when(author.getUsername()).thenReturn("User");
        when(eventMessage.getAuthor()).thenReturn(Optional.of(author));
        when(eventMessage.getContent()).thenReturn("any message");

        messageService.getResponse(eventMessage);
        verify(messageSource).getMessage("welcomeMessage", new Object[]{author.getUsername()}, null);
    }
}