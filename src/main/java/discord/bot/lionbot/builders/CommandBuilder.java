package discord.bot.lionbot.builders;

import discord.bot.lionbot.CommandRouter;
import discord.bot.lionbot.Main;
import discord.bot.lionbot.handlers.DiscordCommandHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    private CommandRouter router;
    private DiscordApi discordApi;
    private String commandName;
    private String commandDescription;

    private List<SlashCommandOption> options = new ArrayList<>();

    private DiscordCommandHandler handler;
    public CommandBuilder(CommandRouter commandRouter) {
        this.router = commandRouter;
    }
    public CommandBuilder createGlobalCommandFor(DiscordApi discordApi) {
        Main.getLogger().finest("Setting GlobalCommand whose token is: " + discordApi.getToken());
        this.discordApi = discordApi;
        return this;
    }
    public CommandBuilder setNameAndDescription(String name, String description) {
        Main.getLogger().finest("Setting name to " + name + " and description to " + description);
        this.commandName = name;
        this.commandDescription = description;
        return this;
    }

    public CommandBuilder setOptions(SlashCommandOption commandOption) {
        Main.getLogger().finest("Adding command option named as " + commandOption.getName());
        this.options.add(commandOption);
        return this;
    }

    public CommandBuilder setHandler(DiscordCommandHandler discordCommandHandler) {
        Main.getLogger().finest("Adding command handler to anwser and treat interaction whose class is named as " + discordCommandHandler);
        this.handler = discordCommandHandler;
        return this;
    }
    public SlashCommand build() {
        SlashCommand command = SlashCommand.with(this.commandName, this.commandDescription)
                .setOptions(this.options)
                .createGlobal(this.discordApi)
                .join();
        router.newCommand(command, this.handler);
        return command;
    }
}
