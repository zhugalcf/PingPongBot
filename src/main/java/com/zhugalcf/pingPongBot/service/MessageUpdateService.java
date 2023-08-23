package com.zhugalcf.pingPongBot.service;

import com.zhugalcf.pingPongBot.listener.EventListener;
import com.zhugalcf.pingPongBot.listener.MessageListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageUpdateService implements EventListener<MessageUpdateEvent> {

    private final MessageListener messageListener;
    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(messageListener::processMessage);
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        return EventListener.super.handleError(error);
    }
}
