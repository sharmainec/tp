package lingogo.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import lingogo.commons.core.GuiSettings;
import lingogo.commons.core.LogsCenter;
import lingogo.logic.commands.Command;
import lingogo.logic.commands.CommandResult;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.logic.parser.FlashcardAppParser;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.Model;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.flashcard.Flashcard;
import lingogo.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save/load data to/from flashcardApp.json";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FlashcardAppParser flashcardAppParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        flashcardAppParser = new FlashcardAppParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = flashcardAppParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFlashcardApp(model.getFlashcardApp());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFlashcardApp getFlashcardApp() {
        return model.getFlashcardApp();
    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return model.getFilteredFlashcardList();
    }

    @Override
    public ReadOnlySlideshowApp getSlideshowApp() {
        return model.getSlideshowApp();
    }

    @Override
    public Path getFlashcardAppFilePath() {
        return model.getFlashcardAppFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
