package lingogo.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import lingogo.commons.util.StringUtil;

/**
 * A {@code Predicate} which tests a given {@code Flashcard} against a list of given keywords.
 */
public class PhraseContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public PhraseContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getEnglishPhrase().value, keyword)
                        || StringUtil.containsForeignCharacter(flashcard.getForeignPhrase().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PhraseContainsKeywordsPredicate
                && keywords.equals(((PhraseContainsKeywordsPredicate) other).keywords));
    }
}
