package lingogo.model.slideshow;

import static java.util.Objects.requireNonNull;

import lingogo.model.flashcard.Flashcard;

public class Slide {
    private final Flashcard flashcard;
    private boolean isAnswered;

    public Slide(Flashcard flashcard) {
        requireNonNull(flashcard);
        this.flashcard = flashcard;
        isAnswered = false;
    }

    public void answer() {
        isAnswered = true;
    }

    public boolean isAnswered() {
        return isAnswered;
    }
}
