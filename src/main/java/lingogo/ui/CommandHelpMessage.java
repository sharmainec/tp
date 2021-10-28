package lingogo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import lingogo.logic.commands.AddCommand;
import lingogo.logic.commands.AnswerCommand;
import lingogo.logic.commands.ClearCommand;
import lingogo.logic.commands.CommandEnum;
import lingogo.logic.commands.DeleteCommand;
import lingogo.logic.commands.EditCommand;
import lingogo.logic.commands.ExitCommand;
import lingogo.logic.commands.ExportCommand;
import lingogo.logic.commands.FilterCommand;
import lingogo.logic.commands.FindCommand;
import lingogo.logic.commands.HelpCommand;
import lingogo.logic.commands.ImportCommand;
import lingogo.logic.commands.ListCommand;
import lingogo.logic.commands.NextSlideCommand;
import lingogo.logic.commands.PreviousSlideCommand;
import lingogo.logic.commands.SlideshowCommand;
import lingogo.logic.commands.StopSlideshowCommand;

public class CommandHelpMessage extends UiPart<TitledPane> {
    private static final String FXML = "CommandHelpMessage.fxml";

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
            this.setDisplayText(AddCommand.COMMAND_WORD, AddCommand.COMMAND_DESCRIPTION,
                    AddCommand.COMMAND_PARAMETERS, AddCommand.COMMAND_EXAMPLES);
            break;
        case CLEAR:
            this.setDisplayText(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_DESCRIPTION,
                    ClearCommand.COMMAND_PARAMETERS, ClearCommand.COMMAND_EXAMPLES);
            break;
        case DELETE:
            this.setDisplayText(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_DESCRIPTION,
                    DeleteCommand.COMMAND_PARAMETERS, DeleteCommand.COMMAND_EXAMPLES);
            break;
        case EDIT:
            this.setDisplayText(EditCommand.COMMAND_WORD, EditCommand.COMMAND_DESCRIPTION,
                    EditCommand.COMMAND_PARAMETERS, EditCommand.COMMAND_EXAMPLES);
            break;
        case EXIT:
            this.setDisplayText(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_DESCRIPTION,
                    ExitCommand.COMMAND_PARAMETERS, ExitCommand.COMMAND_EXAMPLES);
            break;
        case EXPORT:
            this.setDisplayText(ExportCommand.COMMAND_WORD, ExportCommand.COMMAND_DESCRIPTION,
                    ExportCommand.COMMAND_PARAMETERS, ExportCommand.COMMAND_EXAMPLES);
            break;
        case FILTER:
            this.setDisplayText(FilterCommand.COMMAND_WORD, FilterCommand.COMMAND_DESCRIPTION,
                    FilterCommand.COMMAND_PARAMETERS, FilterCommand.COMMAND_EXAMPLES);
            break;
        case FIND:
            this.setDisplayText(FindCommand.COMMAND_WORD, FindCommand.COMMAND_DESCRIPTION,
                    FindCommand.COMMAND_PARAMETERS, FindCommand.COMMAND_EXAMPLES);
            break;
        case HELP:
            this.setDisplayText(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_DESCRIPTION,
                    HelpCommand.COMMAND_PARAMETERS, HelpCommand.COMMAND_EXAMPLES);
            break;
        case IMPORT:
            this.setDisplayText(ImportCommand.COMMAND_WORD, ImportCommand.COMMAND_DESCRIPTION,
                    ImportCommand.COMMAND_PARAMETERS, ImportCommand.COMMAND_EXAMPLES);
            break;
        case LIST:
            this.setDisplayText(ListCommand.COMMAND_WORD, ListCommand.COMMAND_DESCRIPTION,
                    ListCommand.COMMAND_PARAMETERS, ListCommand.COMMAND_EXAMPLES);
            break;

        // Slideshow commands
        case ANSWER:
            this.setDisplayText(AnswerCommand.COMMAND_WORD, AnswerCommand.COMMAND_DESCRIPTION,
                    AnswerCommand.COMMAND_PARAMETERS, AnswerCommand.COMMAND_EXAMPLES);
            break;
        case NEXT:
            this.setDisplayText(NextSlideCommand.COMMAND_WORD, NextSlideCommand.COMMAND_DESCRIPTION,
                    NextSlideCommand.COMMAND_PARAMETERS, NextSlideCommand.COMMAND_EXAMPLES);
            break;
        case PREVIOUS:
            this.setDisplayText(PreviousSlideCommand.COMMAND_WORD, PreviousSlideCommand.COMMAND_DESCRIPTION,
                    PreviousSlideCommand.COMMAND_PARAMETERS, PreviousSlideCommand.COMMAND_EXAMPLES);
            break;
        case SLIDESHOW:
            this.setDisplayText(SlideshowCommand.COMMAND_WORD, SlideshowCommand.COMMAND_DESCRIPTION,
                    SlideshowCommand.COMMAND_PARAMETERS, SlideshowCommand.COMMAND_EXAMPLES);
            break;
        case STOP:
            this.setDisplayText(StopSlideshowCommand.COMMAND_WORD, StopSlideshowCommand.COMMAND_DESCRIPTION,
                    StopSlideshowCommand.COMMAND_PARAMETERS, StopSlideshowCommand.COMMAND_EXAMPLES);
            break;
        default:
            assert false : "CommandHelpMessage: Invalid command";
        }
    }

    private void setDisplayText(String command, String description, String[] parameters, String[] examples) {
        this.getRoot().setText(command);
        this.description.setText(description);

        StringBuilder commandParameters = new StringBuilder(command);
        for (String parameter : parameters) {
            commandParameters.append(" ").append(parameter);
        }
        this.usage.setText(commandParameters.toString());

        StringBuilder commandExamples = new StringBuilder();
        for (String example : examples) {
            commandExamples.append(example).append("\n");
        }
        this.examples.setText(commandExamples.toString().trim());
    }
}
