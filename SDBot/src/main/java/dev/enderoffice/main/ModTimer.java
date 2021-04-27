package dev.enderoffice.main;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.credentialmanager.identityprovider.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.ClipList;
import com.github.twitch4j.helix.domain.ModeratorList;
import dev.enderoffice.twitch.MongoManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class ModTimer extends TimerTask {

    private final MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");

    @Override
    public void run() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("[" + strDate + "]" + " LOGGER: Fetching Moderators");
        ArrayList<String> connected = Main.mm.getAllUsersConnected();
        ArrayList<String> mods = getModerators();
        Role moderator = Main.guild.getRoleById("778346313259155456");
        for(String user : connected) {
            Member member = Main.guild.getMemberById(mm.getUsersDiscordID(user));
            if(mods.contains(user)) {
                if(!member.getRoles().contains(moderator)) {
                    Main.guild.addRoleToMember(member, moderator).queue();
                }
            }
            if(member.getRoles().contains(moderator)) {
                if(!mods.contains(user)) {
                    Main.guild.removeRoleFromMember(member, moderator).queue();
                }
            }
        }
        System.out.println("[" + strDate + "]" + " LOGGER: Done Fetching Moderators");
    }

    private ArrayList<String> getModerators() {
        //Might have to replace that token aswell same like in Main (OAuth of the Streamer)
        String token = "PRIVATE";
        String broadcasterId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(new OAuth2Credential("twitch", token)).map(OAuth2Credential::getUserId).get();
        String cursor = null;
        ModeratorList results = Main.twitchClient.getHelix().getModerators(token, broadcasterId, null, null).execute();
        ArrayList<String> mods = new ArrayList<>();
        results.getModerators().forEach(moderator -> {
            mods.add(moderator.getUserName().toLowerCase());
        });
        return mods;
    }

}
