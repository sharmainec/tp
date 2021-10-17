package lingogo.testutil;

import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import lingogo.logic.commands.AddCommand;
import lingogo.logic.commands.EditCommand.EditFlashcardDescriptor;
import lingogo.model.flashcard.Flashcard;

/**
 * A utility class for Flashcard.
 */
public class FlashcardUtil {

    /**
     * Returns an add command string for adding the {@code flashcard}.
     */
    public static String getAddCommand(Flashcard flashcard) {
        return AddCommand.COMMAND_WORD + " " + getFlashcardDetails(flashcard);
    }

    /**
     * Returns the part of command string for the given {@code flashcard}'s details.
     */
    public static String getFlashcardDetails(Flashcard flashcard) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_LANGUAGE_TYPE + flashcard.getLanguageType().value + " ");
        sb.append(PREFIX_ENGLISH_PHRASE + flashcard.getEnglishPhrase().value + " ");
        sb.append(PREFIX_FOREIGN_PHRASE + flashcard.getForeignPhrase().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFlashcardDescriptor}'s details.
     */
    public static String getEditFlashcardDescriptorDetails(EditFlashcardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getLanguageType().ifPresent(languageType -> sb.append(PREFIX_LANGUAGE_TYPE)
                .append(languageType.value).append(" "));
        descriptor.getEnglishPhrase().ifPresent(englishPhrase -> sb.append(PREFIX_ENGLISH_PHRASE)
                .append(englishPhrase.value).append(" "));
        descriptor.getForeignPhrase().ifPresent(foreignPhrase -> sb.append(PREFIX_FOREIGN_PHRASE)
                .append(foreignPhrase.value).append(" "));
        return sb.toString();
    }
}
