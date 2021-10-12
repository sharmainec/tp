package lingogo.model.flashcard;

import java.util.function.Predicate;

public class EnglishPhraseMatchesGivenPhrasePredicate implements Predicate<Flashcard> {
    private final Phrase givenPhrase;

    public EnglishPhraseMatchesGivenPhrasePredicate(Phrase givenPhrase) {
        this.givenPhrase = givenPhrase;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return flashcard.getEnglishPhrase().value.equalsIgnoreCase(givenPhrase.value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EnglishPhraseMatchesGivenPhrasePredicate
                && givenPhrase.equals(((EnglishPhraseMatchesGivenPhrasePredicate) other).givenPhrase));
    }
}
