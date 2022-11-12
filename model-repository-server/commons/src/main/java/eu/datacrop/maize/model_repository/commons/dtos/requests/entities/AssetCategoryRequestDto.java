package eu.datacrop.maize.model_repository.commons.dtos.requests.entities;

import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**********************************************************************************************************************
 * This class is a data transfer object representing Asset Categories. Used in HTTP requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Builder
public class AssetCategoryRequestDto extends RequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4603728822126069994L;

    /******************************************************************************************************************
     * A human-readable string representing a unique identifier for the Asset Category. Mandatory field.
     *****************************************************************************************************************/
    private String name;

    /******************************************************************************************************************
     * A human-readable longer elucidation of the Asset Category.
     *****************************************************************************************************************/
    private String description;

    /******************************************************************************************************************
     * Constructor of the AssetCategoryRequestDto class.
     *
     * @param name A human-readable string representing a unique identifier for the Asset Category. Mandatory field.
     * @param description A human-readable longer elucidation of the Asset Category.
     *****************************************************************************************************************/
    public AssetCategoryRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /******************************************************************************************************************
     * Empty constructor of the AssetCategoryRequestDto class.
     *****************************************************************************************************************/
    public AssetCategoryRequestDto() {
        this("", "");
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
     * Method that checks whether two AssetCategoryRequestDto objects are equal. Two such objects are considered
     * equal if their names are the same.
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetCategoryRequestDto that = (AssetCategoryRequestDto) o;
        return name.equals(that.name);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the AssetCategoryRequestDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /******************************************************************************************************************
     * Transforms a AssetCategoryRequestDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a AssetCategoryRequestDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("description", description);
        return jo;
    }

    /******************************************************************************************************************
     * This method triggers validation of the data transfer object's attributes and external relationships.
     *
     * @return An AssetCategoryResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *****************************************************************************************************************/
    @Override
    public AssetCategoryResponseWrapper performValidation() {
        AssetCategoryResponseWrapper wrapper;
        try {
            // Validating attributes.
            wrapper = (AssetCategoryResponseWrapper) super.getValidator().validateAttributes(this);

            // If we already have an error there is no point in checking further.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Validating relationships (if applicable).
            wrapper = (AssetCategoryResponseWrapper) super.getValidator().validateRelationships(this);

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
            return new AssetCategoryResponseWrapper(ResponseCode.ERROR, message, null,
                    AssetCategoryErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }
}
