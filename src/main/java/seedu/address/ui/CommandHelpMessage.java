package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import seedu.address.logic.commands.CommandEnum;

public class CommandHelpMessage extends UiPart<TitledPane> {
    private static final String FXML = "CommandHelpMessage.fxml";
    private static final String ADD_COMMAND_DESCRIPTION = "Adds a flashcard";
    private static final String ADD_COMMAND_USAGE = "add e/ENGLISH_PHRASE f/FOREIGN_PHRASE";
    private static final String ADD_COMMAND_EXAMPLES = "add e/Hello f/你好";
    private static final String DELETE_COMMAND_DESCRIPTION = "Deletes a flashcard";
    private static final String DELETE_COMMAND_USAGE = "delete CARD_NUMBER";
    private static final String DELETE_COMMAND_EXAMPLES = "delete 3";
    private static final String EDIT_COMMAND_DESCRIPTION = "Edits a flashcard";
    private static final String EDIT_COMMAND_USAGE = "edit CARD_NUMBER ([e/ENGLISH_PHRASE] [f/FOREIGN_PHRASE])";
    private static final String EDIT_COMMAND_EXAMPLES = "edit 1 e/Hi f/Hola\nedit 1 e/Hello\nedit 1 f/Guten Morgen";
    private static final String FLIP_COMMAND_DESCRIPTION = "Flips a flashcard to toggle whether its answer is shown";
    private static final String FLIP_COMMAND_USAGE = "flip CARD_NUMBER";
    private static final String FLIP_COMMAND_EXAMPLES = "flip 3";
    private static final String TEST_COMMAND_DESCRIPTION = "Accepts an answer for a flashcard, then displays whether"
            + " the answer is correct";
    private static final String TEST_COMMAND_USAGE = "test CARD_NUMBER a/ANSWER";
    private static final String TEST_COMMAND_EXAMPLES = "test 1 a/good morning";
    private static final String IMPORT_COMMAND_DESCRIPTION = "Imports flashcards into LingoGO! from a specified"
            + " CSV file";
    private static final String IMPORT_COMMAND_USAGE = "upload CSV_FILE_PATH";
    private static final String IMPORT_COMMAND_EXAMPLES = "upload ./dictionary.csv";
    private static final String EXPORT_COMMAND_DESCRIPTION = "Exports flashcards to a CSV file located at"
            + " <TO BE CONFIRMED>";
    private static final String EXPORT_COMMAND_USAGE = "export CSV_FILE_PATH";
    private static final String EXPORT_COMMAND_EXAMPLES = "export ./mycards.csv";

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
            this.setDisplayText("Add", ADD_COMMAND_DESCRIPTION, ADD_COMMAND_USAGE, ADD_COMMAND_EXAMPLES);
            break;
        case EDIT:
            this.setDisplayText("Edit", EDIT_COMMAND_DESCRIPTION, EDIT_COMMAND_USAGE,
                    EDIT_COMMAND_EXAMPLES);
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
            this.setDisplayText("Delete", DELETE_COMMAND_DESCRIPTION, DELETE_COMMAND_USAGE,
                    DELETE_COMMAND_EXAMPLES);
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
