package de.ragnaruk.dcbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.interaction.SlashCommandEvent;
import discord4j.rest.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import de.ragnaruk.dcbot.listeners.SlashCommandListener;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DcBotApplication {
	
	public Properties loadProperties(String file) {
	    Properties props = new Properties();
	    ClassLoader classLoader = getClass().getClassLoader();
	    try (InputStream fis = classLoader.getResourceAsStream(file)) {
	    	props.load(fis);
	    }
	    catch (IOException ioex) { 
	    	ioex.printStackTrace(); 
	    }
	    return props;
	}

	private static String BOT_TOKEN = new DcBotApplication().loadProperties("discord.properties").getProperty("BOT_TOKEN");
	
    public static void main(String[] args) {
    	//Start spring application
        ApplicationContext springContext = new SpringApplicationBuilder(DcBotApplication.class)
            .build()
            .run(args);

        //Login
        DiscordClientBuilder.create(BOT_TOKEN).build()
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
        return RestClient.create(BOT_TOKEN);
    }
}
