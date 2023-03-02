package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.errors.FileIsNotPdfException;
import discord.bot.lionbot.errors.PdfIsTooHeavyException;
import discord.bot.lionbot.errors.UploadPDFException;
import org.javacord.api.entity.Attachment;

public class PDFValidator {
    /**
     * 8MB
     */
    private final int MAX_SIZE_OF_PDF = (int) Math.pow(2, 20) * 8;

    private String getExtensionOfAFile(String filename) {
        int indexOfLastDot = filename.lastIndexOf('.');
        if(indexOfLastDot == -1) {
            return null;
        }
        return filename.substring(indexOfLastDot + 1);
    }

    public void validate(Attachment pdf) throws UploadPDFException {
       String filename = pdf.getFileName();
       String extension = getExtensionOfAFile(filename);

       if(!extension.equals("pdf")) {
           throw new FileIsNotPdfException(filename);
       }
       if(pdf.getSize() > MAX_SIZE_OF_PDF) {
           throw new PdfIsTooHeavyException(MAX_SIZE_OF_PDF);
       }
    }
}
