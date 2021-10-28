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
    public static final String COMMAND_DESCRIPTION = "Finds all flashcards whose English or foreign phrases contain "
            + "any of the specified keywords (case-insensitive) and displays them in the displayed flashcard list.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
        "[" + PREFIX_ENGLISH_PHRASE + Parameter.ENGLISH_PHRASE + "]",
        "[" + PREFIX_FOREIGN_PHRASE + Parameter.ENGLISH_KEYWORD + "]"
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " " + PREFIX_ENGLISH_PHRASE + "Good morning " + PREFIX_FOREIGN_PHRASE + "你",
        COMMAND_WORD + " " + PREFIX_ENGLISH_PHRASE + "Good morning",
        COMMAND_WORD + " " + PREFIX_FOREIGN_PHRASE + "你"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

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
