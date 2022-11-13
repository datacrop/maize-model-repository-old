package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import org.springframework.http.ResponseEntity;

/**********************************************************************************************************************
 * This interface defines the services offered by the API layer pertaining to Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface VendorApiServices {

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveVendorByDatabaseID(String vendorID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing Vendor using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveVendorByName(String name);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Vendors paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Vendors or failure messages.
     *****************************************************************************************************************/
    ResponseEntity retrieveAllVendors(int page, int size);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to record information on a new Vendor using.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity createVendor(VendorRequestDto requestDto);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to update information of an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity updateVendor(VendorRequestDto requestDto, String vendorID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing Vendor using its databaseID
     * as unique identifier.
     *
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity deleteVendor(String vendorID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete all existing Vendors.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity deleteAllVendors();
}
