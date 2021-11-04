package lingogo.model.flashcard;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * A {@code Predicate} which tests whether a given {@code Flashcard} is contained in a specified list of flashcards.
 */
public class FlashcardInGivenFlashcardListPredicate implements Predicate<Flashcard> {

    /**
     * A hashset is used for improved time complexity when testing if
     * a given flashcard is contained in the given set of flashcards.
     */
    private final HashSet<Flashcard> givenFlashcardSet;

    /**
     * Constructs a predicate that will check if a specified flashcard
     * is in the list of specified flashcards.
     * @param givenFlashcardList The list of specified flashcards.
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
