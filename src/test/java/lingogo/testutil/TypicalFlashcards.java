package lingogo.testutil;

import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_SUNRISE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_TAMIL;
import static lingogo.logic.commands.CommandTestUtil.VALID_TAMIL_PHRASE_SUNRISE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lingogo.model.FlashcardApp;
import lingogo.model.flashcard.Flashcard;

public class TypicalFlashcards {

    public static final Flashcard AFTERNOON_CHINESE_FLASHCARD = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Afternoon").withForeignPhrase("下午").build();
    public static final Flashcard NIGHT_CHINESE_FLASHCARD = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Night").withForeignPhrase("晚上").build();
    public static final Flashcard BYE_CHINESE_FLASHCARD = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Bye").withForeignPhrase("再见").build();
    public static final Flashcard SORRY_CHINESE_FLASHCARD = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Sorry").withForeignPhrase("对不起").build();

    // Manually added
    public static final Flashcard THANK_YOU_CHINESE_FLASHCARD = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Thank You").withForeignPhrase("谢谢").build();
    public static final Flashcard HAPPY_BIRTHDAY = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Happy Birthday").withForeignPhrase("生日快乐").build();
    public static final Flashcard HAPPY_ANNIVERSARY_CHINESE = new FlashcardBuilder().withLanguageType("Chinese")
            .withEnglishPhrase("Happy Anniversary").withForeignPhrase("周年快乐").build();
    public static final Flashcard HAPPY_BIRTHDAY_JAPANESE = new FlashcardBuilder().withLanguageType("Japanese")
        .withEnglishPhrase("Happy Birthday").withForeignPhrase("誕生日おめでとう").build();

    // Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard GOOD_MORNING_CHINESE_FLASHCARD =
        new FlashcardBuilder().withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
    public static final Flashcard HELLO_CHINESE_FLASHCARD =
        new FlashcardBuilder().withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
    public static final Flashcard SUNRISE_TAMIL_FLASHCARD =
        new FlashcardBuilder().withLanguageType(VALID_LANGUAGE_TYPE_TAMIL)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_SUNRISE)
                .withForeignPhrase(VALID_TAMIL_PHRASE_SUNRISE).build();

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
                SORRY_CHINESE_FLASHCARD, SUNRISE_TAMIL_FLASHCARD));
    }
}
