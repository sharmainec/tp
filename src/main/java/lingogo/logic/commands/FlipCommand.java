package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.List;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Toggle between the English and Foreign phrase for the flashcard with the specified index
 */
public class FlipCommand extends Command {

    public static final String COMMAND_WORD = "flip";
    public static final String COMMAND_DESCRIPTION = "Flips the flashcard to hide or show its English phrase";
    public static final String COMMAND_USAGE = "flip INDEX";
    public static final String COMMAND_EXAMPLES = "flip 3";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flips the flashcard to hide or show its English phrase.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FLIP_FLASHCARD_SUCCESS = "Flipped flashcard with Foreign phrase: %1$s";

    private final Index index;

    /**
     * @param index of the flashcard to flip
     */
    public FlipCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToFlip = lastShownList.get(index.getZeroBased());
        Phrase foreignPhrase = flashcardToFlip.getForeignPhrase();

        Flashcard flippedFlashcard = flashcardToFlip.getFlippedFlashcard();

        model.setFlashcard(flashcardToFlip, flippedFlashcard);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        return new CommandResult(String.format(MESSAGE_FLIP_FLASHCARD_SUCCESS, foreignPhrase.value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlipCommand // instanceof handles nulls
                && index.equals(((FlipCommand) other).index)); // state check
    }
}
