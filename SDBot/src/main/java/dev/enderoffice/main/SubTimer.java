package dev.enderoffice.main;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.credentialmanager.identityprovider.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.Moderator;
import com.github.twitch4j.helix.domain.ModeratorList;
import com.github.twitch4j.helix.domain.Subscription;
import com.github.twitch4j.helix.domain.SubscriptionList;
import dev.enderoffice.twitch.MongoManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubTimer extends TimerTask {

    private final MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");

    @Override
    public void run() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("[" + strDate + "]" + " LOGGER: Fetching Subscribers");
        ArrayList<String> connected = mm.getAllUsersConnected();
        ArrayList<String> subs = getSubs();
        Main.twitchClient.getChat().sendMessage("sood_live", "/vips");
        Role subscriber = Main.guild.getRoleById("778346782077878313");
        for(String user : connected) {
            Member member = Main.guild.getMemberById(mm.getUsersDiscordID(user));
            if(subs.contains(user)) {
                if(!member.getRoles().contains(subscriber)) {
                    //User is sub without Role
                    Main.guild.addRoleToMember(member, subscriber).queue();
                }
            }
            if(member.getRoles().contains(subscriber)) {
                if(!subs.contains(user)) {
                    Main.guild.removeRoleFromMember(member, subscriber).queue();
                }
            }
        }
        System.out.println("[" + strDate + "]" + " LOGGER: Done Fetching Subscribers");
    }

    private ArrayList<String> getSubs() {
        ArrayList<String> liste = new ArrayList<>();
        String token = "PRIVATE";
        String broadcasterId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(new OAuth2Credential("twitch", token)).map(OAuth2Credential::getUserId).get();
        String cursor = null;
        do {
            SubscriptionList results = Main.twitchClient.getHelix().getSubscriptions(token, broadcasterId, cursor, null, 100).execute();
            List<Subscription> subs = results.getSubscriptions();
            if (subs == null || subs.isEmpty() || Objects.equals(cursor, results.getPagination().getCursor())) {
                break;
            } else {
                for (Subscription sub : subs) {
                    liste.add(sub.getUserName().toLowerCase());
                }
            }
            cursor = results.getPagination().getCursor();
        } while (cursor != null && !cursor.isEmpty());
        return liste;
    }

}
