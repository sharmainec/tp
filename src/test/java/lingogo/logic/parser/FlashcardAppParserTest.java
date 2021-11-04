package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static lingogo.logic.commands.CommandTestUtil.INDICES_DESC_DESC_ONE_TWO;
import static lingogo.logic.commands.CommandTestUtil.RANGE_DESC_TWO_FOUR;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_TAMIL;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;
import static lingogo.testutil.Assert.assertThrows;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.AddCommand;
import lingogo.logic.commands.AnswerCommand;
import lingogo.logic.commands.ClearCommand;
import lingogo.logic.commands.DeleteCommand;
import lingogo.logic.commands.EditCommand;
import lingogo.logic.commands.EditCommand.EditFlashcardDescriptor;
import lingogo.logic.commands.ExitCommand;
import lingogo.logic.commands.ExportCommand;
import lingogo.logic.commands.FilterCommand;
import lingogo.logic.commands.FindCommand;
import lingogo.logic.commands.HelpCommand;
import lingogo.logic.commands.ImportCommand;
import lingogo.logic.commands.ListCommand;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.ForeignPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;
import lingogo.testutil.EditFlashcardDescriptorBuilder;
import lingogo.testutil.FilterBuilderBuilder;
import lingogo.testutil.FlashcardBuilder;
import lingogo.testutil.FlashcardUtil;


public class FlashcardAppParserTest {

    private final FlashcardAppParser parser = new FlashcardAppParser();

    @Test
    public void parseCommand_add() throws Exception {
        Flashcard flashcard = new FlashcardBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(FlashcardUtil.getAddCommand(flashcard));
        assertEquals(new AddCommand(flashcard), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_FLASHCARD), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Flashcard flashcard = new FlashcardBuilder().build();
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(flashcard).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FLASHCARD.getOneBased() + " "
                + FlashcardUtil.getEditFlashcardDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_FLASHCARD, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findEnglish() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " e/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new EnglishPhraseContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findForeign() throws Exception {
        List<String> keywords = Arrays.asList("早", "晚", "好");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " f/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new ForeignPhraseContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMixed() throws Exception {
        List<String> keywords = Arrays.asList("早", "foo", "bar", "好");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " f/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PhraseContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        LanguageType givenPhrase = new LanguageType(VALID_LANGUAGE_TYPE_TAMIL);
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + PREFIX_LANGUAGE_TYPE + VALID_LANGUAGE_TYPE_TAMIL
                + INDICES_DESC_DESC_ONE_TWO + RANGE_DESC_TWO_FOUR);

        assertEquals(
                new FilterCommand(new FilterBuilderBuilder().withIndexList(1, 2).withLanguageType(givenPhrase)
                .withRange(2, 4).build()), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_export() throws Exception {
        String csvFileName = "myCards.csv";
        ExportCommand command = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + csvFileName);
        assertEquals(new ExportCommand(csvFileName), command);
    }

    @Test
    public void parseCommand_import() throws Exception {
        File data = new File("data");
        if (!data.exists()) {
            data.mkdir();
        }
        String csvFileName = "newContent.csv";
        Path srcFile = Paths.get("src/test/data/SampleCsvFiles/" + csvFileName).toAbsolutePath();
        Path destFile = Paths.get("data/" + csvFileName).toAbsolutePath();
        Files.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING);
        ImportCommand command = (ImportCommand) parser.parseCommand(
                ImportCommand.COMMAND_WORD + " " + csvFileName);
        assertEquals(new ImportCommand(csvFileName), command);
        File file = new File("data/" + csvFileName);
        file.delete();
    }

    @Test
    public void parseCommand_answer() throws Exception {
        AnswerCommand command = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_WORD + " " + " " + PREFIX_ENGLISH_PHRASE
                + VALID_ENGLISH_PHRASE_GOOD_MORNING);
        assertEquals(new AnswerCommand(ParserUtil.parsePhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
