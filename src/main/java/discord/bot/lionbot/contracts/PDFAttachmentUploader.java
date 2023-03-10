package discord.bot.lionbot.contracts;

import discord.bot.lionbot.errors.UploadError;
import org.javacord.api.entity.Attachment;

public interface PDFAttachmentUploader {
    public void upload(Attachment pdf, String description) throws UploadError;
}
