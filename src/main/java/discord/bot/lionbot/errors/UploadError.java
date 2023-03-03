package discord.bot.lionbot.errors;

import discord.bot.lionbot.Main;

public class UploadError extends UploadPDFException{
    public UploadError(Exception e) {
        super("There was an error while trying to upload the PDF");
        Main.getLogger().warning("ERROR => " + e.getMessage());
        e.printStackTrace();
    }
    @Override
    public String getUserFriendlyMessage() {
        return "There was an error while trying to upload the PDF. Contact the administrator with this message, the pdf, date and time that you're trying to use our services";
    }
}
