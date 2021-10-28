package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;

public class SlideshowCommand extends Command {

    public static final String COMMAND_WORD = "slideshow";
    public static final String COMMAND_DESCRIPTION =
            "Starts the slideshow with the current filtered list of flashcards.";
    public static final String[] COMMAND_PARAMETERS = new String[0];
    public static final String[] COMMAND_EXAMPLES = new String[] { COMMAND_WORD };

    public static final String MESSAGE_SUCCESS = "Slideshow started!";
    public static final String MESSAGE_EMPTY_SLIDESHOW = "An empty slideshow cannot be started!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        try {
            model.startSlideshow();
        } catch (EmptySlideshowException e) {
            throw new CommandException(MESSAGE_EMPTY_SLIDESHOW);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
