package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.EnglishPhraseMatchesGivenPhrasePredicate;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Checks whether the English phrase of a flashcard matches a given phrase.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";
    public static final String COMMAND_DESCRIPTION = "Checks whether the English phrase of a flashcard"
        + " matches a given phrase";
    public static final String COMMAND_USAGE = "test INDEX e/ENGLISH_PHRASE";
    public static final String COMMAND_EXAMPLES = "test 4 e/hello";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Checks whether the English phrase of a flashcard matches a given phrase.\n"
        + "Parameters: INDEX (must be a positive integer) e/ENGLISH_PHRASE\n"
        + "Example: " + COMMAND_EXAMPLES;

    private static final String COMPARISON_TEXT = "Foreign Phrase: %1$s\n" + "Expected answer: %2$s\n"
        + "Your answer: %3$s";

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT = "Well done! You got it right!\n"
        + COMPARISON_TEXT;

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG = "Oh no! You got it wrong!\n" + COMPARISON_TEXT;

    private final Index targetIndex;
    private final EnglishPhraseMatchesGivenPhrasePredicate predicate;
    private final Phrase givenPhrase;

    /**
     * @param targetIndex of the flashcard in the displayed flashcard list to test
     * @param givenPhrase to test for match with the flashcard's English Phrase
     */
    public TestCommand(Index targetIndex, Phrase givenPhrase) {
        this.targetIndex = targetIndex;
        this.predicate = new EnglishPhraseMatchesGivenPhrasePredicate(givenPhrase);
        this.givenPhrase = givenPhrase;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToTest = lastShownList.get(targetIndex.getZeroBased());

        if (!predicate.test(flashcardToTest)) {
            return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG,
                flashcardToTest.getForeignPhrase(), flashcardToTest.getEnglishPhrase(), givenPhrase));
        }
        return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
            flashcardToTest.getForeignPhrase(), flashcardToTest.getEnglishPhrase(), givenPhrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TestCommand // instanceof handles nulls
            && targetIndex.equals(((TestCommand) other).targetIndex)
            && givenPhrase.equals(((TestCommand) other).givenPhrase)); // state check
    }
}
