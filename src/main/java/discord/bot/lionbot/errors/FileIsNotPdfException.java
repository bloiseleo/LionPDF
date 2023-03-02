package discord.bot.lionbot.errors;

/**
 * This exception is meant to be thrown when a file recieved from Discord server is not a PDF.
 */
public class FileIsNotPdfException extends UploadPDFException {

    private String filename;

    public FileIsNotPdfException(String filename) {
        super("The file of name \"" + filename + "\" is not a valid PDF. Check the extension of it");
        this.filename = filename;
    }

    @Override
    public String getUserFriendlyMessage() {
        return "This file named as \"" + this.filename + "\" is not a valid file! >:(";
    }
}
