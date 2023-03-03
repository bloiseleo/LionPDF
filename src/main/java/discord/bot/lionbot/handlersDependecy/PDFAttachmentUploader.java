package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.errors.UploadError;
import org.javacord.api.entity.Attachment;

public interface PDFAttachmentUploader {
    public void upload(Attachment pdf) throws UploadError;
}
