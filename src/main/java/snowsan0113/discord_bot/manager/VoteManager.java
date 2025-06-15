package snowsan0113.discord_bot.manager;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class VoteManager {

    //投票リスト
    private static final List<Vote> vote_list = new ArrayList<>();

    public static void startVote(TextChannel channel, String messageid, String name, String display_name, int time, List<String> detail_list) {
        Vote vote = new Vote(channel, messageid, name, display_name, time, detail_list);
        vote_list.add(vote);
    }

    public static void stopVote(String name) {
        vote_list.stream().filter(vote -> vote.getName().equalsIgnoreCase(name)).findFirst().orElse(null).stopVote();
    }

    public static Vote getVote(String id) {
        return vote_list.stream().filter(vote -> vote.getMessageId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public static List<Vote> getVoteList() {
        return vote_list;
    }
}
