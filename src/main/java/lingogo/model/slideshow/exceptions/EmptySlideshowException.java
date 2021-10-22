package lingogo.model.slideshow.exceptions;

public class EmptySlideshowException extends RuntimeException {
    public EmptySlideshowException() {
        super("An empty slideshow cannot be started");
    }
}
