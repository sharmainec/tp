package lingogo.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import lingogo.commons.core.LogsCenter;
import lingogo.model.flashcard.Flashcard;

/**
 * A panel that displays flashcards when the user is testing themselves.
 */
public class Slideshow extends UiPart<Region> {
    private static final String FXML = "Slideshow.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private Label currentFlashcardNumber;
    @FXML
    private Label currentForeignPhrase;
    @FXML
    private Label progress;

    private ObservableList<Flashcard> flashcardList;

    /**
     * Creates a {@code Slideshow} component.
     */
    public Slideshow(ObservableList<Flashcard> flashcardList) {
        super(FXML);
        this.flashcardList = flashcardList;
    }

    //TODO: Implement logic for displaying flashcards from the flashcardList one phrase at a time
    public void beginSlideshow() {

    }

    public void endSlideshow() {

    }
}
