package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Phrase;

/**
 * A utility class to help with building EditFlashcardDescriptor objects.
 */
public class EditFlashcardDescriptorBuilder {

    private EditFlashcardDescriptor descriptor;

    public EditFlashcardDescriptorBuilder() {
        descriptor = new EditFlashcardDescriptor();
    }

    public EditFlashcardDescriptorBuilder(EditFlashcardDescriptor descriptor) {
        this.descriptor = new EditFlashcardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFlashcardDescriptor} with fields containing {@code flashcard}'s details
     */
    public EditFlashcardDescriptorBuilder(Flashcard flashcard) {
        descriptor = new EditFlashcardDescriptor();
        descriptor.setEnglishPhrase(flashcard.getEnglishPhrase());
        descriptor.setForeignPhrase(flashcard.getForeignPhrase());
    }

    /**
     * Sets the {@code englishPhrase} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withEnglishPhrase(String englishPhrase) {
        descriptor.setEnglishPhrase(new Phrase(englishPhrase));
        return this;
    }

    /**
     * Sets the {@code foreignPhrase} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withForeignPhrase(String foreignPhrase) {
        descriptor.setForeignPhrase(new Phrase(foreignPhrase));
        return this;
    }

    public EditFlashcardDescriptor build() {
        return descriptor;
    }

}
