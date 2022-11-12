package eu.datacrop.maize.model_repository.mongodb.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the entry points (Data Access Objects) to the services offered by Mongo databases
 * pertaining to the persistence of Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface AssetCategoryMongoDbDao {

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByDatabaseID(String databaseID);

    /******************************************************************************************************************
     * Method to retrieve an existing Asset Category using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Asset Category or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper retrieveAssetCategoryByName(String name);

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
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper createAssetCategory(AssetCategoryRequestDto requestDto);

    /******************************************************************************************************************
     * Method to update an existing Asset Category using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Asset Category or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper updateAssetCategory(AssetCategoryRequestDto requestDto, String databaseID);

    /******************************************************************************************************************
     * Method to delete an existing Asset Category using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Asset Category or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAssetCategory(String databaseID);

    /******************************************************************************************************************
     * Method to delete all existing Asset Categories.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    AssetCategoryResponseWrapper deleteAllAssetCategories();

}
