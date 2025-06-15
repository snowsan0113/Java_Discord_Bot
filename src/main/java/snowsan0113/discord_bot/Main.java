package snowsan0113.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import snowsan0113.discord_bot.command.HelloWorldCommand;
import snowsan0113.discord_bot.command.VoteCommand;
import snowsan0113.discord_bot.listener.StringSelectListener;
import snowsan0113.discord_bot.manager.ConfigManager;

import java.io.IOException;

public class Main extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] strings) throws InterruptedException, IOException {
        if (jda == null) {
            jda = JDABuilder.createDefault(ConfigManager.getToken())
                    .addEventListeners(new Main())
                    .addEventListeners(new HelloWorldCommand())
                    .addEventListeners(new VoteCommand())
                    .addEventListeners(new StringSelectListener())
                    .build();
            jda.awaitReady();

            jda.updateCommands()
                    .addCommands(Commands.slash("helloworld", "helloworldを出力する。"))
                    .addCommands(Commands.slash("vote", "投票コマンド")
                            .addOption(OptionType.STRING, "vote_option", "投票の選択し"))
                    .queue();
        }
    }

    public static JDA getJDA() {
        return jda;
    }

}
