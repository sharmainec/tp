package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;

public class NextSlideCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String COMMAND_DESCRIPTION = "Display the next flashcard in the slideshow.";
    public static final String[] COMMAND_PARAMETERS = new String[0];
    public static final String[] COMMAND_EXAMPLES = new String[] { COMMAND_WORD };

    public static final String MESSAGE_SUCCESS = "Next flashcard shown!";
    public static final String MESSAGE_NO_NEXT_SLIDE = "There are no more slides left in the current slideshow!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);
        }

        try {
            model.slideshowNextFlashcard();
        } catch (InvalidSlideshowIndexException e) {
            throw new CommandException(MESSAGE_NO_NEXT_SLIDE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
