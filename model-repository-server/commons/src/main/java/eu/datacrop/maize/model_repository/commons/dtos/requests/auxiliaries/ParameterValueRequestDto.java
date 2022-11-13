package eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.ParameterValueErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries.ParameterValueResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**********************************************************************************************************************
 * This class is a data transfer object representing Parameter Values, in essence a name/value pair. Used
 * in HTTP requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
public class ParameterValueRequestDto extends RequestDto implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the ParameterValueRequestDto class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -6762157091680105635L;

    /******************************************************************************************************************
     * A human-readable name for the Parameter Value, not null.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    private Object value;

    /******************************************************************************************************************
     * Constructor of the ParameterValueRequestDto class.
     *
     * @param name A human-readable name for the Parameter Value, not null.
     * @param value The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    public ParameterValueRequestDto(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /******************************************************************************************************************
     * Empty constructor of the ParameterValueRequestDto class.
     *****************************************************************************************************************/
    public ParameterValueRequestDto() {
        this("", null);
    }

    /******************************************************************************************************************
     * "Getter" method for "name" attribute.
     *
     * @return The current value of the object's "name" attribute.
     *****************************************************************************************************************/
    public String getName() {
        return name;
    }

    /******************************************************************************************************************
     * "Setter" method for "name" attribute.
     *
     * @param name A value to assign to the object's "name" attribute, not null.
     *****************************************************************************************************************/
    public void setName(String name) {
        this.name = name;
    }

    /******************************************************************************************************************
     * "Getter" method for "value" attribute.
     *
     * @return The current value of the object's "value" attribute.
     *****************************************************************************************************************/
    public Object getValue() {
        return value;
    }

    /******************************************************************************************************************
     * "Setter" method for "value" attribute.
     *
     * @param value A value to assign to the object's "value" attribute, not null.
     *****************************************************************************************************************/
    public void setValue(Object value) {
        this.value = value;
    }

    /******************************************************************************************************************
     * Method that checks whether two ParameterValueRequestDto objects are equal. Two Parameter Values are
     * considered equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValueRequestDto that = (ParameterValueRequestDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the ParameterValueRequestDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a ParameterValueRequestDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a ParameterValueRequestDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("value", value);
        return jo;
    }

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes and external relationships.
     * Note: this mechanism exists following the "Strategy Design Pattern".
     *
     * @return An abstract ResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *****************************************************************************************************************/
    @Override
    public ResponseWrapper performValidation() {
        ParameterValueResponseWrapper wrapper;
        try {
            // Validating attributes.
            wrapper = (ParameterValueResponseWrapper) super.getValidator().validateAttributes(this);

            // If we already have an error there is no point in checking further.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Validating relationships (if applicable).
            wrapper = (ParameterValueResponseWrapper) super.getValidator().validateRelationships(this);

            // If an error has been discovered report it and return.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Reporting that the validation discovered no issues.
            log.debug("Validation of the Request DTO has no issues to report.");
            return wrapper;
        } catch (IllegalArgumentException e) {
            String message = "Error occurred during Request DTO validation.";
            log.error(message);
            return new ParameterValueResponseWrapper(ResponseCode.ERROR, message, null, ParameterValueErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }
}
