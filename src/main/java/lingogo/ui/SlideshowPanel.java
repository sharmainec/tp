package lingogo.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import lingogo.commons.core.LogsCenter;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.flashcard.Flashcard;

/**
 * A panel that displays flashcards when the user is testing themselves.
 */
public class SlideshowPanel extends UiPart<Region> {
    private static final String FXML = "SlideshowPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private Label currentFlashcardNumber;
    @FXML
    private Label currentForeignPhrase;
    @FXML
    private Label answer;
    @FXML
    private Label progress;

    /**
     * Creates a {@code Slideshow} component.
     */
    public SlideshowPanel(ReadOnlySlideshowApp readOnlySlideshowApp) {
        super(FXML);
        readOnlySlideshowApp.getCurrentSlideProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Flashcard> o, Flashcard oldVal, Flashcard newVal) {
                currentForeignPhrase.setText(newVal.getForeignPhrase().toString());
            }
        });
        readOnlySlideshowApp.isAnswerDisplayedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal.booleanValue()) {
                    answer.setText(readOnlySlideshowApp.getCurrentSlide().getEnglishPhrase().toString());
                } else {
                    answer.setText("");
                }
            }
        });
        // TODO: Add getters for current flashcard number and current progress
    }
}
