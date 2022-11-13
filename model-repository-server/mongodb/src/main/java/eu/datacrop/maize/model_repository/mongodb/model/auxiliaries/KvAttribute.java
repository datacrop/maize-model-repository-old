package eu.datacrop.maize.model_repository.mongodb.model.auxiliaries;

import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**********************************************************************************************************************
 * This class defines the data model of KvAttributes (in essence additional parameters describing Assets)  for
 * persistence in a MongoDB.
 * Note: Parameters are auxiliary entities and are not stored in a Document of their own.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public class KvAttribute implements Serializable {

    @Serial
    private static final long serialVersionUID = -2525357868604982016L;

    /******************************************************************************************************************
     * A human-readable name for the KvAttribute group.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A collection of Parameter Values.
     *****************************************************************************************************************/
    private Set<ParameterValue> parameterValues;

    /******************************************************************************************************************
     * Constructor of the KvAttribute class.
     *
     * @param name A human-readable name for the Parameter Value, not null.
     * @returns A new KvAttribute object.
     *****************************************************************************************************************/
    public KvAttribute(String name) {
        this.name = name;
        this.parameterValues = new HashSet<>();
    }

    /******************************************************************************************************************
     * Empty constructor of the KvAttribute class.
     *
     * @returns A new KvAttribute object.
     *****************************************************************************************************************/
    public KvAttribute() {
        this("");
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
     * "Getter" method for "parameterValues" attribute.
     *
     * @return The current value of the object's "parameterValues" attribute.
     *****************************************************************************************************************/
    public Set<ParameterValue> getParameterValues() {

        if (parameterValues == null) {
            this.parameterValues = new HashSet<>();
        }

        return parameterValues;
    }

    /******************************************************************************************************************
     * "Setter" method for "parameterValues" attribute.
     *
     * @param parameterValues A value to assign to the object's "parameterValues" attribute, not null.
     *****************************************************************************************************************/
    public void setParameterValues(Set<ParameterValue> parameterValues) {

        if (parameterValues == null) {
            this.parameterValues = new HashSet<>();
            return;
        }

        this.parameterValues = parameterValues;
    }

    /******************************************************************************************************************
     * Method to gracefully add a new Parameter Value object to the "parameterValues" collection.
     *
     * @param parameterValue The Parameter Value object to add to the "parameterValues" collection, not null.
     *****************************************************************************************************************/
    public void addParameterValue(ParameterValue parameterValue) {
        if (this.parameterValues == null) {
            this.parameterValues = new HashSet<>();
        }

        parameterValues.add(parameterValue);
    }

    /******************************************************************************************************************
     * Method to gracefully add a new Parameter Value object to the "parameterValues" collection.
     *
     * @param name A name for the Parameter Value object to add to the "parameterValues" collection, not null.
     * @param value A value for the Parameter Value object to add to the "parameterValues" collection, not null.
     *****************************************************************************************************************/
    public void addParameterValue(String name, Object value) {
        if (this.parameterValues == null) {
            this.parameterValues = new HashSet<>();
        }

        ParameterValue parameterValue = new ParameterValue(name, value);

        parameterValues.add(parameterValue);
    }

    /******************************************************************************************************************
     * Method to gracefully delete a Parameter Value object from the "parameterValues" collection.
     *
     * @param parameterValue The Parameter Value object to delete from the "parameterValues" collection, not null.
     *****************************************************************************************************************/
    public void removeParameterValue(ParameterValue parameterValue) {
        if (this.parameterValues == null) {
            this.parameterValues = new HashSet<>();
            return;
        }

        parameterValues.remove(parameterValue);
    }

    /******************************************************************************************************************
     * Method to gracefully delete all Parameter Value objects from the "parameterValues" collection.
     *****************************************************************************************************************/
    public void removeAllParameterValues() {
        if (this.parameterValues == null) {
            this.parameterValues = new HashSet<>();
            return;
        }

        this.parameterValues.clear();
    }

    /******************************************************************************************************************
     * Method to return the value attribute of a Parameter Value in the set by using its name as key. Returns null
     * if the key has not been located.
     *
     * @param key A string to act as a search key, not null.
     * @return The value of the parameter represented with the key. Null if nothing has been found.
     *
     * @throws IllegalArgumentException - if the key provided as parameter is null or empty.
     *****************************************************************************************************************/
    public Object getParameterActualValueByKey(String key) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("Invalid parameter detected for method " +
                    "KvAttribute.getParameterActualValueByKey().");
        }

        if (this.parameterValues == null) {
            this.parameterValues = new HashSet<>();
            return null;
        }

        Object result = null;
        key = key.trim();

        for (ParameterValue parameterValue : this.parameterValues) {
            String paramKey = parameterValue.getName();

            if (paramKey == null || paramKey.equals("")) continue;

            paramKey = paramKey.trim();

            if (!key.equals(paramKey)) continue;

            result = parameterValue.getValue();

            break;
        }

        return result;
    }

    /******************************************************************************************************************
     * Method that checks whether two KvAttribute objects are equal. Two KvAttributes are considered
     * equal if they have the same "name".
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KvAttribute that = (KvAttribute) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the KvAttribute object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a KvAttribute object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", parameterValues=" + parameterValues +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a KvAttribute object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("parameterValues", parameterValues);
        return jo;
    }
}