package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

public class SlideshowCommand extends Command {

    public static final String COMMAND_WORD = "slideshow";
    public static final String COMMAND_DESCRIPTION = "Starts the slideshow with the current filtered list of flashcards";
    public static final String COMMAND_USAGE = "slideshow";
    public static final String COMMAND_EXAMPLES = "slideshow";
    public static final String MESSAGE_SUCCESS = "Slideshow started!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        model.startSlideshow();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}