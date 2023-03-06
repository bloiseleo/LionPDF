package discord.bot.lionbot.daos;

import discord.bot.lionbot.model.Metadata;

import java.util.Collection;

public interface DAO<T>{
    public void save(T data);
    public T get(int id);
    public Collection<Metadata> listItemsPaginated(int from, int to);
}
