package discord.bot.lionbot.contracts;

/**
 * This interface is used when an exception created by us should return a User Friendly Message.
 */
public interface HasUserFriendlyMessage {
    public String getUserFriendlyMessage();
}
