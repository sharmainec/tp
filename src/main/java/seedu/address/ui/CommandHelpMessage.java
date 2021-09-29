package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandEnum;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;

public class CommandHelpMessage extends UiPart<TitledPane> {
    private static final String FXML = "CommandHelpMessage.fxml";

    // TODO: Shift help messages to respective command classes when implemented
    private static final String FLIP_COMMAND_DESCRIPTION = "Toggles the flashcard to either show or hide its English"
            + " translation.";
    private static final String FLIP_COMMAND_USAGE = "flip INDEX";
    private static final String FLIP_COMMAND_EXAMPLES = "flip 3";
    private static final String TEST_COMMAND_DESCRIPTION = "Checks whether the English translation of a flashcard"
            + " matches a given phrase.";
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
            this.setDisplayText("Add", AddCommand.COMMAND_DESCRIPTION, AddCommand.COMMAND_USAGE,
                    AddCommand.COMMAND_EXAMPLES);
            break;
        case EDIT:
            this.setDisplayText("Edit", EditCommand.EDIT_COMMAND_DESCRIPTION, EditCommand.EDIT_COMMAND_USAGE,
                    EditCommand.EDIT_COMMAND_EXAMPLES);
            break;
        case FLIP:
            this.setDisplayText("Flip", FLIP_COMMAND_DESCRIPTION, FLIP_COMMAND_USAGE,
                    FLIP_COMMAND_EXAMPLES);
            break;
        case TEST:
            this.setDisplayText("Test", TEST_COMMAND_DESCRIPTION, TEST_COMMAND_USAGE,
                    TEST_COMMAND_EXAMPLES);
            break;
        case DELETE:
            this.setDisplayText("Delete", DeleteCommand.COMMAND_DESCRIPTION, DeleteCommand.COMMAND_USAGE,
                    DeleteCommand.COMMAND_EXAMPLES);
            break;
        case EXPORT:
            this.setDisplayText("Export", EXPORT_COMMAND_DESCRIPTION, EXPORT_COMMAND_USAGE,
                    EXPORT_COMMAND_EXAMPLES);
            break;
        case IMPORT:
            this.setDisplayText("Upload", IMPORT_COMMAND_DESCRIPTION, IMPORT_COMMAND_USAGE,
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
