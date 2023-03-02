package discord.bot.lionbot.errors;

import java.io.IOException;

public class DownloadFailedException extends IOException implements HasUserFriendlyMessage {
    public DownloadFailedException(IOException exception) {
        super("The download of the file uploaded via Discord was not possible to complete and thrown an IOException => MESSAGE: " + exception.getMessage());
        this.setStackTrace(exception.getStackTrace());
    }

    public String getUserFriendlyMessage() {
        return "The download of the file failed. Please report it to me with the date, time and PDF name that you tried to use my services.";
    }
}
