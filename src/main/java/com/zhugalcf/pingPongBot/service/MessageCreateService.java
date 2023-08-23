package com.zhugalcf.pingPongBot.service;

import com.zhugalcf.pingPongBot.listener.EventListener;
import com.zhugalcf.pingPongBot.listener.MessageListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Slf4j
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
        log.error("Message create event error", error);
        return EventListener.super.handleError(error);
    }
}
