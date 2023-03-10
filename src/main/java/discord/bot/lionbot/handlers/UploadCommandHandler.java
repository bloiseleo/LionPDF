package discord.bot.lionbot.handlers;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.embedMessages.UpdateAboutFileUploadEmbedMessage;
import discord.bot.lionbot.errors.UploadPDFException;
import discord.bot.lionbot.contracts.PDFAttachmentUploader;
import discord.bot.lionbot.handlersDependecy.PDFValidator;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import java.util.Optional;

public class UploadCommandHandler extends DiscordCommandHandler {

    private final PDFAttachmentUploader uploader;
    private final PDFValidator pdfValidator;

    public UploadCommandHandler( PDFAttachmentUploader uploader, PDFValidator pdfValidator) {
        this.uploader = uploader;
        this.pdfValidator = pdfValidator;
    }
    @Override
    public void handle(Interaction interaction) throws IllegalAccessException {
        super.handle(interaction);
        SlashCommandInteraction commandInteraction = this.getCommandInteraction(interaction);
        SlashCommandInteractionOption optionalPdfOption = (SlashCommandInteractionOption) extractSlashCommandInteraction(commandInteraction, "pdf");
        if(optionalPdfOption == null) {
            Main.getLogger().warning("pdf option was not provided - Optional<SlashCommandInteractionOption>");
            commandInteraction.createImmediateResponder()
                    .append("You should attach a PDF file to use this command")
                    .respond();
            return;
        }
        Optional<Attachment> optionalPdf = optionalPdfOption.getAttachmentValue();
        if (optionalPdf.isEmpty()) {
            Main.getLogger().warning("pdf option was not provided -  Optional<Attachment>");
            commandInteraction.createImmediateResponder()
                    .append("You should attach a PDF file to use this command")
                    .respond();
            return;
        }
        Attachment pdf = optionalPdf.get();
        SlashCommandInteractionOption descriptionOption = (SlashCommandInteractionOption) extractSlashCommandInteraction(commandInteraction, "description");
        if(descriptionOption == null) {
            Main.getLogger().warning("Description option was not provided");
            commandInteraction.createImmediateResponder()
                    .append("You should determine a description for your file")
                    .respond();
            return;
        }
        String description = descriptionOption.getStringValue().get();
        if(description.length() < 3 || description.length() > 50) {
            commandInteraction.createImmediateResponder()
                    .append("Description must have 3 to 50 charcters")
                    .respond();
            return;
        }
        commandInteraction.createImmediateResponder().append("Your file is being validated and uploaded to our servers. We will notify you when it's completed")
                .respond()
                .join();
        Main.getLogger().info("Client anwser completed");
        Main.getLogger().info("Creating thread to treat and download file");

        processAsync(() -> {
            User user = commandInteraction.getUser();
            try {
                this.pdfValidator.validate(pdf);
                this.uploader.upload(pdf, description);
                user.sendMessage(
                        new UpdateAboutFileUploadEmbedMessage("File Upload Status :)", "The upload of file " + pdf.getFileName() + " was a SUCCESS! NAILED IT!")
                                .get()
                );
            } catch (UploadPDFException uploadOrDownloadException) {
                Main.getLogger().warning(uploadOrDownloadException.getMessage());
                user.sendMessage(
                        new UpdateAboutFileUploadEmbedMessage(
                                "File Upload Status :(",
                                uploadOrDownloadException.getUserFriendlyMessage())
                                .get()
                ).join();
            }
            Main.getLogger().info("Thread finished");
        });
    }
}
