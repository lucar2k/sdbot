package dev.enderoffice.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CMD_VeriTut extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getChannel().getType().equals(ChannelType.TEXT) && event.getChannel().getId().equalsIgnoreCase("778418416058236982")) {
            if(event.getMessage().getContentRaw().equalsIgnoreCase("!veritut")) {
                Role techniker = event.getGuild().getRoleById("778341253816516668");
                Role bot = event.getGuild().getRoleById("778346958023819285");
                if(event.getMessage().getMember().isOwner() || event.getMessage().getMember().getRoles().contains(techniker) || event.getMessage().getMember().getRoles().contains(bot)) {
                    event.getMessage().delete().queue();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Wie verbinde ich meinen Twitch Account?", null);
                    eb.setColor(Color.red);
                    eb.addField("Schritt 1:", "!verify (Twitchname) in diesen Chat eingeben z.B. !verify Max_Mustermann", false);
                    eb.addField("Schritt 2:", "!verify im SooD_Live Twitch Chat eingeben", false);
                    eb.addField("Schritt 3:", "Fertig! Der Bot wird sich im Stream für das Verbinden bedanken. Es kann einen Moment dauern für die synchronisation.", false);
                    eb.setFooter("SoodBot - Developed by EnderOffice");
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            }
        }
    }
}
