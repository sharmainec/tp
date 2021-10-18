package lingogo.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

/**
 * A {@code Predicate} which tests whether a given {@code Flashcard}'s Language type matches the given phrase.
 */
public class FlashcardInGivenFlashcardListPredicate implements Predicate<Flashcard> {
    private final List<Flashcard> givenFlashcardList;

    public FlashcardInGivenFlashcardListPredicate(List<Flashcard> givenFlashcardList) {
        this.givenFlashcardList = givenFlashcardList;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return givenFlashcardList.contains(flashcard);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlashcardInGivenFlashcardListPredicate
                && givenFlashcardList.equals(((FlashcardInGivenFlashcardListPredicate) other).givenFlashcardList));
    }
}
