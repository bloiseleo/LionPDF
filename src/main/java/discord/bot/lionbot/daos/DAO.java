package discord.bot.lionbot.daos;

import java.util.Collection;

public interface DAO<T>{
    public void save(T data);
    public T get(int id);
    public Collection<T> listAll();
}
