package discord.bot.lionbot;

import discord.bot.lionbot.builders.CommandBuilder;
import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.database.Database;
import discord.bot.lionbot.handlers.DiscordCommandHandler;
import discord.bot.lionbot.handlers.PingCommandHandler;
import discord.bot.lionbot.handlers.UploadCommandHandler;
import discord.bot.lionbot.handlersDependecy.PDFAttachmentDownloader;
import discord.bot.lionbot.handlersDependecy.PDFValidator;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.interaction.SlashCommandOption;
import java.io.IOException;
import java.util.logging.*;

public class Main {
    private final static Logger logger = Logger.getLogger("LionBOT");

    public static Logger getLogger() {
        return logger;
    }

    /**
     * Base knowledge to understand logging configuration
     * https://docs.oracle.com/javase/6/docs/technotes/guides/logging/overview.html
     */
    private static void configureLogger() {
        logger.info("Creating and setting logger to work properly");
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
        try {
            String name = "lionbot.log";
            logger.info("Trying to create log file named as " + name);
            Handler handler2 = new FileHandler(name);
            handler2.setLevel(Level.ALL);
            logger.addHandler(handler2);
            logger.finest("FileHandler to logger created and setted successfully");
        } catch (IOException exception) {
            logger.throwing("Main", "configureLogger", exception);
        }
    }
    public static void main(String[] args) {

        configureLogger();
        Database database = configureDatabase();
        MetadataDAO metadataDAO = new MetadataDAO(database);
        DiscordApi discordApi = configureBot();
        logger.finest("Bot created successfully");
        CommandRouter commandRouter = new CommandRouter();
        CommandBuilder commandBuilder = new CommandBuilder(commandRouter);
        logger.finest("Command Router and Builder created to easily add features to the bot");

        commandBuilder.createGlobalCommandFor(discordApi)
                .setHandler(new PingCommandHandler())
                .setNameAndDescription("ping", "test if connection and anwser is ok")
                .build();

        commandBuilder.createGlobalCommandFor(discordApi)
                .setHandler(new UploadCommandHandler(
                        new PDFAttachmentDownloader(metadataDAO),
                        new PDFValidator()
                ))
                .setNameAndDescription("uploadpdf", "Save your PDF")
                .setOptions(SlashCommandOption.createAttachmentOption("pdf", "The pdf file you want to save", true))
                .build();

        discordApi.addSlashCommandCreateListener(event -> {
            DiscordCommandHandler command1 = commandRouter.getHanlder(event.getSlashCommandInteraction().getCommandId());
            try {
                command1.handle(event.getInteraction());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    private static Database configureDatabase() {
        Database database =  new Database();
        database.connect();
        Main.getLogger().finest("Database connected");
        return database;
    }
    private static DiscordApi configureBot() {
        Dotenv dotenv = Dotenv.load();
        logger.finest("Creating bot with token: " + dotenv.get("DISCORD_KEY"));
        return new DiscordApiBuilder()
                .setToken(dotenv.get("DISCORD_KEY"))
                .addIntents(Intent.MESSAGE_CONTENT)
                .addIntents(Intent.DIRECT_MESSAGES)
                .login()
                .join();
    }

}
