package dev.enderoffice.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import dev.enderoffice.main.Main;
import org.apache.commons.lang.StringUtils;

public class Meister {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public Meister(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(IRCMessageEvent.class, event -> onMessage(event));
    }

    /**
     * Subscribe to the Follow Event
     */
    private void onMessage(IRCMessageEvent event) {
        String input = event.getMessage().toString();
        String cmd = StringUtils.substringBetween(input, "[", "]");
        TwitchConnection twitchConn = new TwitchConnection();
        if(cmd != null && cmd.equalsIgnoreCase("!meister")) {
            //If user Typed !discord
            event.getTwitchChat().sendMessage(event.getChannel().getName(), "Ich laufe auf der Version " + Main.currentVersion + " und EnderOffice ist mein Meister!");
        }
    }

}
