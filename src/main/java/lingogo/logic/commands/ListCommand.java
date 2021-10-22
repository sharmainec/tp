package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.logic.parser.ParserUtil;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.FlashcardInGivenFlashcardListPredicate;

/**
 * Lists flashcards in the flashcard app to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_DESCRIPTION = "Lists flashcards";
    public static final String COMMAND_USAGE = "list [NUMBER_OF_FLASHCARDS]";
    public static final String COMMAND_EXAMPLES = "list\nlist 4";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";
    public static final String MESSAGE_SUCCESS_SHUFFLED = "Randomly selected %1$d flashcard(s) to be listed";
    private final int n;

    public ListCommand() {
        this.n = 0;
    }

    public ListCommand(int n) {
        this.n = n;
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

        if (n > size || n <= 0) { // show all flashcards
            return new CommandResult(MESSAGE_SUCCESS);
        }
        List<Flashcard> filteredList = null;
        try {
            filteredList = chooseRandomFlashcards(n, size, lastShownList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert filteredList != null;
        model.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(filteredList));
        return new CommandResult(
                String.format(MESSAGE_SUCCESS_SHUFFLED, model.getFilteredFlashcardList().size()));
    }

    private List<Flashcard> chooseRandomFlashcards(int n, int size, List<Flashcard> flashcardList)
            throws ParseException {
        String indexString = new Random().ints(1, size + 1)
                .distinct()
                .limit(n)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        System.out.println(indexString);
        List<Index> indexList = ParserUtil.parseIndices(indexString);
        return indexList.stream()
                .map(index -> flashcardList.get(index.getZeroBased()))
                .collect(Collectors.toList());
    }
}
