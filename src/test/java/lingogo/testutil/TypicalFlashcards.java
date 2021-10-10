package lingogo.testutil;

import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lingogo.model.FlashcardApp;
import lingogo.model.flashcard.Flashcard;

public class TypicalFlashcards {

    public static final Flashcard AFTERNOON_CHINESE_FLASHCARD =
        new FlashcardBuilder().withEnglishPhrase("Afternoon")
            .withForeignPhrase("下午").build();
    public static final Flashcard NIGHT_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Night")
        .withForeignPhrase("晚上").build();
    public static final Flashcard BYE_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Bye")
        .withForeignPhrase("再见").build();
    public static final Flashcard SORRY_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Sorry")
        .withForeignPhrase("对不起").build();

    // Manually added
    public static final Flashcard THANK_YOU_CHINESE_FLASHCARD = new FlashcardBuilder().withEnglishPhrase("Thank You")
        .withForeignPhrase("谢谢").build();
    public static final Flashcard HAPPY_BIRTHDAY = new FlashcardBuilder().withEnglishPhrase("Happy Birthday")
        .withForeignPhrase("生日快乐").build();


    // Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard GOOD_MORNING_CHINESE_FLASHCARD =
        new FlashcardBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
            .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
    public static final Flashcard HELLO_CHINESE_FLASHCARD =
        new FlashcardBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
            .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();

    private TypicalFlashcards() {
    } // prevents instantiation

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
        return new ArrayList<>(
            Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD,
                SORRY_CHINESE_FLASHCARD));
    }
}
