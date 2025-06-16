package snowsan0113.discord_bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;
import snowsan0113.discord_bot.manager.VoteManager;

import java.util.Arrays;
import java.util.List;

public class VoteCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        JDA jda = event.getJDA();
        MessageChannelUnion channel = event.getChannel();
        if ("vote".equalsIgnoreCase(cmd)) {
            //引数
            String option_string = event.getOption("vote_option").getAsString();
            OptionMapping title_option = event.getOption("title");
            String title_string = title_option == null ? "投票" : title_option.getAsString();
            int time = event.getOption("time").getAsInt();

            List<String> option_list = Arrays.asList(option_string.split(","));

            StringSelectMenu.Builder select = StringSelectMenu.create("vote_select")
                    .setPlaceholder("選択肢を選んでください");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(title_string + "（時間：" + time + "秒）");
            for (int n = 0; n < option_list.size(); n++) {
                String option = option_list.get(n);
                embed.addField(n + 1 + ":" , option, true);
                select.addOption(option, n + "_" + option);
            }

            event.replyEmbeds(embed.build())
                    .addActionRow(select.build())
                    .queue(replay -> {
                        replay.retrieveOriginal().queue(message -> {
                            VoteManager.startVote(channel.asTextChannel(), message.getId(), title_string, title_string, time, option_list);
                        });;
                    });
        }
    }

}
