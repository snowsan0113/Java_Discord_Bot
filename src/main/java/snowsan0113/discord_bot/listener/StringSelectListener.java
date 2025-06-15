package snowsan0113.discord_bot.listener;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import snowsan0113.discord_bot.manager.VoteManager;

public class StringSelectListener extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        String messageid = event.getInteraction().getId();
        StringSelectInteraction interaction = event.getInteraction();
        if (interaction.getComponentId().equalsIgnoreCase("vote_select")) {
            for (SelectOption option : interaction.getSelectedOptions()) {
                VoteManager.getVote(event.getMessageId()).addVote(event.getMember(), option.getLabel());
            }
            event.deferReply(true).complete();
            event.getHook().sendMessage("投票しました。").queue();
        }
    }
}
