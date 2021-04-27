package dev.enderoffice.events;

import dev.enderoffice.twitch.MongoManager;
import dev.enderoffice.twitch.TwitchConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class QuitEvent extends ListenerAdapter {

    private final MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");

    @Override
    public void onGuildMemberLeave(@NotNull GuildMemberLeaveEvent event) {
        EmbedBuilder ver = new EmbedBuilder();
        ver.setTitle("Jemand verlässt uns :(");
        ver.setDescription("Hauste rein " + event.getUser().getName() + " bis bald!");
        ver.setColor(Color.red);
        ver.setThumbnail(event.getMember().getUser().getAvatarUrl());
        event.getGuild().getTextChannelById("778347360395853844").sendTyping().queue();
        event.getGuild().getTextChannelById("778347360395853844").sendMessage(ver.build()).queue();
        ver.clear();
        TwitchConnection tConn = new TwitchConnection();
        ArrayList<String> connected = mm.getAllUsersConnected();
        if(connected.contains(event.getMember().getUser().getName().toLowerCase())) {
            tConn.removeRequest(event.getMember().getUser().getName().toLowerCase());
        }
    }
}
