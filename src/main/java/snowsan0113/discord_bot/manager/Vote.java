package snowsan0113.discord_bot.manager;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.*;

public class Vote {

    //投票の中身
    private final String name;
    private final String display_name;
    private final Map<String, List<Member>> vote_player_map;
    private final TextChannel channel;
    private final String messageid;
    private final TimerTask task;

    public Vote(TextChannel channel, String messageid, String name, String display_name, int time, List<String> detail_list) {
        this.channel = channel;
        this.messageid = messageid;
        this.name = name;
        this.vote_player_map = new HashMap<>();
        this.display_name = display_name;
        for (String detail : detail_list) {
            this.vote_player_map.put(detail, new ArrayList<>());
        }

        Timer timer = new Timer();
        this.task = new VoteTask(this, time);
        timer.scheduleAtFixedRate(this.task, 0L, 1000L);
    }

    public int addVote(Member member, String detail) {
        if (!isVoted(member)) {
            if (vote_player_map.get(detail) != null) {
                vote_player_map.get(detail).add(member);
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            return 2;
        }
    }

    public void removeVote(Member member) {
        String voted_name = getVotedDetail(member);
        if (isVoted(member) && voted_name != null) {
            List<Member> vote_player_list = vote_player_map.get(voted_name);
            if (vote_player_list != null) {
                vote_player_list.remove(member);
            }
        }
    }

    public String getVotedDetail(Member member) {
        if (isVoted(member)) {
            for (Map.Entry<String, List<Member>> entry : vote_player_map.entrySet()) {
                String detail = entry.getKey();
                List<Member> vote_player_list = entry.getValue();

                if (vote_player_list.contains(member)) {
                    return detail;
                }
            }
        }
        return null;
    }

    public boolean isVoted(Member member) {
        for (Map.Entry<String, List<Member>> entry : vote_player_map.entrySet()) {
            List<Member> map_vote_player = entry.getValue();

            if (map_vote_player.contains(member)) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }

    public Map<String, List<Member>> getVoteMap() {
        return new HashMap<>(vote_player_map);
    }

    public void stopVote() {
        task.cancel();
        VoteManager.getVoteList().remove(this);
    }

    public TextChannel getChannel() {
        return channel;
    }

    public String getMessageId() {
        return messageid;
    }
}
