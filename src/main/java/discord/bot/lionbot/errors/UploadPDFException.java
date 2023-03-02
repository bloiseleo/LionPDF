package discord.bot.lionbot.errors;

import java.util.Arrays;

/**
 * This the base that every Error related to PDF while being uploaded must extends.
 * It's compile time checked, because the developer needs to do something.
 * Most of the times, you will return an error to an User, but YOU DO need to do something.
 */
public abstract class UploadPDFException extends Exception implements  HasUserFriendlyMessage{
    public UploadPDFException(String message) {
        super(message);
    }
    @Override
    public String getUserFriendlyMessage() {
        return "There was an error while trying to upload your file. Please report it with the date, time and PDF file you tried to use our services.";
    }
}
