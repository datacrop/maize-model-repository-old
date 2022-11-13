package eu.datacrop.maize.model_repository.persistence.mongo_implementation;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.services.VendorServices;
import eu.datacrop.maize.model_repository.persistence.daos.VendorPersistenceLayerDaos;
import eu.datacrop.maize.model_repository.persistence.validators.VendorValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class redirects enquires to the persistence layer pertaining to Vendors. Implementation for MongoDB.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class VendorMongoDaos implements VendorPersistenceLayerDaos {

    @Autowired
    VendorServices services;

    @Autowired
    VendorValidator validator;


    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.retrieveVendorByDatabaseID().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of Vendor with ID: '{}'.", databaseID);
        return services.retrieveVendorByDatabaseID(databaseID);
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByName(String name) throws IllegalArgumentException {

        // Checking input parameters.
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.retrieveVendorByName().");
        }

        log.info("Persistence layer (MongoDB) received request for retrieval of Vendor with Name: '{}'.", name);
        return services.retrieveVendorByName(name);
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
        log.info("Persistence layer (MongoDB) received request for retrieval of all Vendors.");
        return services.retrieveAllVendors(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A wrapped data transfer object with either information on the created Vendor or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper createVendor(VendorRequestDto requestDto) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.createVendor().");
        }

        log.info("Persistence layer (MongoDB) received request for creation of new Vendor.");

        // Making the validator specific for Vendors.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        VendorResponseWrapper wrapper = (VendorResponseWrapper) requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.createVendor(requestDto);
    }

    /******************************************************************************************************************
     * Method to update an existing Vendor using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the updated Vendor or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper updateVendor(VendorRequestDto requestDto, String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (requestDto == null || databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.updateVendor().");
        }

        log.info("Persistence layer (MongoDB) received request for update of Vendor with ID: '{}'.", databaseID);

        // Making the validator specific for Vendors.
        requestDto.setValidator(this.validator);

        // Performing validations of data transfer object.
        VendorResponseWrapper wrapper = requestDto.performValidation();
        if (!wrapper.getCode().equals(ResponseCode.SUCCESS)) {
            // Aborting if issues have been discovered.
            log.debug("Issues discovered during attribute validation.");
            return synthesizeResponseWrapperForError(wrapper.getCode(), wrapper.getMessage(), wrapper.getErrorCode());
        }

        // Continuing if issues have not been discovered.
        return services.updateVendor(requestDto, databaseID);
    }

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteVendor(String databaseID) throws IllegalArgumentException {

        // Checking input parameters.
        if (databaseID.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.deleteVendor().");
        }

        log.info("Persistence layer (MongoDB) received request for deletion of Vendor with ID: '{}'.", databaseID);
        return services.deleteVendor(databaseID);
    }

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteAllVendors() {
        log.info("Persistence layer (MongoDB) received request for deletion of all Vendors.");
        return services.deleteAllVendors();
    }

    /******************************************************************************************************************
     * Method that creates an error report.
     *
     * @param code A response code to be translated to an appropriate Http code.
     * @param message The message describing the actual error.
     * @param errorCode A shorthand key for the error.
     * @return A wrapped data transfer object with either a success message or failure messages.
     *
     * @throws IllegalArgumentException - if code is null or empty.
     * @throws IllegalArgumentException - if code is ResponseCode.SUCCESS or ResponseCode.UNDEFINED.
     *****************************************************************************************************************/
    private VendorResponseWrapper synthesizeResponseWrapperForError(ResponseCode code, String message, VendorErrorMessages errorCode) throws IllegalArgumentException {

        if (code == null || code.equals(ResponseCode.SUCCESS) || code.equals(ResponseCode.UNDEFINED) || message.isBlank()) {
            throw new IllegalArgumentException("Invalid parameter detected for method VendorMongoDaos.synthesizeResponseWrapperForError().");
        }

        VendorResponseWrapper wrapper = new VendorResponseWrapper();
        wrapper.setCode(code);
        wrapper.setMessage(message);
        wrapper.setResponse(null);
        wrapper.setErrorCode(errorCode);

        log.debug("Successfully produced ResponseWrapper for unsuccessful database transaction.");

        return wrapper;
    }
}
