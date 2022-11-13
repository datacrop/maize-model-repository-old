package eu.datacrop.maize.model_repository.services.persistence;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered that pertain to persistence (CRUD) operations of Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface VendorPersistenceServicesDao {

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID);

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper retrieveVendorByName(String name);

    /******************************************************************************************************************
     * Method to retrieve all Vendors paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Vendors or failure messages.
     *****************************************************************************************************************/
    VendorResponsesWrapper retrieveAllVendors(int page, int size);

    /******************************************************************************************************************
     * Method to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A wrapped data transfer object with either information on the created Vendor or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper createVendor(VendorRequestDto requestDto);

    /******************************************************************************************************************
     * Method to update an existing Vendor using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Vendor or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper updateVendor(VendorRequestDto requestDto, String databaseID);

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper deleteVendor(String databaseID);

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper deleteAllVendors();
}
