package az.office.officenter.exception;

public class BlogPostNotFoundException extends RuntimeException{
    public BlogPostNotFoundException(String message) {
        super(message);
    }
}
