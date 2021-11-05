package lingogo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import lingogo.model.flashcard.Flashcard;

/**
 * A UI component that displays information of a {@code Flashcard}.
 */
public class FlashcardPane extends UiPart<Region> {

    private static final String FXML = "FlashcardPane.fxml";
    private static final int LARGE_PHRASE_PADDING = 30;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Flashcard flashcard;

    @FXML
    private GridPane cardPane;
    @FXML
    private Label id;
    @FXML
    private Label language;
    @FXML
    private Text foreignPhrase;
    @FXML
    private Text englishPhrase;

    /**
     * Creates a {@code FlashcardPane} with the given {@code Flashcard} and index to display.
     */
    public FlashcardPane(Flashcard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        language.setText(flashcard.getLanguageType().value);
        foreignPhrase.setText(flashcard.getForeignPhrase().value);
        englishPhrase.setText(flashcard.getEnglishPhrase().value);

        foreignPhrase.setWrappingWidth(cardPane.getColumnConstraints().get(2).getPrefWidth());
        englishPhrase.setWrappingWidth(cardPane.getColumnConstraints().get(3).getPrefWidth());

        // If the English or foreign phrase or both are very long, the flashcard pane will need more height to display
        // all the text
        double heightNeeded =
                Math.max(foreignPhrase.getLayoutBounds().getHeight(), englishPhrase.getLayoutBounds().getHeight())
                + LARGE_PHRASE_PADDING;
        cardPane.setMinHeight(heightNeeded);
        if (cardPane.getPrefHeight() < heightNeeded) {
            cardPane.setPrefHeight(heightNeeded);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashcardPane)) {
            return false;
        }

        // state check
        FlashcardPane card = (FlashcardPane) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
