package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.flashcard.EnglishPhraseContainsKeywordsPredicate;

/**
 * Finds and lists all flashcards in the flashcard app whose English phrase contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_DESCRIPTION = "Finds flashcard(s) that have a matching English phrase";
    public static final String COMMAND_USAGE = "find ENGLISH_PHRASE";
    public static final String COMMAND_EXAMPLES = "find Good Morning";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all flashcards whose english phrases contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " hello";

    private final EnglishPhraseContainsKeywordsPredicate predicate;

    public FindCommand(EnglishPhraseContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW, model.getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
