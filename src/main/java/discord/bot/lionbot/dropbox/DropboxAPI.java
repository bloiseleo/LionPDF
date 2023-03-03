package discord.bot.lionbot.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import discord.bot.lionbot.errors.UploadError;
import org.javacord.api.entity.Attachment;
import java.io.IOException;
import java.io.InputStream;
public class DropboxAPI {
    private String token;
    private DbxClientV2 client;
    public DropboxAPI(String token) {
        this.token = token;
        this.configureClient();
    }
    public void configureClient() {
        DbxRequestConfig config =DbxRequestConfig
                .newBuilder("lionbot")
                .build();
        this.client = new DbxClientV2(config, this.token);
    }
    public void upload(Attachment pdf) throws UploadError {
        try(InputStream in = pdf.asInputStream()) {
            FileMetadata metadata = client.files().uploadBuilder("/" + pdf.getFileName())
                    .uploadAndFinish(in);
            System.out.println(metadata);
        } catch (IOException | DbxException exception) {
            throw new UploadError(exception);
        }
    }
}
