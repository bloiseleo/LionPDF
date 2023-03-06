package discord.bot.lionbot.model;

public class Metadata {
    private int id = -1;
    private String fullpath;
    private String name;
    public Metadata() {}
    public Metadata(String fullpath, String name) {
        setFullpath(fullpath);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public int getId() {
        return id;
    }

    public String getFullpath() {
        return fullpath;
    }
}
