package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;

/**
 * Clears the flashcard app.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_DESCRIPTION = "Clears all flashcards.";
    public static final String[] COMMAND_PARAMETERS = new String[0];
    public static final String[] COMMAND_EXAMPLES = new String[] { COMMAND_WORD };
    public static final String MESSAGE_SUCCESS = "All your flashcards have been cleared!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        model.setFlashcardApp(new FlashcardApp());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
