package de.ragnaruk.dcbot.commands;

import discord4j.core.event.domain.interaction.SlashCommandEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.rest.util.ApplicationCommandOptionType;

import java.util.List;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AddCommand implements SlashCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public Mono<Void> handle(SlashCommandEvent event) {
        /*
        Since slash command options are optional according to discal, we will wrap it into the following function
        that gets the value of our option as a Mono<String>, so that we may use it later on without
        needing to call .get() several times on our optional values.

        In this case, there is no fear the mono will return empty as this is marked "required: true" in our json.
         */
        event.acknowledgeEphemeral();
    	
    	Mono<String> nameMono = Mono.justOrEmpty(event.getOption("name")
            .flatMap(ApplicationCommandInteractionOption::getValue)
        ).map(ApplicationCommandInteractionOptionValue::asString);
        //String json = (event.getOption("item").flatMap(ApplicationCommandInteractionOption::getValue)).map(ApplicationCommandInteractionOptionValue::asString).get();
    	//List<ApplicationCommandInteractionOption> options = event.getOptions();    
    	
        return nameMono.flatMap(name -> {
            //Reply to the slash command, with the name the user supplied
            return event.replyEphemeral("Item " + name + " added.");
        });
    }
}
