package abn.com.receipes.web.exceptions;

import com.abn.receipe.models.Error;
import com.abn.receipe.models.Errors;

public class ExceptionFormatter extends Errors {

    private Integer code;
    private String message;
    private String aditionalMessage;

    public ExceptionFormatter(Integer code, String message, String aditionalMessage) {
        this.code = code;
        this.message = message;
        this.aditionalMessage = aditionalMessage;
    }

    private Error buildError() {
        Error error = new Error();
        error.setCode(code);
        error.setAditionalMessage(aditionalMessage);
        error.setMessage(message);
        return error;
    }

    public Errors buildErrors() {
        return addErrorsItem(buildError());
    }
}
