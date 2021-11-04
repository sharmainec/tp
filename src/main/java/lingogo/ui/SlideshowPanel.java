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
    private static final String CURRENT_FLASHCARD_NUMBER_FORMAT_STRING = "Current flashcard: %s";
    private static final String ANSWER_HEADER_STRING = "Answer:";
    private static final String PROGRESS_FORMAT_STRING = "Flashcards answered: %s";

    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private Label currentFlashcardNumber;
    @FXML
    private Label currentForeignPhrase;
    @FXML
    private Label answerHeader;
    @FXML
    private Label answer;
    @FXML
    private Label progress;

    /**
     * Creates a {@code Slideshow} component.
     */
    public SlideshowPanel(ReadOnlySlideshowApp readOnlySlideshowApp) {
        super(FXML);
        readOnlySlideshowApp.currentSlideProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Flashcard> o, Flashcard oldVal, Flashcard newVal) {
                currentFlashcardNumber.setText(String.format(CURRENT_FLASHCARD_NUMBER_FORMAT_STRING,
                        readOnlySlideshowApp.getCurrentSlideNumber()));
                currentForeignPhrase.setText(newVal.getForeignPhrase().toString());
                if (readOnlySlideshowApp.isCurrentSlideAnswered()) {
                    answerHeader.setText(ANSWER_HEADER_STRING);
                    answer.setText(newVal.getEnglishPhrase().toString());
                } else {
                    answerHeader.setText("");
                    answer.setText("");
                }
                progress.setText(String.format(PROGRESS_FORMAT_STRING, readOnlySlideshowApp.getProgress()));
            }
        });
        readOnlySlideshowApp.isAnswerDisplayedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    answerHeader.setText(ANSWER_HEADER_STRING);
                    answer.setText(readOnlySlideshowApp.getCurrentSlide().getEnglishPhrase().toString());
                    progress.setText(String.format(PROGRESS_FORMAT_STRING, readOnlySlideshowApp.getProgress()));
                } else {
                    answerHeader.setText("");
                    answer.setText("");
                }
            }
        });
    }
}
