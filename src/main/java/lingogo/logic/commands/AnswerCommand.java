package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.EnglishPhraseMatchesGivenPhrasePredicate;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;
import lingogo.model.slideshow.exceptions.SlideAlreadyAnsweredException;

/**
 * Checks whether the English phrase of a flashcard matches a given phrase.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_DESCRIPTION =
            "Checks whether the English phrase of the displayed flashcard in the slideshow matches the given phrase.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
        PREFIX_ENGLISH_PHRASE + Parameter.ENGLISH_PHRASE.toString()
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " " + PREFIX_ENGLISH_PHRASE + "hello"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String COMPARISON_TEXT = "Expected answer: %1$s\nYour answer: %2$s";

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT = "Well done! You got it right!\n"
        + COMPARISON_TEXT;

    public static final String MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG = "Oh no! You got it wrong!\n" + COMPARISON_TEXT;

    private final EnglishPhraseMatchesGivenPhrasePredicate predicate;
    private final Phrase givenPhrase;

    /**
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

        try {
            model.answerCurrentSlide();
            model.displayCurrentAnswer();
        } catch (SlideAlreadyAnsweredException e) {
            throw new CommandException(Messages.MESSAGE_FLASHCARD_ALREADY_ANSWERED);
        }

        Flashcard currentFlashcard = model.getCurrentSlide();
        if (!predicate.test(currentFlashcard)) {
            return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG,
                    currentFlashcard.getEnglishPhrase(), givenPhrase));
        }

        return new CommandResult(String.format(MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
                currentFlashcard.getEnglishPhrase(), givenPhrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AnswerCommand // instanceof handles nulls
            && givenPhrase.equals(((AnswerCommand) other).givenPhrase)); // state check
    }
}
