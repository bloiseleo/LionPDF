package discord.bot.lionbot.handlers;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.handlersDependecy.PDFAttachmentDownloader;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.Optional;

public class UploadCommandHandler extends DiscordCommandHandler {

    private final PDFAttachmentDownloader pdfAttachmentDownloader;

    public UploadCommandHandler(PDFAttachmentDownloader pdfAttachmentDownloader) {
        this.pdfAttachmentDownloader = pdfAttachmentDownloader;
    }
    @Override
    public void handle(Interaction interaction) throws IllegalAccessException {
        super.handle(interaction);
        SlashCommandInteraction commandInteraction = this.getCommandInteraction(interaction);
        Optional<SlashCommandInteractionOption> optionalPdfOption = commandInteraction.getOptionByName("pdf");
        if(optionalPdfOption.isEmpty()) {
            Main.getLogger().warning("pdf option was not provided - Optional<SlashCommandInteractionOption>");
            commandInteraction.createImmediateResponder()
                    .append("You should attach a PDF file to use this command")
                    .respond();
            return;
        }
        SlashCommandInteractionOption pdfOption = optionalPdfOption.get();
        Optional<Attachment> optionalPdf = pdfOption.getAttachmentValue();
        if (optionalPdf.isEmpty()) {
            Main.getLogger().warning("pdf option was not provided -  Optional<Attachment>");
            commandInteraction.createImmediateResponder()
                    .append("You should attach a PDF file to use this command")
                    .respond();
            return;
        }
        Attachment pdf = optionalPdf.get();
        Main.getLogger().info("Pdf found: " + pdf.getFileName());
        commandInteraction.createImmediateResponder().append("Your file is being uploaded to our servers. We will notify you when it's completed")
                .respond()
                .join();
        Main.getLogger().info("Client anwser completed");
        Main.getLogger().info("Creating thread to download file");
        Thread downloadThread = new Thread(() -> {
            Main.getLogger().info("THREAD TO DOWNLOAD FILE STARTED: " + pdf.getFileName() + " will be downloaded");
            User user = commandInteraction.getUser();
            if(!this.pdfAttachmentDownloader.download(pdf)) {
                user.sendMessage("Your file was not uploaded due an error. Contact the ADMIN");
                return;
            }
            user.sendMessage("Your file was uploaded successfully");
        });

        downloadThread.start();
    }
}
