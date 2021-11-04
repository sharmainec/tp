package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import lingogo.commons.core.GuiSettings;
import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.ReadOnlyUserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.testutil.FlashcardBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashcardAdded modelStub = new ModelStubAcceptingFlashcardAdded();
        Flashcard validFlashcard = new FlashcardBuilder().build();

        CommandResult commandResult = new AddCommand(validFlashcard).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFlashcard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashcard), modelStub.flashcardsAdded);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard validFlashcard = new FlashcardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithFlashcard(validFlashcard);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_FLASHCARD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        Flashcard validFlashcard = new FlashcardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithActiveSlideshow();

        assertThrows(CommandException.class,
                Messages.MESSAGE_IN_SLIDESHOW_MODE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Flashcard goodMorning = new FlashcardBuilder().withEnglishPhrase("Good Morning").build();
        Flashcard hello = new FlashcardBuilder().withEnglishPhrase("Hello").build();
        AddCommand addGoodMorningCommand = new AddCommand(goodMorning);
        AddCommand addHelloCommand = new AddCommand(hello);

        // same object -> returns true
        assertTrue(addGoodMorningCommand.equals(addGoodMorningCommand));

        // same values -> returns true
        AddCommand addGoodMorningCommandCopy = new AddCommand(goodMorning);
        assertTrue(addGoodMorningCommand.equals(addGoodMorningCommandCopy));

        // different types -> returns false
        assertFalse(addGoodMorningCommand.equals(1));

        // null -> returns false
        assertFalse(addGoodMorningCommand.equals(null));

        // different person -> returns false
        assertFalse(addGoodMorningCommand.equals(addHelloCommand));
    }

    /**
     * A default model stub that have all of the methods failing, and an inactive slideshow.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFlashcardAppFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcardAppFilePath(Path flashcardAppFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcardApp(ReadOnlyFlashcardApp newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFlashcardApp getFlashcardApp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startSlideshow() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stopSlideshow() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSlideshowActive() {
            return false;
        }

        @Override
        public void slideshowNextFlashcard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void slideshowPreviousFlashcard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void answerCurrentSlide() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void displayCurrentAnswer() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySlideshowApp getSlideshowApp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Flashcard getCurrentSlide() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single flashcard.
     */
    private class ModelStubWithFlashcard extends ModelStub {
        private final Flashcard flashcard;

        ModelStubWithFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return this.flashcard.isSameFlashcard(flashcard);
        }
    }

    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingFlashcardAdded extends ModelStub {
        final ArrayList<Flashcard> flashcardsAdded = new ArrayList<>();

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return flashcardsAdded.stream().anyMatch(flashcard::isSameFlashcard);
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public ReadOnlyFlashcardApp getFlashcardApp() {
            return new FlashcardApp();
        }
    }

    private class ModelStubWithActiveSlideshow extends ModelStub {
        @Override
        public boolean isSlideshowActive() {
            return true;
        }
    }

}
