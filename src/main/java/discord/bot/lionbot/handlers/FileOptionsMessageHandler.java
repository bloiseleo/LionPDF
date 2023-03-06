package discord.bot.lionbot.handlers;

import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.handlersDependecy.MetadataBookService;
import discord.bot.lionbot.model.Metadata;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SelectMenuInteraction;
import java.io.File;
import java.util.List;

public class FileOptionsMessageHandler extends DiscordCommandHandler{

    private MetadataDAO metadataDAO;

    public FileOptionsMessageHandler(MetadataDAO metadataDAO) {
        this.metadataDAO = metadataDAO;
    }

    public void showNextPage(List<SelectMenuOption> possibleFiles, Interaction interaction) {
        MetadataBookService metadataBookService = new MetadataBookService(metadataDAO);
        List<SelectMenuOption> newListSelectOption = metadataBookService.getMetadataBookFromListSelectedMenuOption(possibleFiles);
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder
                .append("Select a file to download or see the next page")
                .addComponents(
                        ActionRow.of(SelectMenu.createStringMenu("fileoptions", "Click here to show the options", newListSelectOption))
                )
                .send(interaction.getUser());
    }

    public void sendFile(Metadata metadata, Interaction interaction) {
        File file = new File(metadata.getFullpath());
        new MessageBuilder()
                .append("Here is the file!")
                .addAttachment(file)
                .send(interaction.getUser());
    }

    @Override
    public void handle(Interaction interaction) throws IllegalAccessException {
        SelectMenuInteraction menuInteractionFiles = getSelectMenuInteraction(interaction);
        menuInteractionFiles.createImmediateResponder()
                .append("We are processing your response, please wait")
                .respond()
                .join();
        processAsync(() -> {
            List<SelectMenuOption> possibleFiles = menuInteractionFiles.getPossibleOptions();
            List<SelectMenuOption> selectedFile = menuInteractionFiles.getChosenOptions();
            int metadataID = Integer.parseInt(selectedFile.get(0).getValue());
            if(metadataID == -1) {
                showNextPage(possibleFiles, interaction);
                return;
            }
            sendFile(metadataDAO.get(metadataID), interaction);
        });

    }
}
