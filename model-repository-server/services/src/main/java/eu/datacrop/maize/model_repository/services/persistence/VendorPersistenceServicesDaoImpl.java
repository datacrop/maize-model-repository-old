package eu.datacrop.maize.model_repository.services.persistence;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.VendorPersistenceLayerDaos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class redirects enquires to the persistence layer pertaining to Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
public class VendorPersistenceServicesDaoImpl implements VendorPersistenceServicesDao {

    @Autowired
    VendorPersistenceLayerDaos persistenceLayer;

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID) {
        log.info("Services layer received request for retrieval of Vendor with ID: '{}'.", databaseID);
        return persistenceLayer.retrieveVendorByDatabaseID(databaseID);
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByName(String name) {
        log.info("Services layer received request for retrieval of Vendor with Name: '{}'.", name);
        return persistenceLayer.retrieveVendorByName(name);
    }

    /******************************************************************************************************************
     * Method to retrieve all Vendors paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Vendors or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponsesWrapper retrieveAllVendors(int page, int size) {
        log.info("Services layer received request for retrieval of all Vendors.");
        return persistenceLayer.retrieveAllVendors(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A wrapped data transfer object with either information on the created Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper createVendor(VendorRequestDto requestDto) {
        log.info("Services layer received request for creation of new Vendor.");
        return persistenceLayer.createVendor(requestDto);
    }

    /******************************************************************************************************************
     * Method to update an existing Vendor using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper updateVendor(VendorRequestDto requestDto, String databaseID) {
        log.info("Services layer received request for update of Vendor with ID: '{}'.", databaseID);
        return persistenceLayer.updateVendor(requestDto, databaseID);
    }

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteVendor(String databaseID) {
        log.info("Services layer received request for deletion of Vendor with ID: '{}'.", databaseID);
        return persistenceLayer.deleteVendor(databaseID);
    }

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteAllVendors() {
        log.info("Services layer received request for deletion of all Vendors.");
        return persistenceLayer.deleteAllVendors();
    }
}
