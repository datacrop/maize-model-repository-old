package eu.datacrop.maize.model_repository.services.persistence;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered that pertain to persistence (CRUD) operations of AssetCategories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface AssetCategoryPersistenceServicesDao {

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategory or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID);

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategory or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name);

    /******************************************************************************************************************
     * Method to retrieve all Asset Categories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved AssetCategories or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponsesWrapper retrieveAllAssetCategories(int page, int size);

    /******************************************************************************************************************
     * Method to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the AssetCategory, not null.
     * @return A wrapped data transfer object with either information on the created AssetCategory or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto);

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the AssetCategory, not null.
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the updated AssetCategory or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID);

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted AssetCategory or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAssetCategory(String databaseID);

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAllAssetCategories();
}
