package eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries;

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
public class ParameterResponseDto implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the ParameterResponseDto class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -4618573116996215641L;

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
     * Constructor of the ParameterResponseDto class.
     *
     * @param name A human-readable identifier for the Parameter, not null.
     * @param description A textual elucidation of the value of the Parameter, not null.
     * @param type A classification of data; i.e. the data type of the value of the Parameter, not null.
     * @param defaultValue A predefined default value for the Parameter.
     *****************************************************************************************************************/
    public ParameterResponseDto(String name, String description, String type, Object defaultValue) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    /******************************************************************************************************************
     * Empty constructor of the ParameterResponseDto class.
     *****************************************************************************************************************/
    public ParameterResponseDto() {
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
     * Method that checks whether two ParameterResponseDto objects are equal. Two Parameters are considered
     * equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterResponseDto that = (ParameterResponseDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the ParameterResponseDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a ParameterResponseDto object to String.
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
     * Transforms a ParameterResponseDto object to JSONObject.
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
}