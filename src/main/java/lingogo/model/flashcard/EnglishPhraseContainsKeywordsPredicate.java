package lingogo.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import lingogo.commons.util.StringUtil;

/**
 * A {@code Predicate} which tests a given {@code Flashcard} against a list of given keywords.
 */
public class EnglishPhraseContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public EnglishPhraseContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getEnglishPhrase().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EnglishPhraseContainsKeywordsPredicate
                && keywords.equals(((EnglishPhraseContainsKeywordsPredicate) other).keywords));
    }
}
