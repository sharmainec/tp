package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.flashcard.LanguageTypeMatchesGivenPhrasePredicate;

/**
 * Filters all flashcards in the flashcard app by its Language type.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_DESCRIPTION =
            "Filters all flashcards in the flashcard app by its Language type";
    public static final String COMMAND_USAGE = "filter [l/LANGUAGE_TYPE]";
    public static final String COMMAND_EXAMPLES = "filter l/Chinese";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters all flashcards in the flashcard app by its Language type.\n"
            + "Parameters: "
            + "[" + PREFIX_LANGUAGE_TYPE + "LANGUAGE_TYPE] "
            + "Example: " + COMMAND_EXAMPLES;

    private final LanguageTypeMatchesGivenPhrasePredicate predicate;

    public FilterCommand(LanguageTypeMatchesGivenPhrasePredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
