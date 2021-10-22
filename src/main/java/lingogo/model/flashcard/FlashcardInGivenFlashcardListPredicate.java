package lingogo.model.flashcard;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * A {@code Predicate} which tests whether a given {@code Flashcard}'s Language type matches the given phrase.
 */
public class FlashcardInGivenFlashcardListPredicate implements Predicate<Flashcard> {

    private final HashSet<Flashcard> givenFlashcardSet;

    /**
     * Converts {@code givenFlashcardList} into a hashset for improved time complexity when testing against given
     * flashcards
     * @param givenFlashcardList
     */
    public FlashcardInGivenFlashcardListPredicate(List<Flashcard> givenFlashcardList) {

        HashSet<Flashcard> tempSet = new HashSet<>();
        givenFlashcardList.forEach(flashcard -> tempSet.add(flashcard));
        this.givenFlashcardSet = tempSet;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return givenFlashcardSet.contains(flashcard);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlashcardInGivenFlashcardListPredicate
                && givenFlashcardSet.equals(((FlashcardInGivenFlashcardListPredicate) other).givenFlashcardSet));
    }
}
