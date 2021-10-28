package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;

/**
 * Deletes a flashcard identified using it's displayed index from the flashcard app.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_DESCRIPTION =
            "Deletes a flashcard identified by the index number used in the displayed flashcard list.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
        Parameter.INDEX.withCondition("must be a positive integer")
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " 3"
    };

    public static final String MESSAGE_USAGE = getUsageMessage();

    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "Deleted Flashcard: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    private static String getUsageMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND_WORD).append(": ");
        sb.append(COMMAND_DESCRIPTION);
        sb.append("Parameters:");
        for (String parameter : COMMAND_PARAMETERS) {
            sb.append(" ").append(parameter);
        }
        sb.append("\n");
        sb.append("Examples:");
        for (String example : COMMAND_EXAMPLES) {
            sb.append(" ").append(example);
        }
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFlashcard(flashcardToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
