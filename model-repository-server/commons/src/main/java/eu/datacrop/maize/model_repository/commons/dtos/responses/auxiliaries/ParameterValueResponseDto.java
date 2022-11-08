package eu.datacrop.maize.model_repository.commons.dtos.responses.auxiliaries;

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
public class ParameterValueResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4412043931038189371L;

    /******************************************************************************************************************
     * A human-readable name for the Parameter Value, not null.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    private Object value;

    /******************************************************************************************************************
     * Constructor of the ParameterValueResponseDto class.
     *
     * @param name A human-readable name for the Parameter Value, not null.
     * @param value The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    public ParameterValueResponseDto(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /******************************************************************************************************************
     * Empty constructor of the ParameterValueResponseDto class.
     *****************************************************************************************************************/
    public ParameterValueResponseDto() {
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
     * Method that checks whether two ParameterValueResponseDto objects are equal. Two Parameter Values are
     * considered equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValueResponseDto that = (ParameterValueResponseDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the ParameterValueResponseDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a ParameterValueResponseDto object to String.
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
     * Transforms a ParameterValueResponseDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("value", value);
        return jo;
    }
}
