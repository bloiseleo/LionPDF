package discord.bot.lionbot.handlers;

import discord.bot.lionbot.Main;
import org.javacord.api.entity.Attachment;
import org.javacord.api.interaction.*;

import javax.swing.text.html.Option;
import java.util.Optional;

public abstract class DiscordCommandHandler {

    protected SlashCommandInteraction getCommandInteraction(Interaction interaction) throws IllegalAccessException {
        Optional<SlashCommandInteraction> slashCommandInteraction = interaction.asSlashCommandInteraction();
        if(slashCommandInteraction.isEmpty()) {
            if(slashCommandInteraction.isEmpty()) {
                throw new IllegalAccessException("I should not be used to interactions different that SlashCommandInteraction");
            }
        }
        return slashCommandInteraction.get();
    }

    protected SelectMenuInteraction getSelectMenuInteraction(Interaction interaction) throws IllegalAccessException {
        MessageComponentInteraction messageComponentInteraction = getMessageComponentInteraction(interaction);
        Optional<SelectMenuInteraction> optionalSelectMenuInteraction = messageComponentInteraction.asSelectMenuInteraction();
        if(optionalSelectMenuInteraction.isEmpty()) {
            throw new IllegalAccessException("I should not be used to interactions different that SelectMenuInteraction");
        }
        return optionalSelectMenuInteraction.get();
    }

    protected MessageComponentInteraction getMessageComponentInteraction(Interaction interaction) throws IllegalAccessException {
        Optional<MessageComponentInteraction> optionalMessageComponentInteraction = interaction.asMessageComponentInteraction();
        if(optionalMessageComponentInteraction.isEmpty()) {
            throw new IllegalAccessException("I should not be used to interactions different that MessageCommnadInteraction");
        }
        return optionalMessageComponentInteraction.get();
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

    protected void processAsync(Runnable runnable) {
        Thread process = new Thread(runnable);
        process.start();
    }

    protected Object extractSlashCommandInteraction(SlashCommandInteraction commandInteraction, String optionName) {
        Optional<SlashCommandInteractionOption> optionalOption = commandInteraction.getOptionByName(optionName);
        if(optionalOption.isEmpty()) {
            return null;
        }
        return optionalOption.get();
    }
}
