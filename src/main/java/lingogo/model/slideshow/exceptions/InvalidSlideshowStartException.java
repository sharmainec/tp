package lingogo.model.slideshow.exceptions;

public class InvalidSlideshowStartException extends RuntimeException {
    public InvalidSlideshowStartException() {
        super("Slideshow is already started!");
    }
}
