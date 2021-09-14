package de.ragnaruk.dcbot.commands;

import discord4j.core.event.domain.interaction.SlashCommandEvent;
import reactor.core.publisher.Mono;

/**
 * A simple interface defining our slash command class contract.
 *  a getName() method to provide the case-sensitive name of the command.
 *  and a handle() method which will house all the logic for processing each command.
 */
public interface SlashCommand {

    String getName();

    Mono<Void> handle(SlashCommandEvent event);
}
