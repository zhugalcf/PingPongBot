package com.zhugalcf.pingPongBot.service;

import com.zhugalcf.pingPongBot.entity.UserMessage;
import com.zhugalcf.pingPongBot.listener.EventListener;
import com.zhugalcf.pingPongBot.listener.MessageListener;
import com.zhugalcf.pingPongBot.repository.MessageRepository;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageCreateService implements EventListener<MessageCreateEvent> {

    private final MessageListener messageListener;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return messageListener.processMessage(event.getMessage());
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        return EventListener.super.handleError(error);
    }
}
