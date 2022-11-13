package eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries;

import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.ParameterErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.ResponseWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.auxiliaries.ParameterResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**********************************************************************************************************************
 * This class is a data transfer object representing Parameters. Used in HTTP requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
public class ParameterRequestDto extends RequestDto implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the ParameterRequestDto class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = 7498412026167936543L;

    /******************************************************************************************************************
     * A human-readable identifier for the Parameter, not null.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A textual elucidation of the value of the Parameter, not null.
     *****************************************************************************************************************/
    private String description;

    /******************************************************************************************************************
     * A classification of data; i.e. the data type of the value of the Parameter, not null.
     *****************************************************************************************************************/
    private String type;

    /******************************************************************************************************************
     * A predefined default value for the Parameter.
     *****************************************************************************************************************/
    private Object defaultValue;

    /******************************************************************************************************************
     * Constructor of the ParameterRequestDto class.
     *
     * @param name A human-readable identifier for the Parameter, not null.
     * @param description A textual elucidation of the value of the Parameter, not null.
     * @param type A classification of data; i.e. the data type of the value of the Parameter, not null.
     * @param defaultValue A predefined default value for the Parameter.
     *****************************************************************************************************************/
    public ParameterRequestDto(String name, String description, String type, Object defaultValue) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    /******************************************************************************************************************
     * Empty constructor of the ParameterRequestDto class.
     *****************************************************************************************************************/
    public ParameterRequestDto() {
        this("", "", "", null);
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
     * "Getter" method for "description" attribute.
     *
     * @return The current value of the object's "description" attribute.
     *****************************************************************************************************************/
    public String getDescription() {
        return description;
    }

    /******************************************************************************************************************
     * "Setter" method for "description" attribute.
     *
     * @param description A value to assign to the object's "description" attribute, not null.
     *****************************************************************************************************************/
    public void setDescription(String description) {
        this.description = description;
    }

    /******************************************************************************************************************
     * "Getter" method for "type" attribute.
     *
     * @return The current value of the object's "type" attribute.
     *****************************************************************************************************************/
    public String getType() {
        return type;
    }

    /******************************************************************************************************************
     * "Setter" method for "type" attribute.
     *
     * @param type A value to assign to the object's "type" attribute, not null.
     *****************************************************************************************************************/
    public void setType(String type) {
        this.type = type;
    }

    /******************************************************************************************************************
     * "Getter" method for "defaultValue" attribute.
     *
     * @return The current value of the object's "defaultValue" attribute.
     *****************************************************************************************************************/
    public Object getDefaultValue() {
        return defaultValue;
    }

    /******************************************************************************************************************
     * "Setter" method for "defaultValue" attribute.
     *
     * @param defaultValue A value to assign to the object's "defaultValue" attribute, not null.
     *****************************************************************************************************************/
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /******************************************************************************************************************
     * Method that checks whether two ParameterRequestDto objects are equal. Two Parameters are considered
     * equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterRequestDto that = (ParameterRequestDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the ParameterRequestDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a ParameterRequestDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", defaultValue=" + defaultValue +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a ParameterRequestDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("description", description);
        jo.put("type", type);
        jo.put("defaultValue", defaultValue);
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
        ParameterResponseWrapper wrapper;
        try {
            // Validating attributes.
            wrapper = (ParameterResponseWrapper) super.getValidator().validateAttributes(this);

            // If we already have an error there is no point in checking further.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Validating relationships (if applicable).
            wrapper = (ParameterResponseWrapper) super.getValidator().validateRelationships(this);

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
            return new ParameterResponseWrapper(ResponseCode.ERROR, message, null, ParameterErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }
}
