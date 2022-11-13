package eu.datacrop.maize.model_repository.commons.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**********************************************************************************************************************
 * This class is a data transfer object representing Vendors. Used in HTTP responses.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Builder
public class VendorResponseDto implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the VendorResponseDto class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -4391485086619989430L;

    /******************************************************************************************************************
     * A UUID representing a unique identifier for the Vendor. Mandatory field.
     *****************************************************************************************************************/
    private String id;

    /******************************************************************************************************************
     * A human-readable string representing a unique identifier for the Vendor. Mandatory field.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A human-readable longer elucidation of the Vendor.
     *****************************************************************************************************************/
    private String description;

    /******************************************************************************************************************
     * Timestamp of first persistence regarding the Vendor in the database. Mandatory field.
     *****************************************************************************************************************/
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creationDate;

    /******************************************************************************************************************
     * Timestamp of the latest persistence regarding the Vendor in the database. Mandatory field.
     *****************************************************************************************************************/
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime latestUpdateDate;

    /******************************************************************************************************************
     * Constructor of the VendorResponseDto class.
     *
     * @param id A UUID representing a unique identifier for the Vendor, not null.
     * @param name A human-readable string representing a unique identifier for the Vendor, not null.
     * @param description human-readable longer elucidation of the Vendor, not null.
     * @param creationDate Timestamp of first persistence regarding the Vendor in the database, not null.
     * @param latestUpdateDate Timestamp of the latest persistence regarding the Vendor in the database, not null.
     *****************************************************************************************************************/
    public VendorResponseDto(String id, String name, String description, LocalDateTime creationDate, LocalDateTime latestUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.latestUpdateDate = latestUpdateDate;
    }

    /******************************************************************************************************************
     * Empty constructor of the VendorResponseDto class.
     *****************************************************************************************************************/
    public VendorResponseDto() {
        this("", "", "", null, null);
    }

    /******************************************************************************************************************
     * "Getter" method for "id" attribute.
     *
     * @return The current value of the object's "id" attribute.
     *****************************************************************************************************************/
    public String getId() {
        return id;
    }

    /******************************************************************************************************************
     * "Setter" function for "id" attribute.
     *
     * @param id A value to assign to the object's "id" attribute, not null.
     *****************************************************************************************************************/
    public void setId(String id) {
        this.id = id;
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
     * "Getter" method for "creationDate" attribute.
     *
     * @return The current value of the object's "creationDate" attribute.
     *****************************************************************************************************************/
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /******************************************************************************************************************
     * "Setter" function for "creationDate" attribute.
     *
     * @param creationDate A value to assign to the object's "creationDate" attribute, not null.
     *****************************************************************************************************************/
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /******************************************************************************************************************
     * "Getter" method for "latestUpdateDate" attribute.
     *
     * @return The current value of the object's "latestUpdateDate" attribute.
     *****************************************************************************************************************/
    public LocalDateTime getLatestUpdateDate() {
        return latestUpdateDate;
    }

    /******************************************************************************************************************
     * "Setter" function for "latestUpdateDate" attribute.
     *
     * @param latestUpdateDate A value to assign to the object's "latestUpdateDate" attribute, not null.
     *****************************************************************************************************************/
    public void setLatestUpdateDate(LocalDateTime latestUpdateDate) {
        this.latestUpdateDate = latestUpdateDate;
    }

    /******************************************************************************************************************
     * Method that checks whether two VendorResponseDto objects are equal. Two such objects are considered
     * equal if their ids and names are the same.
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendorResponseDto that = (VendorResponseDto) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the VendorResponseDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /******************************************************************************************************************
     * Transforms a VendorResponseDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", latestUpdateDate=" + latestUpdateDate +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a VendorResponseDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("name", name);
        jo.put("description", description);
        jo.put("creationDate", creationDate);
        jo.put("latestUpdateDate", latestUpdateDate);
        return jo;
    }
}
