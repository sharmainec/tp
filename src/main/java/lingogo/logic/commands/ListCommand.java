package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import lingogo.model.Model;

/**
 * Lists all persons in the flashcard app to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_DESCRIPTION = "Lists all flashcards";
    public static final String COMMAND_USAGE = "list";
    public static final String COMMAND_EXAMPLES = "list";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
