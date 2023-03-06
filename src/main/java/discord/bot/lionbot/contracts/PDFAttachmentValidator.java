package discord.bot.lionbot.contracts;

import discord.bot.lionbot.errors.UploadPDFException;
import org.javacord.api.entity.Attachment;

public interface PDFAttachmentValidator {
    void validate(Attachment pdf) throws UploadPDFException;
}
