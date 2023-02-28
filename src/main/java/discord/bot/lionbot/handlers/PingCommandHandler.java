package discord.bot.lionbot.handlers;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Optional;

public class PingCommandHandler extends DiscordCommandHandler{
    @Override
    public void handle(Interaction interaction) throws IllegalAccessException {
        super.handle(interaction);
        Optional<SlashCommandInteraction> slashCommandInteraction =  interaction.asSlashCommandInteraction();
        if(slashCommandInteraction.isEmpty()) {
            throw new IllegalAccessException("I should not be used to interactions different tha SlashCommandInteraction");
        }
        SlashCommandInteraction realInteraction = slashCommandInteraction.get();
        realInteraction.createImmediateResponder()
                .append("pong")
                .respond();
    }
}
