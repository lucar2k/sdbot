package dev.enderoffice.commands;

import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class ChatClear extends TimerTask {

    @Override
    public void run() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("[" + strDate + "]" + " LOGGER: Clearing Verify Chat");
        Guild guild = Main.guild;
        MessageHistory history = new MessageHistory(guild.getTextChannelById("778418416058236982"));
        List<Message> msgs;
        msgs = history.retrievePast(100).complete();
        if(msgs.size() > 1) {
            guild.getTextChannelById("778418416058236982").deleteMessages(msgs).queue();
            guild.getTextChannelById("778418416058236982").sendTyping().queue();
            guild.getTextChannelById("778418416058236982").sendMessage("!veritut").queue();
        } else {
            System.out.println("[" + strDate + "]" + " LOGGER: Did not clear cheat because there are no Messages!");
        }
    }

}
