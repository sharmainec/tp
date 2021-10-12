package lingogo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import lingogo.logic.commands.AddCommand;
import lingogo.logic.commands.ClearCommand;
import lingogo.logic.commands.CommandEnum;
import lingogo.logic.commands.DeleteCommand;
import lingogo.logic.commands.EditCommand;
import lingogo.logic.commands.ExitCommand;
import lingogo.logic.commands.FindCommand;
import lingogo.logic.commands.FlipCommand;
import lingogo.logic.commands.HelpCommand;
import lingogo.logic.commands.ListCommand;

public class CommandHelpMessage extends UiPart<TitledPane> {
    private static final String FXML = "CommandHelpMessage.fxml";

    // TODO: Shift help messages to respective command classes when implemented
    private static final String TEST_COMMAND_DESCRIPTION = "Checks whether the English phrase of a flashcard"
            + " matches a given phrase";
    private static final String TEST_COMMAND_USAGE = "test INDEX a/ENGLISH_PHRASE";
    private static final String TEST_COMMAND_EXAMPLES = "test 1 a/good morning";
    private static final String IMPORT_COMMAND_DESCRIPTION = "Imports flashcards into LingoGO! from a specified"
            + " CSV file";
    private static final String IMPORT_COMMAND_USAGE = "upload CSV_FILE_PATH";
    private static final String IMPORT_COMMAND_EXAMPLES = "upload ./dictionary.csv";
    private static final String EXPORT_COMMAND_DESCRIPTION = "Exports flashcards to a CSV file located in the data"
            + " folder";
    private static final String EXPORT_COMMAND_USAGE = "download CSV_FILE_PATH";
    private static final String EXPORT_COMMAND_EXAMPLES = "download";

    @FXML
    private TitledPane helpPane;
    @FXML
    private Label description;
    @FXML
    private Label usage;
    @FXML
    private Label examples;

    private HelpWindow helpWindow;

    /**
     * Creates a new CommandHelpMessage for a specified command.
     *
     * @param command The command that the help message is explaining.
     * @param helpWindow The HelpWindow that the CommandHelpMessage is contained in.
     */
    public CommandHelpMessage(CommandEnum command, HelpWindow helpWindow) {
        super(FXML);
        this.helpWindow = helpWindow;
        this.getRoot().heightProperty().addListener((obs, oldHeight, newHeight) -> helpWindow.getRoot().sizeToScene());
        switch (command) {
        case ADD:
            this.setDisplayText(AddCommand.COMMAND_WORD, AddCommand.COMMAND_DESCRIPTION, AddCommand.COMMAND_USAGE,
                    AddCommand.COMMAND_EXAMPLES);
            break;
        case CLEAR:
            this.setDisplayText(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_DESCRIPTION, ClearCommand.COMMAND_USAGE,
                    ClearCommand.COMMAND_EXAMPLES);
            break;
        case DELETE:
            this.setDisplayText(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_DESCRIPTION,
                    DeleteCommand.COMMAND_USAGE, DeleteCommand.COMMAND_EXAMPLES);
            break;
        case EDIT:
            this.setDisplayText(EditCommand.COMMAND_WORD, EditCommand.COMMAND_DESCRIPTION, EditCommand.COMMAND_USAGE,
                    EditCommand.COMMAND_EXAMPLES);
            break;
        case EXIT:
            this.setDisplayText(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_DESCRIPTION, ExitCommand.COMMAND_USAGE,
                    ExitCommand.COMMAND_EXAMPLES);
            break;
        case FIND:
            this.setDisplayText(FindCommand.COMMAND_WORD, FindCommand.COMMAND_DESCRIPTION, FindCommand.COMMAND_USAGE,
                    FindCommand.COMMAND_EXAMPLES);
            break;
        case FLIP:
            this.setDisplayText("flip", FlipCommand.FLIP_COMMAND_DESCRIPTION, FlipCommand.FLIP_COMMAND_USAGE,
                    FlipCommand.FLIP_COMMAND_EXAMPLES);
            break;
        case HELP:
            this.setDisplayText(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_DESCRIPTION, HelpCommand.COMMAND_USAGE,
                    HelpCommand.COMMAND_EXAMPLES);
            break;
        case LIST:
            this.setDisplayText(ListCommand.COMMAND_WORD, ListCommand.COMMAND_DESCRIPTION, ListCommand.COMMAND_USAGE,
                    ListCommand.COMMAND_EXAMPLES);
            break;
        case TEST:
            this.setDisplayText("test", TEST_COMMAND_DESCRIPTION, TEST_COMMAND_USAGE,
                    TEST_COMMAND_EXAMPLES);
            break;
        case EXPORT:
            this.setDisplayText("download", EXPORT_COMMAND_DESCRIPTION, EXPORT_COMMAND_USAGE,
                    EXPORT_COMMAND_EXAMPLES);
            break;
        case IMPORT:
            this.setDisplayText("upload", IMPORT_COMMAND_DESCRIPTION, IMPORT_COMMAND_USAGE,
                    IMPORT_COMMAND_EXAMPLES);
            break;
        default:
            assert false : "CommandHelpMessage: Invalid command";
        }
    }

    private void setDisplayText(String command, String description, String usage, String examples) {
        this.getRoot().setText(command);
        this.description.setText(description);
        this.usage.setText(usage);
        this.examples.setText(examples);
    }
}
