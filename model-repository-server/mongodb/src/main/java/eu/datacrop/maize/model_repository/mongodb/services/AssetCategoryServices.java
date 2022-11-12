package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered by Mongo databases pertaining to the persistence of IoT AssetCategories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface AssetCategoryServices {

    /******************************************************************************************************************
     * Method to retrieve an existing AssetCategory using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategory or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID) throws IllegalArgumentException, NonUuidArgumentException;

    /******************************************************************************************************************
     * Method to retrieve an existing AssetCategory using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategory or failure messages.
     *
     * @throws IllegalArgumentException if name parameter is null or an empty string.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to retrieve all AssetCategories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategories or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponsesWrapper retrieveAllAssetCategories(int page, int size);

    /******************************************************************************************************************
     * Method to persist a new AssetCategory.
     *
     * @param requestDto A data transfer object with values for the attributes of the AssetCategory, not null.
     * @return A wrapped data transfer object with either information on the created AssetCategory or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to update an existing AssetCategory using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the AssetCategory, not null.
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the updated AssetCategory or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete an existing AssetCategory using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted AssetCategory or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAssetCategory(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete all existing AssetCategories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAllAssetCategories();

}
