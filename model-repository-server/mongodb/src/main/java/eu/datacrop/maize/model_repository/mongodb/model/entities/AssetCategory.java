package eu.datacrop.maize.model_repository.mongodb.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.datacrop.maize.model_repository.mongodb.listeners.AssetCategoryListener;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**********************************************************************************************************************
 * This class defines the data model of Asset Categories for persistence in a MongoDB.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Builder
@Document(collection = "AssetCategory")
@EntityListeners(AssetCategoryListener.class)
public class AssetCategory implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the AssetCategory class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -1770243006386687651L;

    /******************************************************************************************************************
     * A UUID representing a unique identifier for the Asset Category. Mandatory field.
     *****************************************************************************************************************/
    @Id
    private String id;

    /******************************************************************************************************************
     * A human readable string representing a unique identifier for the Asset Category. Mandatory field.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A textual description of the Asset Category. Mandatory field.
     *****************************************************************************************************************/
    private String description;

    /******************************************************************************************************************
     * Timestamp of first persistence regarding the Asset Category in the database.
     *****************************************************************************************************************/
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creationDate;

    /******************************************************************************************************************
     * Timestamp of latest persistence regarding the Asset Category in the database.
     *****************************************************************************************************************/
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime latestUpdateDate;

    /******************************************************************************************************************
     *  Constructor of the AssetCategory class, used for the Builder pattern.
     *
     * @param id A UUID representing a unique identifier for the Asset Category, not null.
     * @param name A human-readable string representing a unique identifier for the Asset Category, not null.
     * @param description A human-readable longer elucidation of the Asset Category.
     * @param creationDate Timestamp of first persistence regarding the Asset Category in the database, not null.
     * @param latestUpdateDate Timestamp of the latest persistence regarding the Asset Category in the database, not null.
     *****************************************************************************************************************/
    public AssetCategory(String id, String name, String description, LocalDateTime creationDate, LocalDateTime latestUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.latestUpdateDate = latestUpdateDate;
    }

    /******************************************************************************************************************
     * Constructor of the AssetCategory class, used for instantiation with "new".
     *
     * @param name A human-readable string representing a unique identifier for the Asset Category, not null.
     * @param description A human-readable longer elucidation of the Asset Category.
     *****************************************************************************************************************/
    public AssetCategory(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now();
    }

    /******************************************************************************************************************
     * Empty constructor of the AssetCategory class.
     *****************************************************************************************************************/
    public AssetCategory() {
        this("", "");
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
     * Method that checks whether two AssetCategory objects are equal. Two such objects are considered
     * equal if their ids and names are the same.
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetCategory that = (AssetCategory) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the AssetCategory object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /******************************************************************************************************************
     * Transforms an AssetCategory object to String.
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
}
