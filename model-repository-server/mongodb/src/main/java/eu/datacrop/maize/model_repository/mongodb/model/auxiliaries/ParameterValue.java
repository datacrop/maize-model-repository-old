package eu.datacrop.maize.model_repository.mongodb.model.auxiliaries;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**********************************************************************************************************************
 * This class defines the data model of Parameter Values for persistence in a MongoDB.
 * Note: Parameter Values are auxiliary entities and are not stored in a Document of their own.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public class ParameterValue implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the ParameterValue class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -4642802969293716685L;

    /******************************************************************************************************************
     * A human-readable name for the Parameter Value, not null.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    private Object value;

    /******************************************************************************************************************
     * Constructor of the ParameterValue class.
     *
     * @param name A human-readable name for the Parameter Value, not null.
     * @param value The actual value for the Parameter Value (type undetermined), not null.
     *****************************************************************************************************************/
    public ParameterValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /******************************************************************************************************************
     * Empty constructor of the ParameterValue class.
     *****************************************************************************************************************/
    public ParameterValue() {
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
     * Method that checks whether two ParameterValue objects are equal. Two Parameter Values are
     * considered equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValue that = (ParameterValue) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the ParameterValue object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a ParameterValue object to String.
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
}
