package eu.datacrop.maize.model_repository.commons.dtos.requests;

import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**********************************************************************************************************************
 * This class is a data transfer object representing IoT Systems. Used in HTTP requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public class SystemRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -9170777359020648979L;

    /******************************************************************************************************************
     * A human readable string representing a unique identifier for the IoT System. Mandatory field.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A textual description of the IoT System. Mandatory field.
     *****************************************************************************************************************/
    private String description;

    /******************************************************************************************************************
     * The virtual or physical Location of the IoT System. Optional field.
     *****************************************************************************************************************/
    private LocationRequestDto location;

    /******************************************************************************************************************
     * A human readable string representing the organization owning the IoT System. Optional field.
     *****************************************************************************************************************/
    private String organization;

    /******************************************************************************************************************
     * A collection of versatile variables hosting additional information on the IoT System. Optional field.
     *****************************************************************************************************************/
    private transient Set<Object> additionalInformation;

    /******************************************************************************************************************
     * Constructor of the SystemRequestDto class, used for the Builder pattern.
     *****************************************************************************************************************/
    public SystemRequestDto(String name, String description, LocationRequestDto location,
                            String organization, Set<Object> additionalInformation) {
        this.name = name;
        this.description = description;
        this.organization = organization;

        if (location == null) {
            this.location = new LocationRequestDto();
        } else {
            this.location = location;
        }
        if (additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
        } else {
            this.additionalInformation = additionalInformation;
        }
    }

    /******************************************************************************************************************
     * Constructor of the SystemRequestDto class, used for instantiation with "new".
     *****************************************************************************************************************/
    public SystemRequestDto(String name, String description, String organization) {
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.location = new LocationRequestDto();
        this.additionalInformation = new HashSet<>();
    }

    /******************************************************************************************************************
     * Empty constructor of the SystemRequestDto class.
     *****************************************************************************************************************/
    public SystemRequestDto() {
        this("", "", "");
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
     * "Setter" function for "name" attribute.
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
     * "Setter" function for "description" attribute.
     *
     * @param description A value to assign to the object's "description" attribute, not null.
     *****************************************************************************************************************/
    public void setDescription(String description) {
        this.description = description;
    }

    /******************************************************************************************************************
     * "Getter" method for "location" attribute.
     *
     * @return The current value of the object's "location" attribute.
     *****************************************************************************************************************/
    public LocationRequestDto getLocation() {
        if (this.location == null) {
            this.location = new LocationRequestDto();
        }
        return this.location;
    }

    /******************************************************************************************************************
     * "Setter" function for "location" attribute.
     *
     * @param location A value to assign to the object's "location" attribute, not null.
     *****************************************************************************************************************/
    public void setLocation(LocationRequestDto location) {
        if (location == null) {
            this.location = new LocationRequestDto();
        } else {
            this.location = location;
        }
    }

    /******************************************************************************************************************
     * "Getter" method for "organization" attribute.
     *
     * @return The current value of the object's "organization" attribute.
     *****************************************************************************************************************/
    public String getOrganization() {
        return organization;
    }

    /******************************************************************************************************************
     * "Setter" function for "organization" attribute.
     *
     * @param organization A value to assign to the object's "organization" attribute, not null.
     *****************************************************************************************************************/
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /******************************************************************************************************************
     * "Getter" method for "additionalInformation" attribute.
     *
     * @return The current value of the object's "additionalInformation" attribute.
     *****************************************************************************************************************/
    public Set<Object> getAdditionalInformation() {
        if (this.additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
        }
        return additionalInformation;
    }

    /******************************************************************************************************************
     * "Setter" function for "additionalInformation" attribute.
     *
     * @param additionalInformation A value to assign to the object's "additionalInformation" attribute, not null.
     *****************************************************************************************************************/
    public void setAdditionalInformation(Set<Object> additionalInformation) {
        if (additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
        } else {
            this.additionalInformation = additionalInformation;
        }
    }

    /******************************************************************************************************************
     * Method for gracefully adding AdditionalInformation objects to the IoT System object. Duplicates are removed.
     *
     * @param additionalInformation An object (free generic Object) with additional information on the IoT System.
     *****************************************************************************************************************/
    public void addAdditionalInformation(Object additionalInformation) {
        if (this.additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
        }
        if (additionalInformation == null) {
            return;
        }
        this.additionalInformation.add(additionalInformation);
    }

    /******************************************************************************************************************
     * Method for gracefully removing AdditionalInformation objects from the IoT System object.
     *
     * @param additionalInformation An object (free generic Object) with additional information on the IoT System.
     *****************************************************************************************************************/
    public void removeAdditionalInformation(Object additionalInformation) {
        if (this.additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
            return;
        }
        this.additionalInformation.remove(additionalInformation);
    }

    /******************************************************************************************************************
     * Method for gracefully removing all AdditionalInformation objects from the IoT System object.
     *****************************************************************************************************************/
    public void removeAllAdditionalInformation() {
        if (this.additionalInformation == null) {
            this.additionalInformation = new HashSet<>();
            return;
        }
        this.additionalInformation.clear();
    }

    /******************************************************************************************************************
     * Method that checks whether two SystemRequestDto objects are equal.
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemRequestDto that = (SystemRequestDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the SystemRequestDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a SystemRequestDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", organization='" + organization + '\'' +
                ", additionalInformation=" + additionalInformation +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a LocationRequestDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("description", description);
        jo.put("location", location.toJSON());
        jo.put("organization", organization);
        jo.put("additionalInformation", additionalInformation); // This field might not work perfectly.
        return jo;
    }
}
