package dev.enderoffice.main;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import dev.enderoffice.commands.*;
import dev.enderoffice.events.*;
import dev.enderoffice.twitch.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Timer;

public class Main {

    public static Guild guild;
    public static String currentVersion = "0.1.7";
    public static MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");
    public static TwitchClient twitchClient;
    @Deprecated
    //Dont have to replace that Token
    private final static JDABuilder builder = JDABuilder.createDefault("TOKEN");

    public static void main(String[] args) throws LoginException {
        System.out.println("LOGGER: Starting to Build Bot");
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setActivity(Activity.playing("SoodBot Version: " + currentVersion));
        registerCommands();
        registerEvents();
        loadTwitchBot();
        registerTwitchFeatures();
        builder.build();
        Timer timer = new Timer();
        timer.schedule(new RequestTimer(), 0, 300 * 1000);
        timer.schedule(new SubTimer(), 2000, 300 * 1000);
        timer.schedule(new ModTimer(), 2000, 300 * 1000);
        timer.schedule(new ChatClear(), 2000, 900 * 1000);
    }

    private static void loadTwitchBot() {
        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();
        //Might have to replace that OAuth for The Bot Account SoodBotDC
        OAuth2Credential credential = new OAuth2Credential("twitch", "PRIVATE");
        twitchClient = clientBuilder
                .withClientId("PRIVATE")
                .withClientSecret("PRIVATE")
                .withEnableHelix(true)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableGraphQL(true)
                .withEnableKraken(true)
                .build();
        twitchClient.getChat().joinChannel("sood_live");
    }

    private static void registerTwitchFeatures() {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
        VerifyCommand verifyCMD = new VerifyCommand(eventHandler);
        DiscordCommand discordCMD = new DiscordCommand(eventHandler);
        Meister meisterCMD = new Meister(eventHandler);
        GoLive goLive = new GoLive(eventHandler);
        GoOffline goOffline = new GoOffline(eventHandler);
        VIPEvent vipEvent = new VIPEvent(eventHandler);
        twitchClient.getClientHelper().enableStreamEventListener("sood_live");
    }

    private static void registerEvents() {
        builder.addEventListeners(new JoinRank());
        builder.addEventListeners(new QuitEvent());
        builder.addEventListeners(new ReadyEvent());
        System.out.println("LOGGER: Registered all Events");
    }

    private static void registerCommands() {
        builder.addEventListeners(new CMD_VeriTut());
        builder.addEventListeners(new CMD_Socials());
        builder.addEventListeners(new CMD_Verify());
        builder.addEventListeners(new CMD_BotUpdate());
        System.out.println("LOGGER: Registered all Commands");
    }

}
