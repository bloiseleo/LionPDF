package discord.bot.lionbot.handlers;

import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.handlersDependecy.MetadataBookService;
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
        MetadataBookService metadataBookService = new MetadataBookService(metadataDAO);
        processAsync(() -> {
            User user = commandInteraction.getUser();
            List<SelectMenuOption> metadadosMenuOption = metadataBookService.getMetadataBookFromOldMetadataBook(null);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder
                    .append("Select a file to download or see the next page")
                    .addComponents(
                            ActionRow.of(SelectMenu.createStringMenu("fileoptions", "Click here to show the options", metadadosMenuOption))
                    )
                    .send(user);
        });
    }
}
