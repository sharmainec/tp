package lingogo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import lingogo.commons.core.Config;
import lingogo.commons.core.LogsCenter;
import lingogo.commons.core.Version;
import lingogo.commons.exceptions.DataConversionException;
import lingogo.commons.util.ConfigUtil;
import lingogo.commons.util.StringUtil;
import lingogo.logic.Logic;
import lingogo.logic.LogicManager;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlyUserPrefs;
import lingogo.model.UserPrefs;
import lingogo.model.util.SampleDataUtil;
import lingogo.storage.FlashcardAppStorage;
import lingogo.storage.JsonFlashcardAppStorage;
import lingogo.storage.JsonUserPrefsStorage;
import lingogo.storage.Storage;
import lingogo.storage.StorageManager;
import lingogo.storage.UserPrefsStorage;
import lingogo.ui.Ui;
import lingogo.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 1, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing LingoGO! ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        FlashcardAppStorage flashcardAppStorage = new JsonFlashcardAppStorage(userPrefs.getFlashcardAppFilePath());
        storage = new StorageManager(flashcardAppStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s flashcard app and {@code userPrefs}. <br>
     * The data from the sample flashcard app will be used instead if {@code storage}'s flashcard app is not found,
     * or an empty flashcard app will be used instead if errors occur when reading {@code storage}'s flashcard app.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyFlashcardApp> flashcardAppOptional;
        ReadOnlyFlashcardApp initialData;
        try {
            flashcardAppOptional = storage.readFlashcardApp();
            if (!flashcardAppOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample FlashcardApp");
            }
            initialData = flashcardAppOptional.orElseGet(SampleDataUtil::getSampleFlashcardApp);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FlashcardApp");
            initialData = new FlashcardApp();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FlashcardApp");
            initialData = new FlashcardApp();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FlashcardApp");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting FlashcardApp " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping LingoGO! ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
