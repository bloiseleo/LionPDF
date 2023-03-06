package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.contracts.PDFAttachmentUploader;
import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.errors.UploadError;
import discord.bot.lionbot.model.Metadata;
import org.javacord.api.entity.Attachment;

import java.io.*;

public class PDFAttachmentDownloader implements PDFAttachmentUploader {

    private MetadataDAO metadataDAO;

    public PDFAttachmentDownloader(MetadataDAO metadataDAO) {
        this.metadataDAO = metadataDAO;
    }
    private final String pathToSaveFile = new File("").getAbsolutePath();
    private final int chunkSize = 1024;
    public File download(Attachment pdf) throws UploadError {
        String path = pathToSaveFile + File.separator + pdf.getFileName();
        try(  BufferedInputStream bus = new BufferedInputStream(pdf.asInputStream()) ) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path)
            );
            byte[] chunk = createChunkBasedOnBus(bus);
            int bytesReaded = bus.read(chunk);
            Main.getLogger().finest("Download loop initiated");
            while (bytesReaded != -1) {
                if(bytesReaded != 0) {
                    bos.write(chunk);
                }
                chunk = createChunkBasedOnBus(bus);
                bytesReaded = bus.read(chunk);
            }
            Main.getLogger().finest("Download loop finished");
            bos.close();
            return new File(path);
        } catch (IOException exception) {
            throw new UploadError(exception);
        }
    }
    private byte[] createChunkBasedOnBus(InputStream bus) throws IOException {
        int size = chunkSize;
        if(bus.available() < chunkSize && bus.available() != 0) {
            size = bus.available();
        }
        return new byte[size];
    }
    @Override
    public void upload(Attachment pdf) throws UploadError {
        File pdfFile = this.download(pdf);
        this.metadataDAO.save(
                new Metadata(pdfFile.getAbsolutePath(), pdfFile.getName())
        );
    }
}
