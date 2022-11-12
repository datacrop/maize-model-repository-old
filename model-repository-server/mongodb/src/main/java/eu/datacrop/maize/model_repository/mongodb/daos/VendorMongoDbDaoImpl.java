package eu.datacrop.maize.model_repository.mongodb.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.converters.entities.VendorConverters;
import eu.datacrop.maize.model_repository.mongodb.services.VendorServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**********************************************************************************************************************
 * This class implements the entry points (Data Access Objects) to the services offered by Mongo databases
 * pertaining to the persistence of Vendors. It also handles exceptions related to invalid method arguments.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@Service
@Profile("devmongo")
public class VendorMongoDbDaoImpl implements VendorMongoDbDao {

    @Autowired
    VendorServices services;

    @Autowired
    VendorConverters converters;

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByDatabaseID(String databaseID) {

        log.info("MongoDB received request for retrieval of Vendor with ID: '{}'.", databaseID);
        VendorResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveVendorByDatabaseID(databaseID);
        } catch (NonUuidArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing Vendor using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper retrieveVendorByName(String name) {

        log.info("MongoDB received request for retrieval of Vendor with Name: '{}'.", name);
        VendorResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.retrieveVendorByName(name);
        } catch (IllegalArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
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
        log.info("MongoDB received request for retrieval of all Vendors.");
        return services.retrieveAllVendors(page, size);
    }

    /******************************************************************************************************************
     * Method to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A wrapped data transfer object with either information on the created Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper createVendor(VendorRequestDto requestDto) {

        log.info("MongoDB received request for creation of new Vendor.");
        VendorResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.createVendor(requestDto);
        } catch (IllegalArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
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

        log.info("MongoDB received request for update of Vendor with ID: '{}'.", databaseID);
        VendorResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.updateVendor(requestDto, databaseID);
        } catch (NonUuidArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete an existing Vendor using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing Vendor in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted Vendor or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteVendor(String databaseID) {

        log.info("MongoDB received request for deletion of Vendor with ID: '{}'.", databaseID);
        VendorResponseWrapper wrapper;
        String message;
        try {
            wrapper = services.deleteVendor(databaseID);
        } catch (NonUuidArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETER_FORMAT.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETER_FORMAT);
        } catch (IllegalArgumentException e) {
            message = VendorErrorMessages.INVALID_PARAMETERS.toString();
            log.error(message);
            return converters.synthesizeResponseWrapperForError(ResponseCode.ERROR, message, VendorErrorMessages.INVALID_PARAMETERS);
        }
        return wrapper;
    }

    /******************************************************************************************************************
     * Method to delete all existing Vendors.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public VendorResponseWrapper deleteAllVendors() {
        log.info("MongoDB received request for deletion of all Vendors.");
        return services.deleteAllVendors();
    }
}
