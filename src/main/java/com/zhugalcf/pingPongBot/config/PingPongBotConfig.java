package com.zhugalcf.pingPongBot.config;

import com.zhugalcf.pingPongBot.listener.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PingPongBotConfig {

    @Value("${discord.token}")
    private String token;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient (final List<EventListener<T>> eventListeners){
        final GatewayDiscordClient client = DiscordClientBuilder.create(getToken())
                .build()
                .login()
                .block();

        for (final EventListener<T> listener : eventListeners){
            client.on(listener.getEventType())
                    .flatMap(listener::execute)
                    .onErrorResume(listener::handleError)
                    .subscribe();
        }
        return client;
    }

    private String getToken(){
        char[] charArray = token.toCharArray();
        char[] chars = new char[charArray.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (charArray[i] - 2);
        }
        return new String(chars);
    }
}
