package dev.enderoffice.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import org.apache.commons.lang.StringUtils;

public class VerifyCommand {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public VerifyCommand(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(IRCMessageEvent.class, event -> onMessage(event));
    }

    /**
     * Subscribe to the Follow Event
     */
    private void onMessage(IRCMessageEvent event) {
        String input = event.getMessage().toString();
        String cmd = StringUtils.substringBetween(input, "[", "]");
        TwitchConnection twitchConn = new TwitchConnection();
        if(cmd != null && cmd.equalsIgnoreCase("!verify")) {
            //If user Typed !verify
            if(twitchConn.reqExists(event.getUser().getName().toLowerCase())) {
                //If a User Exists
                if(twitchConn.getRequestStatus(event.getUser().getName().toLowerCase()).equalsIgnoreCase("none")) {
                    //If the User is not verified
                    twitchConn.updateRequestStatus(event.getUser().getName().toLowerCase());
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "Danke fürs Verbinden deines Accounts " + event.getUser().getName() + "!");
                }
            }
        }
    }

}
