package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.DESC_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.EditCommand.EditFlashcardDescriptor;
import lingogo.testutil.EditFlashcardDescriptorBuilder;

public class EditFlashcardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFlashcardDescriptor descriptorWithSameValues = new EditFlashcardDescriptor(DESC_GOOD_MORNING);
        assertTrue(DESC_GOOD_MORNING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GOOD_MORNING.equals(DESC_GOOD_MORNING));

        // null -> returns false
        assertFalse(DESC_GOOD_MORNING.equals(null));

        // different types -> returns false
        assertFalse(DESC_GOOD_MORNING.equals(5));

        // different values -> returns false
        assertFalse(DESC_GOOD_MORNING.equals(DESC_HELLO));

        // different English phrase -> returns false
        EditFlashcardDescriptor editedFlashcard = new EditFlashcardDescriptorBuilder(DESC_GOOD_MORNING)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        assertFalse(DESC_GOOD_MORNING.equals(editedFlashcard));

        // different foreign phrase -> returns false
        editedFlashcard = new EditFlashcardDescriptorBuilder(DESC_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        assertFalse(DESC_GOOD_MORNING.equals(editedFlashcard));
    }
}
