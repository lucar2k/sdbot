package dev.enderoffice.commands;

import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CMD_BotUpdate extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith("!botupdate")) {
            if(event.getChannel().getId().equalsIgnoreCase("783691026937151539")) {
                event.getMessage().delete().queue();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Neues Bot Update! - " + Main.currentVersion, null);
                eb.setColor(Color.CYAN);
                String out = event.getMessage().getContentRaw().replace("!botupdate", ">");
                eb.addField("Feature", out, false);
                eb.setFooter("SoodBot - Developed by EnderOffice");
                event.getChannel().sendMessage(eb.build()).queue();
                event.getChannel().sendMessage("@here").queue();
            }
        }
    }
}
