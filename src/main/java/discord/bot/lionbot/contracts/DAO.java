package discord.bot.lionbot.contracts;

import discord.bot.lionbot.model.Metadata;

import java.util.Collection;
import java.util.List;

public interface DAO<T>{
    public void save(T data);
    public T get(int id);
    public List<T> listItemsPaginated(int from, int to);
}
