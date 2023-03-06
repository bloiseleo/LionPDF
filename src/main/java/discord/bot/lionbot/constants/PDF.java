package discord.bot.lionbot.constants;

public enum PDF {
    MAX_SIZE_OF_PDF();
    private int max;

    PDF() {
        max = (int) Math.pow(2, 20) * 8;
    }

    public int getMax() {
        return max;
    }
}
