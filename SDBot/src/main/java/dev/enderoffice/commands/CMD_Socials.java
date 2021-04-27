package dev.enderoffice.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CMD_Socials extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getChannel().getType().equals(ChannelType.TEXT)) {
            if(event.getMessage().getContentRaw().equalsIgnoreCase("!socials")) {
                Role techniker = event.getGuild().getRoleById("778341253816516668");
                if(event.getMessage().getMember().isOwner() || event.getMessage().getMember().getRoles().contains(techniker)) {
                    event.getMessage().delete().queue();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Socials", null);
                    eb.setColor(Color.red);
                    eb.addField("Steam", "https://steamcommunity.com/id/timolemsky", false);
                    eb.addField("YouTube", "https://www.youtube.com/user/SooD1887", false);
                    eb.addField("ESL", "https://play.eslgaming.com/player/2474731/", false);
                    eb.addField("Instagram", "https://www.instagram.com/sood_live/", false);
                    eb.setFooter("SoodBot - Developed by EnderOffice");
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            }
        }
    }
}
