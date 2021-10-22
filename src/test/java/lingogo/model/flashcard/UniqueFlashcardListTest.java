package lingogo.model.flashcard;

import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.testutil.TypicalFlashcards.GOOD_MORNING_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HELLO_CHINESE_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lingogo.model.flashcard.exceptions.DuplicateFlashcardException;
import lingogo.model.flashcard.exceptions.FlashcardNotFoundException;
import lingogo.testutil.FlashcardBuilder;

public class UniqueFlashcardListTest {
    private final UniqueFlashcardList uniqueFlashcardList = new UniqueFlashcardList();

    @Test
    public void contains_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.contains(null));
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashcardList.contains(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        assertTrue(uniqueFlashcardList.contains(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void contains_flashcardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        Flashcard editedFlashcard = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withFlipStatus(!FlashcardBuilder.DEFAULT_IS_FLIPPED).build();
        assertTrue(uniqueFlashcardList.contains(editedFlashcard));
    }

    @Test
    public void add_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.add(null));
    }

    @Test
    public void add_duplicateFlashcard_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        assertThrows(DuplicateFlashcardException.class, () -> uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void setFlashcard_nullTargetFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList
                .setFlashcard(null, GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void setFlashcard_nullEditedFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList
                .setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, null));
    }

    @Test
    public void setFlashcard_targetFlashcardNotInList_throwsFlashcardNotFoundException() {
        assertThrows(FlashcardNotFoundException.class, () -> uniqueFlashcardList
                .setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void setFlashcard_editedFlashcardIsSameFlashcard_success() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        uniqueFlashcardList.setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, GOOD_MORNING_CHINESE_FLASHCARD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasSameIdentity_success() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        Flashcard editedFlashcard = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        uniqueFlashcardList.setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, editedFlashcard);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(editedFlashcard);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasDifferentIdentity_success() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        uniqueFlashcardList.setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, HELLO_CHINESE_FLASHCARD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HELLO_CHINESE_FLASHCARD);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueIdentity_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        uniqueFlashcardList.add(HELLO_CHINESE_FLASHCARD);
        assertThrows(DuplicateFlashcardException.class, () -> uniqueFlashcardList
                .setFlashcard(GOOD_MORNING_CHINESE_FLASHCARD, HELLO_CHINESE_FLASHCARD));
    }

    @Test
    public void remove_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.remove(null));
    }

    @Test
    public void remove_flashcardDoesNotExist_throwsFlashcardNotFoundException() {
        assertThrows(FlashcardNotFoundException.class, () -> uniqueFlashcardList
                .remove(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void remove_existingFlashcard_removesFlashcard() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        uniqueFlashcardList.remove(GOOD_MORNING_CHINESE_FLASHCARD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }


    @Test
    public void setFlashcards_nullUniqueFlashcardList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList
                .setFlashcards((UniqueFlashcardList) null));
    }

    @Test
    public void setFlashcards_uniqueFlashcardList_replacesOwnListWithProvidedUniqueFlashcardList() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HELLO_CHINESE_FLASHCARD);
        uniqueFlashcardList.setFlashcards(expectedUniqueFlashcardList);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList
                .setFlashcards((List<Flashcard>) null));
    }

    @Test
    public void setFlashcards_list_replacesOwnListWithProvidedList() {
        uniqueFlashcardList.add(GOOD_MORNING_CHINESE_FLASHCARD);
        List<Flashcard> flashcardList = Collections.singletonList(HELLO_CHINESE_FLASHCARD);
        uniqueFlashcardList.setFlashcards(flashcardList);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HELLO_CHINESE_FLASHCARD);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_listWithDuplicateFlashcards_throwsDuplicateFlashcardException() {
        List<Flashcard> listWithDuplicateFlashcards = Arrays.asList(GOOD_MORNING_CHINESE_FLASHCARD,
                GOOD_MORNING_CHINESE_FLASHCARD);
        assertThrows(DuplicateFlashcardException.class, () -> uniqueFlashcardList
                .setFlashcards(listWithDuplicateFlashcards));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFlashcardList.asUnmodifiableObservableList().remove(0));
    }
}
