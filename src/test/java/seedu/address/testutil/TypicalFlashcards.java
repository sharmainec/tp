package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FlashcardApp;
import seedu.address.model.flashcard.Flashcard;

public class TypicalFlashcards {

    public static final Flashcard GOOD_AFTERNOON_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Good Afternoon")
            .withForeignPhrase("午安").build();
    public static final Flashcard GOOD_NIGHT_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Good Night")
            .withForeignPhrase("晚安").build();
    public static final Flashcard GOOD_BYE_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Good bye")
            .withForeignPhrase("再见").build();
    public static final Flashcard SORRY_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Sorry")
            .withForeignPhrase("对不起").build();

    // Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard GOOD_MORNING_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
            .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
    public static final Flashcard HELLO_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
            .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();

    private TypicalFlashcards() {} // prevents instantiation

    /**
     * Returns an {@code FlashcardApp} with all the typical flashcards.
     */
    public static FlashcardApp getTypicalFlashcardApp() {
        FlashcardApp fa = new FlashcardApp();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            fa.addFlashcard(flashcard);
        }
        return fa;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(GOOD_AFTERNOON_CHINESE_FLASHCARD, GOOD_NIGHT_CHINESE_FLASHCARD));
    }
}
