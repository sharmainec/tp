package lingogo.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import lingogo.commons.core.LogsCenter;

/**
 * A panel that displays flashcards when the user is testing themselves.
 */
public class Slideshow extends UiPart<Region> {
    private static final String FXML = "Slideshow.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    /**
     * Creates a {@code Slideshow} component.
     */
    public Slideshow() {
        super(FXML);

    }
}
