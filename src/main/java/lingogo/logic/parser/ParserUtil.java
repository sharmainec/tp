package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import lingogo.commons.core.index.Index;
import lingogo.commons.util.StringUtil;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.Phrase;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code indices} into an {@code Index} list and returns it.
     *
     * @throws ParseException if any specified indices is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String indices) throws ParseException {
        String[] splitIndices = indices.trim().split(" ");
        List<Index> indexList = new ArrayList<>();
        for (String index
                : splitIndices) {
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            indexList.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        return indexList;
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
}
