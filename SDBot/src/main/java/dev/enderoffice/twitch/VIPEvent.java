package dev.enderoffice.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import com.github.twitch4j.chat.events.channel.ListVipsEvent;
import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VIPEvent {

    private final MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public VIPEvent(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ListVipsEvent.class, event -> onVIP(event));
    }

    private void onVIP(ListVipsEvent event) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("[" + strDate + "]" + " LOGGER: Fetching VIPs");
        ArrayList<String> connected = Main.mm.getAllUsersConnected();
        ArrayList<String> vips = new ArrayList<>();
        event.getVips().forEach(vip -> {
            vips.add(vip.toLowerCase());
        });
        Role viprole = Main.guild.getRoleById("778346499213361213");
        for(String user : connected) {
            Member member = Main.guild.getMemberById(mm.getUsersDiscordID(user));
            if(vips.contains(user)) {
                if(!member.getRoles().contains(viprole)) {
                    //User is sub but does not have the sub role on discord
                    Main.guild.addRoleToMember(member, viprole).queue();
                }
            }
            if(member.getRoles().contains(viprole)) {
                if(!vips.contains(user)) {
                    Main.guild.removeRoleFromMember(member, viprole).queue();
                }
            }
        }
        System.out.println("[" + strDate + "]" + " LOGGER: Done Fetching VIPs");
        connected.clear();
        vips.clear();
    }

    /**
     * Subscribe to the Follow Event
     */

}
