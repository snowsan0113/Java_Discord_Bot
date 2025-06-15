package snowsan0113.discord_bot.manager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.util.*;

public class VoteTask extends TimerTask {

    private final Vote vote;
    private int time;

    public VoteTask(Vote vote, int time) {
        this.vote = vote;
        this.time = time;
    }

    @Override
    public void run() {
        if (time <= 0) {
            Map<String, Integer> vote_size_map = new HashMap<>();
            int max_vote_size = 0;
            List<String> max_vote_detail_list = new ArrayList<>();
            Map<String, List<Member>> vote_player_map = vote.getVoteMap();

            for (Map.Entry<String, List<Member>> entry : vote_player_map.entrySet()) {
                String detail = entry.getKey();
                List<Member> vote_player_list = entry.getValue();
                vote_size_map.put(detail, vote_player_list.size());

                max_vote_size = Collections.max(new ArrayList<>(vote_size_map.values()));
                if (vote_player_list.size() == max_vote_size) {
                    max_vote_detail_list.add(detail);
                }
            }

            EmbedBuilder embed;
            if (max_vote_detail_list.size() == 1) {
                String first_detail = max_vote_detail_list.get(0);
                embed = new EmbedBuilder();
                embed.addField("投票が多かったのは", first_detail + "です（" + max_vote_size + "）", false);
                embed.setTitle("投票が終了しました。");
            }
            else {
                StringBuilder builder = new StringBuilder();
                for (String detail : max_vote_detail_list) {
                    builder.append("「" + detail + "」");
                }

                embed = new EmbedBuilder();
                embed.addField("投票が多かったのは", builder.toString() + "です（" + max_vote_size + "）", false);
                embed.setTitle("投票が終了しました。");
            }

            vote.getChannel().sendMessageEmbeds(embed.build()).queue();
            this.cancel();
        }
        else {
            System.out.println(time + "\n");
            time--;
        }
    }

    public Vote getVote() {
        return vote;
    }

}
