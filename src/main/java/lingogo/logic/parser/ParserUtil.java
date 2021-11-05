package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_INDEX_RANGE;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import lingogo.commons.core.index.Index;
import lingogo.commons.util.StringUtil;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {


    /**
     * Parses {@code indexPair} into an {Index} pair and returns it.
     *
     * @throws ParseException if an index pair is not given of if indices given are invalid
     */
    public static Pair<Index, Index> parseIndexPair(String indexPair) throws ParseException {
        List<Index> indexList = parseIndices(indexPair);

        if (indexList.size() != 2 || indexList.get(0).getZeroBased() > indexList.get(1).getZeroBased()) {
            throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
        }
        return new Pair<>(indexList.get(0), indexList.get(1));
    }


    /**
     * Parses {@code indices} into an {@code Index} list and returns it.
     *
     * @throws ParseException if any specified indices is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String indices) throws ParseException {
        String[] splitIndices = indices.trim().split("\\s+");
        List<Index> indexList = new ArrayList<>();
        for (String index
                : splitIndices) {
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new ParseException(MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
            }
            indexList.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        return indexList;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String phrase} into a {@code Phrase}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phrase} is invalid.
     */
    public static Phrase parsePhrase(String phrase) throws ParseException {
        requireNonNull(phrase);
        String trimmedName = phrase.trim();
        if (!Phrase.isValidPhrase(trimmedName)) {
            throw new ParseException(Phrase.MESSAGE_CONSTRAINTS);
        }
        return new Phrase(trimmedName);
    }

    /**
     * Parses a {@code String languageType} into a {@code LanguageType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code languageType} is invalid.
     */
    public static LanguageType parseLanguageType(String languageType) throws ParseException {
        requireNonNull(languageType);
        String trimmedName = languageType.trim();
        if (!LanguageType.isValidLanguageType(trimmedName)) {
            throw new ParseException(LanguageType.MESSAGE_CONSTRAINTS);
        }
        return new LanguageType(trimmedName);
    }
}
