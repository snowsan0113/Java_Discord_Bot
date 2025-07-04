package snowsan0113.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import snowsan0113.discord_bot.command.HelloWorldCommand;
import snowsan0113.discord_bot.command.MusicCommand;
import snowsan0113.discord_bot.command.RoleReactionCommand;
import snowsan0113.discord_bot.command.VoteCommand;
import snowsan0113.discord_bot.listener.ReactionListener;
import snowsan0113.discord_bot.listener.StringSelectListener;
import snowsan0113.discord_bot.manager.ConfigManager;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] strings) throws InterruptedException, IOException, SQLException {
        if (jda == null) {
            jda = JDABuilder.createDefault(ConfigManager.getToken())
                    .addEventListeners(new Main())
                    .addEventListeners(new HelloWorldCommand())
                    .addEventListeners(new VoteCommand())
                    .addEventListeners(new StringSelectListener())
                    .addEventListeners(new RoleReactionCommand())
                    .addEventListeners(new ReactionListener())
                    .addEventListeners(new MusicCommand())
                    .build();
            jda.awaitReady();

            jda.updateCommands()
                    .addCommands(Commands.slash("play", "音楽を再生するコマンド")
                            .addOption(OptionType.STRING, "url", "再生したいURL　もしくは　再生したいキーワード", true))
                    .addCommands(Commands.slash("play-info", "再生情報を表示するコマンド"))
                    .addCommands(Commands.slash("skip", "音楽をスキップするコマンド"))
                    .addCommands(Commands.slash("volume", "音楽の音量を設定するコマンド")
                            .addOption(OptionType.INTEGER, "volume", "音量"))
                    .addCommands(Commands.slash("helloworld", "helloworldを出力する。"))
                    .addCommands(Commands.slash("vote", "投票コマンド")
                            .addOptions(
                                    new OptionData(OptionType.STRING, "vote_option", "投票の選択肢（コンマで区切る）", true),
                                    new OptionData(OptionType.STRING, "title", "投票のタイトル", true),
                                    new OptionData(OptionType.INTEGER, "time", "投票時間", true)))
                    .addCommands(Commands.slash("role_reaction", "aa"))
                    .queue();
        }
    }

    public static JDA getJDA() {
        return jda;
    }

}
