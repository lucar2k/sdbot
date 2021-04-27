package dev.enderoffice.commands;

import dev.enderoffice.twitch.TwitchConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CMD_Verify extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TwitchConnection twitchCon = new TwitchConnection();
        if(event.getChannel().getId().equalsIgnoreCase("778418416058236982")) {
            if(event.getMessage().getContentRaw().startsWith("!verify") || event.getMessage().getContentRaw().startsWith("!Verify")) {
                String[] args = event.getMessage().getContentRaw().split(" ");
                //Check if this Discord AccountID ! is already in any Document
                try {
                    if(!twitchCon.discordIDHasConnection(event.getAuthor().getId())) {
                        //Check if args[1] (TwitchName) ! is already a Document
                        if(!twitchCon.reqExists(args[1])) {
                            //Create Document with TwitchName and Verified: NaN + DiscordID
                            twitchCon.addTwitchVerification(args[1], event.getAuthor().getId(), event.getAuthor().getName().toLowerCase());
                            printSuccessEmbed(event, "Gebe nun !verify im Stream ein.");
                        } else {
                            printFallbackEmbed(event, "Dein Account befindet sich in der Verifizierung!");
                        }
                    } else {
                        printFallbackEmbed(event, "Dein Account ist bereits verbunden oder befindet sich in der Verifizierung!");
                    }
                }catch (Exception e) {
                    System.out.println("ERROR -> User failed input");
                }
            }
        }
    }

    private void printFallbackEmbed(MessageReceivedEvent event, String errorMSG) {
        event.getMessage().delete().queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Fehler - " + event.getAuthor().getName(), null);
        eb.setColor(Color.red);
        eb.addField("Grund", errorMSG, false);
        eb.setFooter("SoodBot - Developed by EnderOffice");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    private void printSuccessEmbed(MessageReceivedEvent event, String msg) {
        event.getMessage().delete().queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Schritt 1 Erledigt! - " + event.getAuthor().getName(), null);
        eb.setColor(Color.yellow);
        eb.addField("Wichtig!", "Nach 5 Minuten muss dein Account verbunden sein, sonst musst du Schritt 1 Wiederholen!", false);
        eb.setDescription(msg);
        eb.setFooter("SoodBot - Developed by EnderOffice");
        event.getChannel().sendMessage(eb.build()).queue();
    }

}
