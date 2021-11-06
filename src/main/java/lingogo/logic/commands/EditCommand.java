package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.commons.util.CollectionUtil;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.FlashcardInGivenFlashcardListPredicate;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * Edits the details of an existing flashcard in the flashcard app.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_DESCRIPTION =
            "Edits a flashcard identified by the index number shown in the displayed flashcard list.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
        Parameter.INDEX.withCondition("must be a positive integer"),
        "[" + PREFIX_LANGUAGE_TYPE + Parameter.LANGUAGE.toString() + "]",
        "[" + PREFIX_ENGLISH_PHRASE + Parameter.ENGLISH_PHRASE.toString() + "]",
        "[" + PREFIX_FOREIGN_PHRASE + Parameter.FOREIGN_PHRASE.toString() + "]"
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " 1 "
                + PREFIX_LANGUAGE_TYPE + "Chinese "
                + PREFIX_ENGLISH_PHRASE + "Hello "
                + PREFIX_FOREIGN_PHRASE + "你好",
        COMMAND_WORD + " 1 "
                + PREFIX_LANGUAGE_TYPE + "German "
                + PREFIX_ENGLISH_PHRASE + "Hello ",
        COMMAND_WORD + " 1 "
                + PREFIX_FOREIGN_PHRASE + "Guten Morgen"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited Flashcard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flashcard app.";

    private final Index index;
    private final EditFlashcardDescriptor editFlashcardDescriptor;

    /**
     * @param index of the flashcard in the filtered flashcard list to edit
     * @param editFlashcardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashcardDescriptor editFlashcardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashcardDescriptor);

        this.index = index;
        this.editFlashcardDescriptor = new EditFlashcardDescriptor(editFlashcardDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToEdit = lastShownList.get(index.getZeroBased());
        Flashcard editedFlashcard = createEditedFlashcard(flashcardToEdit, editFlashcardDescriptor);

        if (!flashcardToEdit.isSameFlashcard(editedFlashcard) && model.hasFlashcard(editedFlashcard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        // Due to the implementation of JavaFX's FilteredList, when an item is replaced via '.set()' in the original
        // ObservableList, it is also removed from the FilteredList. This results in a bug where the editted flashcard
        // no longer appears in the filtered list of flashcards after the edit command is run.
        // To overcome this, we track the flashcards which should be shown after the edit command in the
        // FlashcardInGivenFlashcardListPredicate, and apply this predicate to the filtered flashcard list after the
        // command is run.
        List<Flashcard> updatedList = new ArrayList<>(lastShownList);
        updatedList.remove(index.getZeroBased());
        updatedList.add(editedFlashcard);

        model.setFlashcard(flashcardToEdit, editedFlashcard);
        model.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(updatedList));

        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard));
    }

    /**
     * Creates and returns a {@code Flashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedFlashcard(Flashcard flashcardToEdit,
            EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;

        LanguageType updatedLanguageType = editFlashcardDescriptor.getLanguageType()
                .orElse(flashcardToEdit.getLanguageType());
        Phrase updatedEnglishPhrase = editFlashcardDescriptor.getEnglishPhrase()
                .orElse(flashcardToEdit.getEnglishPhrase());
        Phrase updatedForeignPhrase = editFlashcardDescriptor.getForeignPhrase()
                .orElse(flashcardToEdit.getForeignPhrase());

        return new Flashcard(updatedLanguageType, updatedEnglishPhrase, updatedForeignPhrase);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFlashcardDescriptor.equals(e.editFlashcardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with. Each non-empty field value will replace the
     * corresponding field value of the flashcard.
     */
    public static class EditFlashcardDescriptor {
        private LanguageType languageType;
        private Phrase englishPhrase;
        private Phrase foreignPhrase;

        public EditFlashcardDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditFlashcardDescriptor(EditFlashcardDescriptor toCopy) {
            setLanguageType(toCopy.languageType);
            setEnglishPhrase(toCopy.englishPhrase);
            setForeignPhrase(toCopy.foreignPhrase);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(languageType, englishPhrase, foreignPhrase);
        }

        public void setLanguageType(LanguageType languageType) {
            this.languageType = languageType;
        }

        public Optional<LanguageType> getLanguageType() {
            return Optional.ofNullable(languageType);
        }

        public void setEnglishPhrase(Phrase englishPhrase) {
            this.englishPhrase = englishPhrase;
        }

        public Optional<Phrase> getEnglishPhrase() {
            return Optional.ofNullable(englishPhrase);
        }

        public void setForeignPhrase(Phrase foreignPhrase) {
            this.foreignPhrase = foreignPhrase;
        }

        public Optional<Phrase> getForeignPhrase() {
            return Optional.ofNullable(foreignPhrase);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFlashcardDescriptor)) {
                return false;
            }

            // state check
            EditFlashcardDescriptor e = (EditFlashcardDescriptor) other;

            return getLanguageType().equals(e.getLanguageType())
                    && getEnglishPhrase().equals(e.getEnglishPhrase())
                    && getForeignPhrase().equals(e.getForeignPhrase());
        }
    }
}
