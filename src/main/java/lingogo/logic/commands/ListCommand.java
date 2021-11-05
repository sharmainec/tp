package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.FlashcardInGivenFlashcardListPredicate;

/**
 * Lists either all flashcards or a random number of flashcards in the flashcard app to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_DESCRIPTION = "Lists either all flashcards or a random number of flashcards.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
        "[" + Parameter.NUMBER_OF_FLASHCARDS + "]"
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD,
        COMMAND_WORD + " 4"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String MESSAGE_SUCCESS = "Listed all flashcards!";
    public static final String MESSAGE_SUCCESS_SHUFFLED = "Randomly selected %1$d flashcard(s) to be listed!";

    private final Optional<Integer> input;
    private final Optional<Random> random;

    /**
     * Constructs a ListCommand that lists all flashcards.
     */
    public ListCommand() {
        this.input = Optional.empty();
        this.random = Optional.empty();
    }

    /**
     * Constructs a ListCommand with a specified number of flashcards to randomly list.
     */
    public ListCommand(int n) {
        this.input = Optional.of(n);
        this.random = Optional.of(new Random());
    }

    /**
     * Constructs a ListCommand with a seed for its random flashcard selector.
     */
    public ListCommand(int n, int seed) {
        this.input = Optional.of(n);
        this.random = Optional.of(new Random(seed));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        int size = lastShownList.size();
        if (input.isEmpty() || input.get() == size) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            int n = input.get();
            if (n > size || n <= 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_N);
            }
            List<Flashcard> filteredList = chooseRandomFlashcards(n, size, lastShownList);
            assert filteredList != null;
            model.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(filteredList));
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS_SHUFFLED, model.getFilteredFlashcardList().size()));
        }
    }

    private List<Flashcard> chooseRandomFlashcards(int n, int size, List<Flashcard> flashcardList) {
        return random.get().ints(0, size)
                .distinct()
                .limit(n)
                .boxed()
                .map(Index::fromZeroBased)
                .map(index -> flashcardList.get(index.getZeroBased()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && input.equals(((ListCommand) other).input));
    }
}
