package snowsan0113.discord_bot.listener;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import snowsan0113.discord_bot.manager.music.MusicManager;

import java.util.List;
import java.util.stream.Collectors;

public class ButtonClickListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        for (int n = 1; n <= 5; n++) {
            if (event.getComponentId().equals("button_" + n)) {
                MessageChannelUnion channel = event.getChannel();
                List<Button> button_list = event.getInteraction().getMessage().getButtons();

                List<Button> disable_button_list = button_list.stream()
                        .map(Button::asDisabled)
                        .collect(Collectors.toList());

                List<ActionRow> newRows = List.of(ActionRow.of(disable_button_list));
                event.editComponents(newRows).queue();

                MusicManager.getInstance().selectPlay(channel.asTextChannel(), n - 1);
            }
        }
    }
}
