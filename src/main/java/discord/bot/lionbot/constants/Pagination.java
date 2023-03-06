package discord.bot.lionbot.constants;

public enum Pagination {
    MAX_ITEM_PER_PAGE(10);
    private int max;
    Pagination(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

}
