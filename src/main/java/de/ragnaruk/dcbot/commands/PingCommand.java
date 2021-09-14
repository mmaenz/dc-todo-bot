package de.ragnaruk.dcbot.commands;

import discord4j.core.event.domain.interaction.SlashCommandEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PingCommand implements SlashCommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public Mono<Void> handle(SlashCommandEvent event) {
        return event.replyEphemeral("Pong!");
    }
}
