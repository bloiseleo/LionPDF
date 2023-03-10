package discord.bot.lionbot.handlers;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.constants.AllMessagesComponents;
import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.handlersDependecy.PaginationService;
import discord.bot.lionbot.model.Metadata;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public class FileCommandHandler extends DiscordCommandHandler{

    private MetadataDAO metadataDAO;

    public FileCommandHandler(MetadataDAO metadataDAO) {
        this.metadataDAO = metadataDAO;
    }

    @Override
    public void handle(Interaction interaction) throws IllegalAccessException {
        super.handle(interaction);
        SlashCommandInteraction commandInteraction = getCommandInteraction(interaction);
        interaction.createImmediateResponder()
                .append("We are preparing a list with the files uploaded. Please, check your messages")
                .respond();
        processAsync(() -> {
            User user = commandInteraction.getUser();
            PaginationService<Metadata> paginationService = new PaginationService<>(metadataDAO, item -> SelectMenuOption.create(
                    "Name: " + item.getName(),
                    String.format("%d", item.getId()),
                    item.getDescription()
            )
            );
            MessageBuilder messageBuilder = new MessageBuilder();
            List<SelectMenuOption> options = paginationService.getStartList();
            Main.getLogger().warning("OPTIONS SIZE: " + options.size());
            if(options.size() < 1) {
                messageBuilder.append("There's any files uploaded yet!")
                        .send(user);
                return;
            }
            messageBuilder
                    .append("Select a file to download or see the next page")
                    .addComponents(
                            ActionRow.of(SelectMenu.createStringMenu(AllMessagesComponents.FILES_UPLOADED_LIST.name(), "Click here to show the options", options))
                    )
                    .send(user);
        });
    }
}
