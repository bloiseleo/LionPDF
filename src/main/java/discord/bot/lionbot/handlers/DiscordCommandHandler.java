package discord.bot.lionbot.handlers;

import discord.bot.lionbot.Main;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Optional;

public abstract class DiscordCommandHandler {

    protected SlashCommandInteraction getCommandInteraction(Interaction interaction) throws IllegalAccessException {
        Optional<SlashCommandInteraction> slashCommandInteraction = interaction.asSlashCommandInteraction();
        if(slashCommandInteraction.isEmpty()) {
            if(slashCommandInteraction.isEmpty()) {
                throw new IllegalAccessException("I should not be used to interactions different tha SlashCommandInteraction");
            }
        }
        return slashCommandInteraction.get();
    }

    /**
     * It will handle the command interaction and anwser it through the interaction object.
     */
    public  void handle(Interaction interaction) throws IllegalAccessException {
        Main.getLogger().info("HANDLE CALLED TO " + this);
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
