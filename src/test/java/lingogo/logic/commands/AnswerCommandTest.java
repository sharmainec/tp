package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_AFTERNOON;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Contains integration tests (interaction with the Model and Flip) and unit tests for
 * {@code AnswerCommand}.
 */
public class AnswerCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Phrase validPhraseAfternoon = new Phrase(VALID_ENGLISH_PHRASE_AFTERNOON);
    private Phrase validPhraseGoodMorning = new Phrase(VALID_ENGLISH_PHRASE_GOOD_MORNING);

    @Test
    public void execute_correctGivenPhrase_success() {
        model.startSlideshow();

        Flashcard flashcardToTest = model.getCurrentSlide();
        AnswerCommand answerCommand = new AnswerCommand(validPhraseAfternoon);

        String expectedMessage = String.format(AnswerCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
            flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_AFTERNOON);

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        expectedModel.startSlideshow();
        expectedModel.answerCurrentSlide();
        expectedModel.displayCurrentAnswer();

        assertCommandSuccess(answerCommand, model, expectedMessage, expectedModel);

        ReadOnlySlideshowApp slideshowApp = model.getSlideshowApp();
        assertTrue(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void execute_wrongGivenPhrase_success() {
        model.startSlideshow();

        Flashcard flashcardToTest = model.getCurrentSlide();
        AnswerCommand answerCommand = new AnswerCommand(validPhraseGoodMorning);

        String expectedMessage = String.format(AnswerCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG,
            flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_GOOD_MORNING);

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        expectedModel.startSlideshow();
        expectedModel.answerCurrentSlide();
        expectedModel.displayCurrentAnswer();

        assertCommandSuccess(answerCommand, model, expectedMessage, expectedModel);

        ReadOnlySlideshowApp slideshowApp = model.getSlideshowApp();
        assertTrue(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void execute_slideshowInactive_throwsCommandException() {
        AnswerCommand answerCommand = new AnswerCommand(validPhraseAfternoon);
        String expectedMessage = String.format(Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);

        assertCommandFailure(answerCommand, model, expectedMessage);

        ReadOnlySlideshowApp slideshowApp = model.getSlideshowApp();
        assertFalse(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void execute_slideAlreadyAnswered_throwsCommandException() {
        model.startSlideshow();

        Flashcard flashcardToTest = model.getCurrentSlide();
        AnswerCommand answerCommand = new AnswerCommand(validPhraseAfternoon);
        String expectedMessage = String.format(AnswerCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
                flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_AFTERNOON);

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        expectedModel.startSlideshow();
        expectedModel.answerCurrentSlide();
        expectedModel.displayCurrentAnswer();

        // First time answer command is executed
        assertCommandSuccess(answerCommand, model, expectedMessage, expectedModel);

        String expectedErrorMessage = String.format(Messages.MESSAGE_FLASHCARD_ALREADY_ANSWERED);

        ReadOnlySlideshowApp slideshowApp = model.getSlideshowApp();
        assertTrue(slideshowApp.isAnswerDisplayedProperty().get());

        // Second time answer command is executed
        assertCommandFailure(answerCommand, model, expectedErrorMessage);

        assertTrue(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void equals() {
        AnswerCommand answerFirstCommand = new AnswerCommand(validPhraseAfternoon);
        AnswerCommand answerSecondCommand = new AnswerCommand(validPhraseGoodMorning);

        // same object -> returns true
        assertTrue(answerFirstCommand.equals(answerFirstCommand));

        // same values -> returns true
        AnswerCommand answerFirstCommandCopy = new AnswerCommand(validPhraseAfternoon);
        assertTrue(answerFirstCommand.equals(answerFirstCommandCopy));

        // different types -> returns false
        assertFalse(answerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(answerFirstCommand.equals(null));

        // different phrase -> returns false
        assertFalse(answerFirstCommand.equals(answerSecondCommand));
    }
}
