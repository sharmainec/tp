package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.EnglishPhraseMatchesGivenPhrasePredicate;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Checks whether the English phrase of a flashcard matches a given phrase.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_DESCRIPTION = "Checks whether the given English phrase matches the English phrase of the current displayed flashcard in the slideshow";
    public static final String COMMAND_USAGE = "answer e/ENGLISH_PHRASE";
    public static final String COMMAND_EXAMPLES = "answer e/hello";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Checks whether the English phrase of the current displayed flashcard in the slideshow matches the given phrase.\n"
        + "Parameters: e/ENGLISH_PHRASE\n"
        + "Example: " + COMMAND_EXAMPLES;

    public static final String COMPARISON_TEXT = "Foreign phrase: %1$s\n" + "Expected answer: %2$s\n"
        + "Your answer: %3$s";

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT = "Well done! You got it right!\n"
        + COMPARISON_TEXT;

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG = "Oh no! You got it wrong!\n" + COMPARISON_TEXT;

    private final EnglishPhraseMatchesGivenPhrasePredicate predicate;
    private final Phrase givenPhrase;

    /**
     * @param targetIndex of the flashcard in the displayed flashcard list to test
     * @param givenPhrase to test for match with the flashcard's English Phrase
     */
    public AnswerCommand(Phrase givenPhrase) {
        this.predicate = new EnglishPhraseMatchesGivenPhrasePredicate(givenPhrase);
        this.givenPhrase = givenPhrase;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);
        }

        Flashcard currentFlashcard = model.getCurrentSlide();
        model.answerCurrentSlide();
        model.displayCurrentAnswer();

        if (!predicate.test(currentFlashcard)) {
            return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG,
                    currentFlashcard.getForeignPhrase(), currentFlashcard.getEnglishPhrase(), givenPhrase));
        }

        return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
                currentFlashcard.getForeignPhrase(), currentFlashcard.getEnglishPhrase(), givenPhrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AnswerCommand // instanceof handles nulls
            && givenPhrase.equals(((AnswerCommand) other).givenPhrase)); // state check
    }
}
