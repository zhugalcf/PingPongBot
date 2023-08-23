package com.zhugalcf.pingPongBot.listener;

import com.zhugalcf.pingPongBot.service.MessageService;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessageService messageService;

    public Mono<Void> processMessage(final Message eventMessage) {
        if (!eventMessage.getAuthor().get().isBot()){
            return messageService.getResponse(eventMessage);
        }
        return Mono.just(eventMessage).then();
    }
}
