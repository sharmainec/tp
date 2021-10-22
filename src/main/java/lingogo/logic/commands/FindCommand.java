package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;

/**
 * Finds and lists all flashcards in the flashcard app whose English phrase or foreign phrase
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_DESCRIPTION = "Finds flashcard(s) that have a matching phrase";
    public static final String COMMAND_USAGE = "find [e/ENGLISH_KEYWORD] [f/FOREIGN_KEYWORD]";
    public static final String COMMAND_EXAMPLES = "find e/Good Morning f/你\nfind e/Good Morning\nfind f/你";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all flashcards whose english or foreign phrases contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_ENGLISH_PHRASE + "ENGLISH_KEYWORD] "
            + "[" + PREFIX_FOREIGN_PHRASE + "FOREIGN_KEYWORD] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENGLISH_PHRASE + "Hello "
            + PREFIX_FOREIGN_PHRASE + "你";

    private final PhraseContainsKeywordsPredicate predicate;

    public FindCommand(PhraseContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

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
