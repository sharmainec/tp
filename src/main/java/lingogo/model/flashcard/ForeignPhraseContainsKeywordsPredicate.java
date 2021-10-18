package lingogo.model.flashcard;

import java.util.List;

import lingogo.commons.util.StringUtil;

public class ForeignPhraseContainsKeywordsPredicate extends PhraseContainsKeywordsPredicate {
    public ForeignPhraseContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return super.getKeywords().stream()
                .anyMatch(keyword -> StringUtil.containsForeignCharacter(flashcard.getForeignPhrase().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ForeignPhraseContainsKeywordsPredicate
                && super.getKeywords().equals(((ForeignPhraseContainsKeywordsPredicate) other).getKeywords()));
    }
}
