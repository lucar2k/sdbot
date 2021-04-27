package dev.enderoffice.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import org.apache.commons.lang.StringUtils;

public class DiscordCommand {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public DiscordCommand(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(IRCMessageEvent.class, event -> onMessage(event));
    }

    /**
     * Subscribe to the Follow Event
     */
    private void onMessage(IRCMessageEvent event) {
        String input = event.getMessage().toString();
        String cmd = StringUtils.substringBetween(input, "[", "]");
        TwitchConnection twitchConn = new TwitchConnection();
        if(cmd != null && cmd.equalsIgnoreCase("!discord") || cmd.equalsIgnoreCase("!dc")) {
            //If user Typed !discord
            event.getTwitchChat().sendMessage(event.getChannel().getName(), "Join unserer Discord-Community. Du hast dort auch die Möglichkeit dich zu verifizieren und die Rechte zu bekommen, die du auch im Stream hast (Mod, Sub, Vip, Follow). https://discord.gg/2u75wAqPnx");
        }
    }

}
