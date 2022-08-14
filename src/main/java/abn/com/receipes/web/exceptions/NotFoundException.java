package abn.com.receipes.web.exceptions;

import com.abn.receipe.models.Errors;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionID = 1L;

    private Errors errors;

    public NotFoundException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
