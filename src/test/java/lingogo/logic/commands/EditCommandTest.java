package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static lingogo.logic.commands.CommandTestUtil.DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.DESC_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.BYE_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static lingogo.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.EditCommand.EditFlashcardDescriptor;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.FlashcardInGivenFlashcardListPredicate;
import lingogo.testutil.EditFlashcardDescriptorBuilder;
import lingogo.testutil.FilterBuilderBuilder;
import lingogo.testutil.FlashcardBuilder;

/**
 * Contains integration tests (interaction with the Model and FilterCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Flashcard editedFlashcard = new FlashcardBuilder().build();
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new FlashcardApp(model.getFlashcardApp()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFlashcard = Index.fromOneBased(model.getFilteredFlashcardList().size());
        Flashcard lastFlashcard = model.getFilteredFlashcardList().get(indexLastFlashcard.getZeroBased());

        FlashcardBuilder flashcardInList = new FlashcardBuilder(lastFlashcard);
        Flashcard editedFlashcard = flashcardInList.withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        EditCommand editCommand = new EditCommand(indexLastFlashcard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new FlashcardApp(model.getFlashcardApp()), new UserPrefs());
        expectedModel.setFlashcard(lastFlashcard, editedFlashcard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, new EditFlashcardDescriptor());
        Flashcard editedFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new FlashcardApp(model.getFlashcardApp()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Flashcard flashcardInFilteredList = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard = new FlashcardBuilder(flashcardInFilteredList)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new FlashcardApp(model.getFlashcardApp()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);
        showFlashcardAtIndex(expectedModel, INDEX_FIRST_FLASHCARD);


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFlashcardUnfilteredList_failure() {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(firstFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FLASHCARD, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_duplicateFlashcardFilteredList_failure() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        // edit flashcard in filtered list into a duplicate in flashcard app
        Flashcard flashcardInList = model.getFlashcardApp().getFlashcardList()
                .get(INDEX_SECOND_FLASHCARD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashcardDescriptorBuilder(flashcardInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_invalidFlashcardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of flashcard app
     */
    @Test
    public void execute_invalidFlashcardIndexFilteredList_failure() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of flashcard app list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFlashcardApp().getFlashcardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        Flashcard editedFlashcard = new FlashcardBuilder().build();
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_filterListThenEdit_success() {

        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        FilterCommand.FilterBuilder filterBuilder = new FilterBuilderBuilder().withRange(1, 3).build();
        FilterCommand filterCommand = new FilterCommand(filterBuilder);

        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD),
            model.getFilteredFlashcardList());

        Flashcard editedFlashcard = new FlashcardBuilder(NIGHT_CHINESE_FLASHCARD).withLanguageType("Test").build();

        EditCommand editCommand = new EditCommand(INDEX_SECOND_FLASHCARD,
                new EditFlashcardDescriptorBuilder(editedFlashcard).build());

        expectedModel.setFlashcard(NIGHT_CHINESE_FLASHCARD, editedFlashcard);

        String secondExpectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        List<Flashcard> updatedList = Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, editedFlashcard,
                BYE_CHINESE_FLASHCARD);
        expectedModel.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(updatedList));

        assertCommandSuccess(editCommand, model, secondExpectedMessage, expectedModel);
        assertEquals(updatedList,
                model.getFilteredFlashcardList());
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FLASHCARD, DESC_GOOD_MORNING);

        // same values -> returns true
        EditFlashcardDescriptor copyDescriptor = new EditFlashcardDescriptor(DESC_GOOD_MORNING);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_FLASHCARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_FLASHCARD, DESC_GOOD_MORNING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_FLASHCARD, DESC_HELLO)));
    }

}
