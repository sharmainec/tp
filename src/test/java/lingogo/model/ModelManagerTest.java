package lingogo.model;

import static lingogo.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static lingogo.testutil.Assert.assertThrows;
import static lingogo.testutil.TypicalFlashcards.GOOD_MORNING_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HELLO_CHINESE_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.GuiSettings;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;
import lingogo.testutil.FlashcardAppBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FlashcardApp(), new FlashcardApp(modelManager.getFlashcardApp()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFlashcardAppFilePath(Paths.get("flashcard/app/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFlashcardAppFilePath(Paths.get("new/flashcard/app/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFlashcardAppFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFlashcardAppFilePath(null));
    }

    @Test
    public void setFlashcardAppFilePath_validPath_setsFlashcardAppFilePath() {
        Path path = Paths.get("flashcard/app/file/path");
        modelManager.setFlashcardAppFilePath(path);
        assertEquals(path, modelManager.getFlashcardAppFilePath());
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFlashcard(null));
    }

    @Test
    public void hasFlashcard_flashcardNotInFlashcardApp_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void hasFlashcard_flashcardInFlashcardApp_returnsTrue() {
        modelManager.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        assertTrue(modelManager.hasFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFlashcardList().remove(0));
    }

    @Test
    public void startSlideshow() {
        modelManager.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        modelManager.startSlideshow();
        assertTrue(modelManager.isSlideshowActive());
        assertEquals(modelManager.getCurrentSlide(), GOOD_MORNING_CHINESE_FLASHCARD);
    }

    @Test
    public void stopSlideshow() {
        modelManager.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        modelManager.startSlideshow();
        assertTrue(modelManager.isSlideshowActive());
        assertEquals(modelManager.getCurrentSlide(), GOOD_MORNING_CHINESE_FLASHCARD);

        modelManager.stopSlideshow();
        assertFalse(modelManager.isSlideshowActive());
    }

    @Test
    public void answerCurrentSlide() {
        modelManager.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        modelManager.startSlideshow();
        assertTrue(modelManager.isSlideshowActive());
        assertEquals(modelManager.getCurrentSlide(), GOOD_MORNING_CHINESE_FLASHCARD);

        modelManager.answerCurrentSlide();
        assertTrue(modelManager.getSlideshowApp().isCurrentSlideAnswered());
    }

    @Test
    public void displayCurrentAnswer() {
        modelManager.addFlashcard(GOOD_MORNING_CHINESE_FLASHCARD);
        modelManager.startSlideshow();
        assertTrue(modelManager.isSlideshowActive());
        assertEquals(modelManager.getCurrentSlide(), GOOD_MORNING_CHINESE_FLASHCARD);

        modelManager.displayCurrentAnswer();
        assertTrue(modelManager.getSlideshowApp().isAnswerDisplayedProperty().get());
    }

    @Test
    public void equals() {
        FlashcardApp flashcardApp = new FlashcardAppBuilder().withFlashcard(GOOD_MORNING_CHINESE_FLASHCARD)
                .withFlashcard(HELLO_CHINESE_FLASHCARD).build();
        FlashcardApp differentFlashcardApp = new FlashcardApp();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(flashcardApp, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(flashcardApp, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different flashcardApp -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFlashcardApp, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GOOD_MORNING_CHINESE_FLASHCARD.getEnglishPhrase().value.split("\\s+");
        modelManager.updateFilteredFlashcardList(new PhraseContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(flashcardApp, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFlashcardAppFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(flashcardApp, differentUserPrefs)));
    }
}
