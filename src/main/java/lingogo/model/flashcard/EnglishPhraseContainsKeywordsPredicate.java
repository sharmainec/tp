package lingogo.model.flashcard;

import java.util.List;

import lingogo.commons.util.StringUtil;

public class EnglishPhraseContainsKeywordsPredicate extends PhraseContainsKeywordsPredicate {
    public EnglishPhraseContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return super.getKeywords().stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getEnglishPhrase().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EnglishPhraseContainsKeywordsPredicate
                && super.getKeywords().equals(((EnglishPhraseContainsKeywordsPredicate) other).getKeywords()));
    }
}
