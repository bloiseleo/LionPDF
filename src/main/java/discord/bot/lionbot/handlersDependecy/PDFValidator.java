package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.constants.PDF;
import discord.bot.lionbot.contracts.PDFAttachmentValidator;
import discord.bot.lionbot.errors.FileIsNotPdfException;
import discord.bot.lionbot.errors.PdfIsTooHeavyException;
import discord.bot.lionbot.errors.UploadPDFException;
import org.javacord.api.entity.Attachment;

public class PDFValidator implements PDFAttachmentValidator {
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
       if(pdf.getSize() > PDF.MAX_SIZE_OF_PDF.getMax()) {
           throw new PdfIsTooHeavyException(PDF.MAX_SIZE_OF_PDF.getMax());
       }
    }
}
