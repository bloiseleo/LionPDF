package discord.bot.lionbot.contracts;

import org.javacord.api.entity.message.component.SelectMenuOption;

public interface AdapterToSelectMenuOption<T> {
    public SelectMenuOption from(T item);
}
