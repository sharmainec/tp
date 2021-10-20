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
import lingogo.logic.commands.FlipCommand;
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
        case FILTER:
            this.setDisplayText(FilterCommand.COMMAND_WORD, FilterCommand.COMMAND_DESCRIPTION,
                    FilterCommand.COMMAND_USAGE, FilterCommand.COMMAND_EXAMPLES);
            break;
        case FLIP:
            this.setDisplayText(FlipCommand.COMMAND_WORD, FlipCommand.COMMAND_DESCRIPTION, FlipCommand.COMMAND_USAGE,
                    FlipCommand.COMMAND_EXAMPLES);
            break;
        case HELP:
            this.setDisplayText(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_DESCRIPTION, HelpCommand.COMMAND_USAGE,
                    HelpCommand.COMMAND_EXAMPLES);
            break;
        case LIST:
            this.setDisplayText(ListCommand.COMMAND_WORD, ListCommand.COMMAND_DESCRIPTION, ListCommand.COMMAND_USAGE,
                    ListCommand.COMMAND_EXAMPLES);
            break;
        case EXPORT:
            this.setDisplayText(ExportCommand.COMMAND_WORD, ExportCommand.COMMAND_DESCRIPTION, ExportCommand.COMMAND_USAGE,
                    ExportCommand.COMMAND_EXAMPLES);
            break;
        case IMPORT:
            this.setDisplayText(ImportCommand.COMMAND_WORD, ImportCommand.COMMAND_DESCRIPTION, ImportCommand.COMMAND_USAGE,
                    ImportCommand.COMMAND_EXAMPLES);
            break;
        case ANSWER:
            this.setDisplayText(AnswerCommand.COMMAND_WORD, AnswerCommand.COMMAND_DESCRIPTION, AnswerCommand.COMMAND_USAGE,
                    AnswerCommand.COMMAND_EXAMPLES);
            break;
        case NEXT:
            this.setDisplayText(NextSlideCommand.COMMAND_WORD, NextSlideCommand.COMMAND_DESCRIPTION, NextSlideCommand.COMMAND_USAGE,
                    NextSlideCommand.COMMAND_EXAMPLES);
        case PREVIOUS:
            this.setDisplayText(PreviousSlideCommand.COMMAND_WORD, PreviousSlideCommand.COMMAND_DESCRIPTION, PreviousSlideCommand.COMMAND_USAGE,
                    PreviousSlideCommand.COMMAND_EXAMPLES);
        case SLIDESHOW:
            this.setDisplayText(SlideshowCommand.COMMAND_WORD, SlideshowCommand.COMMAND_DESCRIPTION, SlideshowCommand.COMMAND_USAGE,
                    SlideshowCommand.COMMAND_EXAMPLES);
        case STOP:
            this.setDisplayText(StopSlideshowCommand.COMMAND_WORD, StopSlideshowCommand.COMMAND_DESCRIPTION, StopSlideshowCommand.COMMAND_USAGE,
                    StopSlideshowCommand.COMMAND_EXAMPLES);
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
