package dev.enderoffice.events;

import dev.enderoffice.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(@NotNull net.dv8tion.jda.api.events.ReadyEvent event) {
        Main.guild = event.getJDA().getGuildById("493152212277657601");
    }

}
