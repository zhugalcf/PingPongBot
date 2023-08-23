package com.zhugalcf.pingPongBot.listener;

import com.zhugalcf.pingPongBot.service.MessageService;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageListenerTest {

    @InjectMocks
    MessageListener messageListener;
    @Mock
    MessageService messageService;
    @Mock
    Message eventMessage;
    @Mock
    User author;

    @Test
    void testProcessMessage() {

        when(eventMessage.getAuthor()).thenReturn(Optional.of(author));
        when(author.isBot()).thenReturn(false);

        messageListener.processMessage(eventMessage);
        verify(messageService).getResponse(eventMessage);
    }
}