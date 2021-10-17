package lingogo.model;

import static lingogo.testutil.TypicalFlashcards.GOOD_MORNING_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.exceptions.DuplicateFlashcardException;
import lingogo.testutil.FlashcardBuilder;

public class FlashcardAppTest {
    private final FlashcardApp flashcardApp = new FlashcardApp();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), flashcardApp.getFlashcardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> flashcardApp.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFlashcardApp_replacesData() {
        FlashcardApp newData = getTypicalFlashcardApp();
        flashcardApp.resetData(newData);
        assertEquals(newData, flashcardApp);
    }

    @Test
    public void resetData_withDuplicateFlashcards_throwsDuplicateFlashcardException() {
        // Two flashcards with the same identity fields
        Flashcard editedFlashcard = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withFlipStatus(!FlashcardBuilder.DEFAULT_IS_FLIPPED).build();
        List<Flashcard> newFlashcards = Arrays.asList(GOOD_MORNING_CHINESE_FLASHCARD, editedFlashcard);
        FlashcardAppStub newData = new FlashcardAppStub(newFlashcards);

        assertThrows(DuplicateFlashcardException.class, () -> flashcardApp.resetData(newData));
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> flashcardApp.hasFlashcard(null));
    }

    @Test
    public void hasFlashcard_flashcardNotInFlashcardApp_returnsFalse() {
        assertFalse(flashcardApp.hasFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void hasFlashcard_flashcardInFlashcardApp_returnsTrue() {
        flashcardApp.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        assertTrue(flashcardApp.hasFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void hasFlashcard_flashcardWithSameIdentityFieldsInFlashcardApp_returnsTrue() {
        flashcardApp.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        Flashcard editedFlashcard = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withFlipStatus(!FlashcardBuilder.DEFAULT_IS_FLIPPED).build();
        assertTrue(flashcardApp.hasFlashcard(editedFlashcard));
    }

    // setFlashcard and removeFlashcard functionality have been tested in FlashcardTest.java

    @Test
    public void getFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> flashcardApp.getFlashcardList().remove(0));
    }

    /**
     * A stub ReadOnlyFlashcardApp whose flashcards list can violate interface constraints.
     */
    private static class FlashcardAppStub implements ReadOnlyFlashcardApp {
        private final ObservableList<Flashcard> flashcards = FXCollections.observableArrayList();

        FlashcardAppStub(Collection<Flashcard> flashcards) {
            this.flashcards.setAll(flashcards);
        }

        @Override
        public ObservableList<Flashcard> getFlashcardList() {
            return flashcards;
        }
    }
}
