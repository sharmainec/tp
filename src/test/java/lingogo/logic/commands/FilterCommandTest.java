package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_TAMIL;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.FilterCommand.FilterBuilder;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.BYE_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HAPPY_ANNIVERSARY_CHINESE;
import static lingogo.testutil.TypicalFlashcards.HAPPY_BIRTHDAY;
import static lingogo.testutil.TypicalFlashcards.HAPPY_BIRTHDAY_JAPANESE;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SORRY_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SUNRISE_TAMIL_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.testutil.FilterBuilderBuilder;

/**
 * Contains integration tests (interaction with the Model and FindCommand) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {

        FilterBuilder firstFilterBuilder =
                new FilterBuilderBuilder().withIndexList(1, 2).withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withRange(2, 4).build();
        FilterBuilder firstFilterBuilderAgain =
                new FilterBuilderBuilder().withIndexList(1, 2).withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withRange(2, 4).build();
        FilterBuilder secondFilterBuilder =
                new FilterBuilderBuilder().withIndexList(1).withLanguageType(VALID_LANGUAGE_TYPE_TAMIL)
                .withRange(1, 3).build();
        FilterBuilder thirdFilterBuilder =
                new FilterBuilderBuilder().withIndexList(1, 2).withLanguageType(VALID_LANGUAGE_TYPE_TAMIL)
                .withRange(2, 4).build();
        FilterBuilder fourthFilterBuilder =
                new FilterBuilderBuilder().withIndexList(1).withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withRange(2, 4).build();
        FilterBuilder fifthFilterBuilder =
            new FilterBuilderBuilder().withIndexList(1, 2).withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withRange(1, 3).build();


        FilterCommand firstFilterCommand = new FilterCommand(firstFilterBuilder);
        FilterCommand secondFilterCommand = new FilterCommand(secondFilterBuilder);
        FilterCommand thirdFilterCommand = new FilterCommand(thirdFilterBuilder);
        FilterCommand fourthFilterCommand = new FilterCommand(fourthFilterBuilder);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFilterCommandCopy = new FilterCommand(firstFilterBuilderAgain);
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // all fields different-> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));

        // language field different -> returns false
        assertFalse(firstFilterCommand.equals(thirdFilterCommand));

        // index list field different -> returns false
        assertFalse(firstFilterCommand.equals(fourthFilterCommand));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different range field -> returns false
        assertFalse(firstFilterCommand.equals(fifthFilterBuilder));
    }

    @Test
    public void execute_unknownLanguage_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguageType("Unknown language").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_invalidIndexInIndexList_failure() {
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withIndexList(6).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        assertCommandFailure(command, model, MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexInRange_failure() {
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withIndexList(1, 1000).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        assertCommandFailure(command, model, MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexKeywordTamil_failure() {
        FilterBuilder filterBuilder =
                new FilterBuilderBuilder().withIndexList(1000).withLanguageType("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        assertCommandFailure(command, model, MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }


    @Test
    public void execute_keywordTamil_tamilFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 1);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguageType("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SUNRISE_TAMIL_FLASHCARD), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguageType("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_selectedIndices_flashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 2);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withIndexList(1, 2).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    @Test
    public void execute_selectedRange_flashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withRange(2, 4).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }



    @Test
    public void execute_selectedAllFields_flashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 4);
        FilterBuilder filterBuilder =
                new FilterBuilderBuilder().withIndexList(1).withLanguageType("Tamil").withRange(2, 3).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD,
                SUNRISE_TAMIL_FLASHCARD), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_filtersInSequence_flashcardsFound() {
        String firstExpectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 4);
        String secondExpectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);

        FilterBuilder firstFilterBuilder = new FilterBuilderBuilder().withLanguageType("Chinese").build();

        FilterBuilder secondFilterBuilder = new FilterBuilderBuilder().withRange(1, 3).build();

        FilterCommand firstCommand = new FilterCommand(firstFilterBuilder);
        FilterCommand secondCommand = new FilterCommand(secondFilterBuilder);

        try {
            expectedModel.updateFilteredFlashcardList(firstFilterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }

        assertCommandSuccess(firstCommand, model, firstExpectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD,
            SORRY_CHINESE_FLASHCARD), model.getFilteredFlashcardList());

        try {
            expectedModel.updateFilteredFlashcardList(secondFilterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }


        assertCommandSuccess(secondCommand, model, secondExpectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());


    }


    @Test
    public void execute_filterFoundFlashcards_flashcardsFound() {
        FlashcardApp flashcardApp = getTypicalFlashcardApp();
        flashcardApp.addFlashcard(HAPPY_ANNIVERSARY_CHINESE);
        flashcardApp.addFlashcard(HAPPY_BIRTHDAY);
        flashcardApp.addFlashcard(HAPPY_BIRTHDAY_JAPANESE);

        // instantiate test and expected models
        Model testModel = new ModelManager(flashcardApp, new UserPrefs());
        Model expectedResultModel = new ModelManager(flashcardApp, new UserPrefs());

        // instantiate expected test messages
        String firstExpectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        String secondExpectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 2);

        List<String> keywords = new ArrayList<>();
        keywords.add("happy");
        FindCommand findCommand = new FindCommand(new EnglishPhraseContainsKeywordsPredicate(keywords));
        expectedResultModel.updateFilteredFlashcardList(new EnglishPhraseContainsKeywordsPredicate(keywords));

        // test find
        assertCommandSuccess(findCommand, testModel, firstExpectedMessage, expectedResultModel);
        assertEquals(Arrays.asList(HAPPY_ANNIVERSARY_CHINESE, HAPPY_BIRTHDAY, HAPPY_BIRTHDAY_JAPANESE),
                testModel.getFilteredFlashcardList());


        FilterBuilder filterBuilder =
                new FilterBuilderBuilder().withLanguageType("Chinese").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedResultModel.updateFilteredFlashcardList(filterBuilder.buildFilter(testModel));
        } catch (Exception e) {
            fail("Exception not expected");
        }

        // test filter
        assertCommandSuccess(command, testModel, secondExpectedMessage, expectedResultModel);
        assertEquals(Arrays.asList(HAPPY_ANNIVERSARY_CHINESE, HAPPY_BIRTHDAY),
                testModel.getFilteredFlashcardList());
    }



}
