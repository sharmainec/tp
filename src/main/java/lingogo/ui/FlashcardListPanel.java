package lingogo.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lingogo.commons.core.LogsCenter;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.flashcard.Flashcard;

/**
 * Panel containing the list of flashcards.
 */
public class FlashcardListPanel extends UiPart<Region> {
    private static final String FXML = "FlashcardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private StackPane flashcardListPanel;
    @FXML
    private StackPane flashcardHeaderBarPlaceholder;
    @FXML
    private ListView<Flashcard> flashcardListView;

    private SlideshowPanel slideshowPanel;

    /**
     * Creates a {@code FlashcardListPanel} with the given {@code ObservableList}.
     */
    public FlashcardListPanel(ObservableList<Flashcard> flashcardList, ReadOnlySlideshowApp readOnlySlideshowApp) {
        super(FXML);
        flashcardHeaderBarPlaceholder.getChildren().add(new FlashcardHeaderBar().getRoot());
        flashcardListView.setItems(flashcardList);
        flashcardListView.setCellFactory(listView -> new FlashcardListViewCell());
        slideshowPanel = new SlideshowPanel(readOnlySlideshowApp);
        readOnlySlideshowApp.isActiveProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal.booleanValue()) {
                    assert !flashcardListPanel.getChildren().contains(slideshowPanel.getRoot())
                            : "FlashcardListPanel.java: Slideshow already being displayed";
                    flashcardListPanel.getChildren().add(slideshowPanel.getRoot());
                } else {
                    assert flashcardListPanel.getChildren().contains(slideshowPanel.getRoot())
                            : "FlashcardListPanel.java: No slideshow to exit from";
                    flashcardListPanel.getChildren().remove(slideshowPanel.getRoot());
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Flashcard} using a {@code FlashcardPane}.
     */
    class FlashcardListViewCell extends ListCell<Flashcard> {
        @Override
        protected void updateItem(Flashcard flashcard, boolean empty) {
            super.updateItem(flashcard, empty);

            if (empty || flashcard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FlashcardPane(flashcard, getIndex() + 1).getRoot());
            }
        }
    }
}
