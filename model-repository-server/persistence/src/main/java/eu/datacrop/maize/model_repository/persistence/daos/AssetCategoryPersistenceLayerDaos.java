package eu.datacrop.maize.model_repository.persistence.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered by the persistence layer pertaining to Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface AssetCategoryPersistenceLayerDaos {

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure
     * messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to retrieve all Asset Categories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Asset Categories or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponsesWrapper retrieveAllAssetCategories(int page, int size);

    /******************************************************************************************************************
     * Method to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A wrapped data transfer object with either information on the created Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Asset Category or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAssetCategory(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAllAssetCategories();

}
