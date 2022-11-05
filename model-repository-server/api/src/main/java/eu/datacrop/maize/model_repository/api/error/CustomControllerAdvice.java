package eu.datacrop.maize.model_repository.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**********************************************************************************************************************
 * This controller intercepts generic API exceptions not caught by controllers corresponding to particular entities.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@ControllerAdvice
public class CustomControllerAdvice {

    /******************************************************************************************************************
     * This exception handler intercepts exceptions occurring when an incoming message body has syntax errors, e.g. a
     * JSON objects missing commas.
     *
     * @param ex The HttpMessageNotReadableException that is intercepted.
     * @return A structure representing a detailed error message.
     *****************************************************************************************************************/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessage handleException(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        String[] parts = message.split(";");
        message = parts[0];
        return new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(), message,
                ErrorMessages.HTTP_MESSAGE_NOT_READABLE.name(), null);

    }

    /******************************************************************************************************************
     * This exception handler intercepts exceptions occurring when an incoming message body has problematic data
     * types.
     *
     * @param ex The MethodArgumentTypeMismatchException that is intercepted.
     * @return A structure representing a detailed error message.
     *****************************************************************************************************************/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessage handleException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getMessage();
        String[] parts = message.split(";");
        message = parts[0];
        return new ErrorMessage(400, HttpStatus.BAD_REQUEST.toString(), message,
                ErrorMessages.ERRONEOUS_PARAMETER_TYPE.name() + "-> " + ex.getName(), null);

    }
}
