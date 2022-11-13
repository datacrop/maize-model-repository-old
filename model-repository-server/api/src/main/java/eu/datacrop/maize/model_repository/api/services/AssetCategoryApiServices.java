package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import org.springframework.http.ResponseEntity;

/**********************************************************************************************************************
 * This interface defines the services offered by the API layer pertaining to Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface AssetCategoryApiServices {

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Asset Category using its databaseID
     * as unique identifier.
     *
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveAssetCategoryByDatabaseID(String assetCategoryID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Asset Category using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing AssetCategory in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveAssetCategoryByName(String name);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Asset Categories paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Asset Categories or
     * failure messages.
     *****************************************************************************************************************/
    ResponseEntity retrieveAllAssetCategories(int page, int size);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to record information on a new Asset Category using.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity createAssetCategory(AssetCategoryRequestDto requestDto);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to update information of an existing Asset Category using
     * its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity updateAssetCategory(AssetCategoryRequestDto requestDto, String assetCategoryID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing Asset Category using its databaseID
     * as unique identifier.
     *
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity deleteAssetCategory(String assetCategoryID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete all existing Asset Categories.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity deleteAllAssetCategories();
}
