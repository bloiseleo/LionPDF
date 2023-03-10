package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.contracts.PDFAttachmentUploader;
import discord.bot.lionbot.dropbox.DropboxAPI;
import discord.bot.lionbot.errors.UploadError;
import org.javacord.api.entity.Attachment;

public final class PDFUploaderDropbox implements PDFAttachmentUploader {

    private DropboxAPI dropboxAPI;

    public PDFUploaderDropbox(DropboxAPI dropboxAPI) {
        this.dropboxAPI = dropboxAPI;
    }

    @Override
    public void upload(Attachment pdf, String description) throws UploadError {
        this.dropboxAPI.upload(pdf);
    }
}
