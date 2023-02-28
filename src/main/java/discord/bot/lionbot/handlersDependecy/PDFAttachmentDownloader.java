package discord.bot.lionbot.handlersDependecy;

import org.javacord.api.entity.Attachment;

import java.io.*;

public class PDFAttachmentDownloader {
    /**
     * 1MB
     */
    private final int chunkSize = 1024;
    public boolean download(Attachment pdf) {
        boolean result = false;
        try(  BufferedInputStream bus = new BufferedInputStream(pdf.asInputStream()) ) {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(pdf.getFileName())
            );
            byte[] chunk = createChunkBasedOnBus(bus);
            int bytesReaded = bus.read(chunk);
            while (bytesReaded != -1) {
                if(bytesReaded != 0) {
                    bos.write(chunk);
                }
                chunk = createChunkBasedOnBus(bus);
                bytesReaded = bus.read(chunk);
            }
            bos.close();
            result = true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private byte[] createChunkBasedOnBus(InputStream bus) throws IOException {
        int size = chunkSize;
        if(bus.available() < chunkSize && bus.available() != 0) {
            size = bus.available();
        }
        return new byte[size];
    }
}
