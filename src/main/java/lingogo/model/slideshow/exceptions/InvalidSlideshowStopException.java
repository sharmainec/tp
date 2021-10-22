package lingogo.model.slideshow.exceptions;

public class InvalidSlideshowStopException extends RuntimeException {
    public InvalidSlideshowStopException() {
        super("Slideshow is not currently active, and cannot be stopped!");
    }
}
