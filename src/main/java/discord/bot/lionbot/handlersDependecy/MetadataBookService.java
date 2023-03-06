package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.daos.MetadataDAO;
import discord.bot.lionbot.model.Metadata;
import discord.bot.lionbot.model.MetadataBook;
import org.javacord.api.entity.message.component.SelectMenuOption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MetadataBookService {
    public MetadataDAO metadataDAO;
    public MetadataBookService(MetadataDAO metadataDAO) {
        this.metadataDAO = metadataDAO;
    }

    private List<SelectMenuOption> processAndCreateMetadataBook(int metadataID) {
        MetadataBook metadataBook = new MetadataBook(metadataDAO, metadataID);
        Collection<Metadata> metadados = metadataBook.getList();
        List<SelectMenuOption> metadadosMenuOption = new ArrayList<>();
        for (Metadata metadata: metadados) {
            metadadosMenuOption.add(
                    SelectMenuOption.create("Document Name: " + metadata.getName(), String.format("%d", metadata.getId()), "Click here to download this file")
            );
        }
        return metadadosMenuOption;
    }
    public List<SelectMenuOption> getMetadataBookFromListSelectedMenuOption(List<SelectMenuOption> listSelectMenuOption) {
        int start = 0;
        int metadataID = start;
        if(listSelectMenuOption != null) {
            start = listSelectMenuOption.size() - 2;
            metadataID = Integer.parseInt(listSelectMenuOption.get(start).getValue());
        }
       return processAndCreateMetadataBook(metadataID);
    }
    public List<SelectMenuOption> getMetadataBookFromOldMetadataBook(MetadataBook oldMetadataBook) {
        int start = 0;
        if(oldMetadataBook != null) {
            start = oldMetadataBook.getStart();
        }
        return processAndCreateMetadataBook(start);
    }
}
