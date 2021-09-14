package de.ragnaruk.dcbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.interaction.SlashCommandEvent;
import discord4j.rest.RestClient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import de.ragnaruk.dcbot.listeners.SlashCommandListener;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DcBotApplication {
	
    public static void main(String[] args) {
        //Start spring application
        ApplicationContext springContext = new SpringApplicationBuilder(DcBotApplication.class)
            .build()
            .run(args);

        //Login
        DiscordClientBuilder.create("ODg3MDI5NDEwNTc2ODY3MzQw.YT-Mgw.0OUFx3ojMCL-uj83sAXIaXHHofE").build()
            .withGateway(gatewayClient -> {
                SlashCommandListener slashCommandListener = new SlashCommandListener(springContext);

                Mono<Void> onSlashCommandMono = gatewayClient
                    .on(SlashCommandEvent.class, slashCommandListener::handle)
                    .then();
                return Mono.when(onSlashCommandMono);
            }).block();
    }

    @Bean
    public RestClient discordRestClient() {
        return RestClient.create("ODg3MDI5NDEwNTc2ODY3MzQw.YT-Mgw.0OUFx3ojMCL-uj83sAXIaXHHofE");
    }
}
