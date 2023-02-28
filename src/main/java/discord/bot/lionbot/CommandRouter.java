package discord.bot.lionbot;

import discord.bot.lionbot.handlers.DiscordCommandHandler;
import org.javacord.api.interaction.SlashCommand;

import java.util.HashMap;

public class CommandRouter {
    private HashMap<Long, SlashCommand> commandsRegistered = new HashMap<>();
    private HashMap<Long, DiscordCommandHandler> commandHandlerRegistered = new HashMap<>();
    public void newCommand(SlashCommand command, DiscordCommandHandler commandHandler) {
        Main.getLogger().info("CREATING ROUTE TO THE COMMAND BASED ON UUID OF COMMAND");
        this.commandsRegistered.put(command.getId(), command);
        Main.getLogger().finest("Command of name " + command.getName() + " registered");
        this.commandHandlerRegistered.put(command.getId(), commandHandler);
        Main.getLogger().finest("Handler of name " + commandHandler.toString() + " registered");
        Main.getLogger().info("Command of name " + command.getName() + " registered with route to " + commandHandler);
    }
    public DiscordCommandHandler getHanlder(long id) {
         return this.commandHandlerRegistered.get(id);
    }
    public SlashCommand getCommand(long id) { return this.commandsRegistered.get(id); }
}
