package dev.enderoffice.events;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.entities.Guild;

public class GoOffline {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public GoOffline(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelGoOfflineEvent.class, event -> onOffline(event));
    }

    /**
     * Subscribe to the Follow Event
     */

    private void onOffline(ChannelGoOfflineEvent event) {
        Guild guild = Main.guild;
        guild.getTextChannelById("746357283939811438").getManager().setName("❌-live").queue();
    }

}

