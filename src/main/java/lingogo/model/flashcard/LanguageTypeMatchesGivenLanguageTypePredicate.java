package lingogo.model.flashcard;

import java.util.function.Predicate;

/**
 * A {@code Predicate} which tests whether a given {@code Flashcard}'s Language type matches the given language type.
 */
public class LanguageTypeMatchesGivenLanguageTypePredicate implements Predicate<Flashcard> {
    private final LanguageType givenLanguageType;

    public LanguageTypeMatchesGivenLanguageTypePredicate(LanguageType givenLanguageType) {
        this.givenLanguageType = givenLanguageType;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return flashcard.getLanguageType().value.trim().equalsIgnoreCase(givenLanguageType.value.trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LanguageTypeMatchesGivenLanguageTypePredicate
                && givenLanguageType
                .equals(((LanguageTypeMatchesGivenLanguageTypePredicate) other).givenLanguageType));
    }
}
