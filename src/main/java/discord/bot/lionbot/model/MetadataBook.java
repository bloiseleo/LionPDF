package discord.bot.lionbot.model;

import discord.bot.lionbot.daos.MetadataDAO;

import java.util.Collection;

public class MetadataBook {
    private MetadataDAO metadataDAO;
    private int start = 0;
    private int qtdItems = 10;
    private int end;
    public MetadataBook(MetadataDAO metadataDAO) {
        end = qtdItems + start;
        this.metadataDAO = metadataDAO;
    }
    public MetadataBook(MetadataDAO metadataDAO, int start) {
        this.start = start;
        end = start + qtdItems;
        this.metadataDAO = metadataDAO;
    }
    public Collection<Metadata> getList() {
        Collection<Metadata> list = metadataDAO.listItemsPaginated(start, end);
        Metadata metadata = new Metadata();
        metadata.setName("Próxima página");
        list.add(metadata);
        return list;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
