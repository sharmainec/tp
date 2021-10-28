package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;

/**
 * Adds a flashcard to the flashcard app.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_DESCRIPTION = "Adds a flashcard.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
            PREFIX_LANGUAGE_TYPE + Parameter.LANGUAGE.toString(),
            PREFIX_ENGLISH_PHRASE + Parameter.ENGLISH_PHRASE.toString(),
            PREFIX_FOREIGN_PHRASE + Parameter.FOREIGN_PHRASE.toString()
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
            COMMAND_WORD + " "
                    + PREFIX_LANGUAGE_TYPE + "Chinese "
                    + PREFIX_ENGLISH_PHRASE + "Good Morning "
                    + PREFIX_FOREIGN_PHRASE + "早安"
    };

    public static final String MESSAGE_USAGE = getUsageMessage();

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flashcard app";

    private final Flashcard toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Flashcard}
     */
    public AddCommand(Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashcard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private static String getUsageMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND_WORD).append(": ");
        sb.append(COMMAND_DESCRIPTION);
        sb.append("\n");
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
