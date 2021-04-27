package dev.enderoffice.events;

import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public class JoinRank extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        EmbedBuilder ver = new EmbedBuilder();
        ver.setTitle("Neues Mitglied");
        ver.setDescription("Willkommen " + event.getUser().getName() + " auf Soods Discord Server!");
        ver.setColor(Color.GREEN);
        ver.setThumbnail(event.getMember().getUser().getAvatarUrl());
        event.getGuild().getTextChannelById("778347360395853844").sendTyping().queue();
        event.getGuild().getTextChannelById("778347360395853844").sendMessage(ver.build()).queue();
        ver.clear();
        Role mitglied = event.getGuild().getRoleById("778347113003352074");
        assert mitglied != null;
        if(!event.getMember().getRoles().contains(mitglied)) {
            event.getMember().getGuild().addRoleToMember(event.getMember(), mitglied).queue();
        }
    }
}


