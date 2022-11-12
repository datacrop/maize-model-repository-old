package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered by Mongo databases pertaining to the persistence of IoT Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public interface VendorServices {

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID) throws IllegalArgumentException, NonUuidArgumentException;

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException if name parameter is null or an empty string.
     *****************************************************************************************************************/
    VendorResponseWrapper retrieveVendorByName(String name) throws IllegalArgumentException;

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
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     *****************************************************************************************************************/
    VendorResponseWrapper createVendor(VendorRequestDto requestDto) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to update an existing Vendor using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Vendor or failure messages.
     *
     * @throws IllegalArgumentException if requestDto parameter is null.
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    VendorResponseWrapper updateVendor(VendorRequestDto requestDto, String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *
     * @throws IllegalArgumentException if databaseID parameter is null or empty string.
     * @throws NonUuidArgumentException if databaseID parameter does not adhere to UUID format.
     *****************************************************************************************************************/
    VendorResponseWrapper deleteVendor(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    VendorResponseWrapper deleteAllVendors();

}
