package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

public class StopSlideshowCommand extends Command {

    public static final String COMMAND_WORD = "stop";
    public static final String COMMAND_DESCRIPTION = "Stops the current slideshow in progress";
    public static final String COMMAND_USAGE = "stop";
    public static final String COMMAND_EXAMPLES = "stop";
    public static final String MESSAGE_SUCCESS = "Slideshow stopped!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);
        }

        model.stopSlideshow();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
