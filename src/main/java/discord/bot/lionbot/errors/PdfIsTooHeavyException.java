package discord.bot.lionbot.errors;

public class PdfIsTooHeavyException extends UploadPDFException{
    public PdfIsTooHeavyException(int MAX_SIZE) {
        super("The PDF uploaded is too heavy. Only upload sizing less then 8MB PDFs are " + MAX_SIZE);
    }

    @Override
    public String getUserFriendlyMessage() {
        return "The PDF uploaded is too heavy. Try to upload something with less than 8MB";
    }
}
