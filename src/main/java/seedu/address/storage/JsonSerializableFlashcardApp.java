package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FlashcardApp;
import seedu.address.model.ReadOnlyFlashcardApp;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable seedu.address.model.FlashcardApp that is serializable to JSON format.
 */
@JsonRootName(value = "flashcardapp")
class JsonSerializableFlashcardApp {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashcardApp} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableFlashcardApp(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyFlashcardApp} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashcardApp}.
     */
    public JsonSerializableFlashcardApp(ReadOnlyFlashcardApp source) {
        flashcards.addAll(
            source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard app into the model's {@code seedu.address.model.FlashcardApp} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FlashcardApp toModelType() throws IllegalValueException {
        FlashcardApp flashcardApp = new FlashcardApp();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (flashcardApp.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            flashcardApp.addFlashcard(flashcard);
        }
        return flashcardApp;
    }

}
