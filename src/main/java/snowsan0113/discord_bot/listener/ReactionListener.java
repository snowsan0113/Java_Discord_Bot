package snowsan0113.discord_bot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import snowsan0113.discord_bot.manager.ConfigManager;

import java.io.IOException;

public class ReactionListener extends ListenerAdapter {
    
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        MessageChannelUnion channel = event.getChannel();
        MessageReaction reaction = event.getReaction();
        User user = event.getUser();
        Member member = event.getMember();
        try {
            if (!user.isBot()) {
                if (reaction.getEmoji().asUnicode().getAsReactionCode().equalsIgnoreCase("1\uFE0F⃣")) {
                    Role role = event.getJDA().getRoleById(ConfigManager.getRoles().get(0).getId());
                    event.getGuild().addRoleToMember(user, role)
                            .queue(success -> {
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setTitle("成功");
                                embed.addField("ロールを追加することができました。", " ", true);
                                channel.sendMessageEmbeds(embed.build()).queue();
                                reaction.removeReaction(user).queue();
                            }, error -> {
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setTitle("失敗");
                                embed.addField("エラーが発生しました", error.getLocalizedMessage(), true);
                                channel.sendMessageEmbeds(embed.build()).queue();
                            });
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
