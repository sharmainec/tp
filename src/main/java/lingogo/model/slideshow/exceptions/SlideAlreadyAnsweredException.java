package lingogo.model.slideshow.exceptions;

public class SlideAlreadyAnsweredException extends RuntimeException {
    public SlideAlreadyAnsweredException() {
        super("Slide has already been answered!");
    }
}
