package snowsan0113.discord_bot.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import snowsan0113.discord_bot.Main;
import snowsan0113.discord_bot.manager.ConfigManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoleReactionCommand extends ListenerAdapter {

    private static final List<Emoji> emoji_list = Arrays.asList(
            Emoji.fromUnicode("1\uFE0F"),
            Emoji.fromUnicode("2\uFE0F")
    );

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        JDA jda = event.getJDA();
        MessageChannelUnion channel = event.getChannel();
        if ("role_reaction".equalsIgnoreCase(cmd)) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("選択してください");
            try {
                for (int n = 0; n < ConfigManager.getRoles().size(); n++) {
                    Role role = ConfigManager.getRoles().get(n);
                    embed.addField((n + 1) + ". " + role.getName(), " ", true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            channel.sendMessageEmbeds(embed.build()).queue(message -> {
                message.addReaction(Emoji.fromUnicode("1\uFE0F⃣")).queue();
            });
        }
    }

}
