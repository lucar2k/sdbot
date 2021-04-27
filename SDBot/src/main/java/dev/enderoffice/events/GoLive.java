package dev.enderoffice.events;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;

public class GoLive {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public GoLive(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelGoLiveEvent.class, event -> onLive(event));
    }

    /**
     * Subscribe to the Follow Event
     */

    private void onLive(ChannelGoLiveEvent event) {
        Guild guild = Main.guild;
        EmbedBuilder ver = new EmbedBuilder();
        ver.setTitle("Sood ist jetzt Live!");
        ver.setDescription(event.getStream().getTitle());
        ver.setImage(event.getStream().getThumbnailUrl());
        ver.setColor(Color.GREEN);
        guild.getTextChannelById("746357283939811438").sendTyping().queue();
        guild.getTextChannelById("746357283939811438").sendMessage(ver.build()).queue();
        ver.clear();
        guild.getTextChannelById("746357283939811438").getManager().setName("✔-live").queue();
        guild.getTextChannelById("746357283939811438").sendMessage("@here").queue();
    }

}
