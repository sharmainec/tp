package lingogo.model.flashcard;

import java.util.function.Predicate;

/**
 * A {@code Predicate} which tests whether a given {@code Flashcard}'s English phrase matches the given phrase.
 */
public class EnglishPhraseMatchesGivenPhrasePredicate implements Predicate<Flashcard> {
    private final Phrase givenPhrase;

    public EnglishPhraseMatchesGivenPhrasePredicate(Phrase givenPhrase) {
        this.givenPhrase = givenPhrase;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return flashcard.getEnglishPhrase().value.trim().equalsIgnoreCase(givenPhrase.value.trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EnglishPhraseMatchesGivenPhrasePredicate
                && givenPhrase.equals(((EnglishPhraseMatchesGivenPhrasePredicate) other).givenPhrase));
    }
}
