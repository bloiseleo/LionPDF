package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.Main;
import discord.bot.lionbot.errors.DownloadFailedException;
import org.javacord.api.entity.Attachment;

import java.io.*;

public class PDFAttachmentDownloader {
    /**
     * 1MB
     */
    private final int chunkSize = 1024;
    public void download(Attachment pdf) throws DownloadFailedException {
        try(  BufferedInputStream bus = new BufferedInputStream(pdf.asInputStream()) ) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(pdf.getFileName())
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
        } catch (IOException exception) {
            throw new DownloadFailedException(exception);
        }
    }

    private byte[] createChunkBasedOnBus(InputStream bus) throws IOException {
        int size = chunkSize;
        if(bus.available() < chunkSize && bus.available() != 0) {
            size = bus.available();
        }
        return new byte[size];
    }
}
