package discord.bot.lionbot.embedMessages;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import java.awt.*;
import java.io.File;

public class UpdateAboutFileUploadEmbedMessage {
    private final String title = "Upload File Result";
    private final String description = "This is an update about your file upload recently made";
    private final Color orange = Color.decode("#D56736");
    private final String author = "Leonardo Bloise";
    private String titleOfContent;
    private String content;
    public UpdateAboutFileUploadEmbedMessage(String title, String content) {
        this.titleOfContent = title;
        this.content = content;
    }

    public EmbedBuilder get() {
        return new EmbedBuilder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setAuthor(this.author)
                .setColor(this.orange)
                .addField(this.titleOfContent, this.content);
    }
}
