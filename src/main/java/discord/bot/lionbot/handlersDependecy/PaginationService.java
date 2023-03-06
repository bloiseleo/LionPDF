package discord.bot.lionbot.handlersDependecy;

import discord.bot.lionbot.constants.Pagination;
import discord.bot.lionbot.contracts.AdapterToSelectMenuOption;
import discord.bot.lionbot.contracts.DAO;
import org.javacord.api.entity.message.component.SelectMenuOption;
import java.util.ArrayList;
import java.util.List;

public class PaginationService<T> {
    private final DAO<T> dataDAO;
    private final AdapterToSelectMenuOption<T> adapter;
    public PaginationService(DAO<T> dataDAO, AdapterToSelectMenuOption<T> adapter) {
        this.dataDAO = dataDAO;
        this.adapter = adapter;
    }
    private void removePageMarker(List<SelectMenuOption> list) {
        list.remove(list.size() - 1);
    }

    private List<SelectMenuOption> generateList(int start, int end) {
        List<T> items = dataDAO.listItemsPaginated(start, end);
        List<SelectMenuOption> itemsToSelect = new ArrayList<>();
        for(T item: items) {
            itemsToSelect.add(
                    this.adapter.from(item)
            );
        }
        return itemsToSelect;
    }

    public List<SelectMenuOption> getStartList() {
        int start = 0;
        int end = start + Pagination.MAX_ITEM_PER_PAGE.getMax();
        return generateList(start, end);
    }
    public List<SelectMenuOption> continueOldList(List<SelectMenuOption> oldList) {
        removePageMarker(oldList);
        int start = Integer.parseInt(oldList.get(oldList.size() - 1).getValue());
        int end = start + Pagination.MAX_ITEM_PER_PAGE.getMax();
        return generateList(start, end);
    }
}
