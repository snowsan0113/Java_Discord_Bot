package snowsan0113.discord_bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloWorldCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        JDA jda = event.getJDA();
        MessageChannelUnion channel = event.getChannel();
        if ("helloworld".equalsIgnoreCase(cmd)) {
            channel.sendMessage("HelloWorld").queue();
        }
    }
}
