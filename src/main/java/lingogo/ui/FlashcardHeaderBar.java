package lingogo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Headers for each column of a flashcard in the application.
 */
public class FlashcardHeaderBar extends UiPart<Region> {

    private static final String FXML = "FlashcardHeaderBar.fxml";

    @FXML
    private HBox flashcardHeaderBar;
    @FXML
    private Label id;
    @FXML
    private Label language;
    @FXML
    private Label foreignPhrase;
    @FXML
    private Label englishPhrase;

    /**
     * Creates a {@code FlashcardPane} with the given {@code Flashcard} and index to display.
     */
    public FlashcardHeaderBar() {
        super(FXML);
        id.setText("No.");
        language.setText("Language");
        foreignPhrase.setText("Foreign Phrase");
        englishPhrase.setText("English Phrase");
    }
}
