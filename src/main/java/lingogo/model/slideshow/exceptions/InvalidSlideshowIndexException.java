package lingogo.model.slideshow.exceptions;

public class InvalidSlideshowIndexException extends RuntimeException {
    public InvalidSlideshowIndexException() {
        super("Operation accesses an invalid index in the slideshow");
    }
}
