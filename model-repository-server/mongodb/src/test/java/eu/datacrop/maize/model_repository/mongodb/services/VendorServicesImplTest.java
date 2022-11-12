package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.VendorResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.VendorResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.entities.Vendor;
import eu.datacrop.maize.model_repository.mongodb.repositories.VendorRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("devmongo")
@SpringBootTest(classes = VendorServicesImpl.class)
@SpringBootApplication
@ComponentScan(basePackages = {
        "eu.datacrop.maize.model_repository.commons.dtos",
        "eu.datacrop.maize.model_repository.commons.dtos.requests",
        "eu.datacrop.maize.model_repository.commons.dtos.requests.templates",
        "eu.datacrop.maize.model_repository.commons.dtos.responses",
        "eu.datacrop.maize.model_repository.commons.enums",
        "eu.datacrop.maize.model_repository.commons.error",
        "eu.datacrop.maize.model_repository.commons.error.exceptions",
        "eu.datacrop.maize.model_repository.commons.error.messages",
        "eu.datacrop.maize.model_repository.commons.util",
        "eu.datacrop.maize.model_repository.commons.validators",
        "eu.datacrop.maize.model_repository.commons.wrappers",
        "eu.datacrop.maize.model_repository.commons.wrappers.collection",
        "eu.datacrop.maize.model_repository.commons.wrappers.single",
        "eu.datacrop.maize.model_repository.mongodb.converters",
        "eu.datacrop.maize.model_repository.mongodb.converters.auxiliaries",
        "eu.datacrop.maize.model_repository.mongodb.daos",
        "eu.datacrop.maize.model_repository.mongodb.listeners",
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliaries",
        "eu.datacrop.maize.model_repository.mongodb.repositories",
        "eu.datacrop.maize.model_repository.mongodb.services",
        "org.springframework.data.annotation"
})
@EntityScan(basePackages = {
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary"
})
//@EnableMongoAuditing
//@EnableMongoRepositories(basePackages = {"eu.datacrop.maize.model_repository.mongodb.repositories"})
class VendorServicesImplTest {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    VendorServices vendorServices;

    Vendor vendor1;
    Vendor vendor2;

    @BeforeEach
    void setUp() {

        // Creating two different dummy Vendors using the above (and different names, descriptions, organizations).
        Vendor syst = new Vendor();
        syst.setName("Vendor1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        vendor1 = vendorRepository.save(syst);

        syst = new Vendor();
        syst.setName("Vendor2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        vendor2 = vendorRepository.save(syst);
    }

    @AfterEach
    void tearDown() {
        vendorRepository.delete(vendor1);
        vendorRepository.delete(vendor2);
    }

    @Test
    void retrieveVendorByDatabaseID() {

        // Testing the retrieval of the first Vendor.
        VendorResponseWrapper wrapper = vendorServices.retrieveVendorByDatabaseID(vendor1.getId());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        VendorResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "Vendor has not been retrieved successfully (null test):");
        Assertions.assertEquals(vendor1.getId(), retrieved.getId(), "The retrieved Vendor has incorrect identifier:");
        Assertions.assertEquals(vendor1.getName(), retrieved.getName(), "The retrieved Vendor has incorrect name:");
        Assertions.assertEquals(vendor1.getDescription(), retrieved.getDescription(), "The retrieved Vendor has incorrect description:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved Vendor did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved Vendor did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = vendorServices.retrieveVendorByDatabaseID(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveVendorByDatabaseID()."));
    }

    @Test
    void retrieveVendorByName() {

        // Testing the retrieval of the first Vendor.
        VendorResponseWrapper wrapper = vendorServices.retrieveVendorByName(vendor1.getName());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        VendorResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "Vendor has not been retrieved successfully (null test):");
        Assertions.assertEquals(vendor1.getId(), retrieved.getId(), "The retrieved Vendor has incorrect identifier:");
        Assertions.assertEquals(vendor1.getName(), retrieved.getName(), "The retrieved Vendor has incorrect name:");
        Assertions.assertEquals(vendor1.getDescription(), retrieved.getDescription(), "The retrieved Vendor has incorrect description:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved Vendor did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved Vendor did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = RandomStringUtils.randomAlphabetic(10);
        wrapper = vendorServices.retrieveVendorByName(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_NAME.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByName(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByName()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByName(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByName()."));
    }

    @Test
    void retrieveAllVendors() {

        // Testing the retrieval of both Vendors.
        VendorResponsesWrapper wrapper = vendorServices.retrieveAllVendors(0, 5);

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getListOfResponses(), "Wrapper has not received proper SUCCESS ListOfResponses:");
        Assertions.assertNotNull(wrapper.getPaginationInfo(), "Wrapper has not received proper SUCCESS PaginationInfo:");

        List<VendorResponseDto> retrievedList = wrapper.getListOfResponses();
        Assertions.assertEquals(2, retrievedList.size(), "Wrapper contains erroneous number of retrieved items:");

        PaginationInfo paginationInfo = wrapper.getPaginationInfo();
        Assertions.assertEquals(2, paginationInfo.getTotalItems(), "Pagination info contains invalid number of Total Items.");
        Assertions.assertEquals(1, paginationInfo.getTotalPages(), "Pagination info contains invalid number of Total Pages.");
        Assertions.assertEquals(0, paginationInfo.getCurrentPage(), "Pagination info contains invalid index of Current Page.");

        // Testing the retrieval of one Vendor through Pagination.
        wrapper = vendorServices.retrieveAllVendors(0, 1);

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getListOfResponses(), "Wrapper has not received proper SUCCESS ListOfResponses:");
        Assertions.assertNotNull(wrapper.getPaginationInfo(), "Wrapper has not received proper SUCCESS PaginationInfo:");

        retrievedList = wrapper.getListOfResponses();
        Assertions.assertEquals(1, retrievedList.size(), "Wrapper contains erroneous number of retrieved items:");

        paginationInfo = wrapper.getPaginationInfo();
        Assertions.assertEquals(2, paginationInfo.getTotalItems(), "Pagination info contains invalid number of Total Items.");
        Assertions.assertEquals(2, paginationInfo.getTotalPages(), "Pagination info contains invalid number of Total Pages.");
        Assertions.assertEquals(0, paginationInfo.getCurrentPage(), "Pagination info contains invalid index of Current Page.");

        // Testing also the "Not Found" scenario.
        vendorRepository.delete(vendor1);
        vendorRepository.delete(vendor2);
        wrapper = vendorServices.retrieveAllVendors(0, 5);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(VendorErrorMessages.NO_VENDORS_FOUND.toString(), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertEquals(0, wrapper.getListOfResponses().size(), "Wrapper has not received proper NOT_FOUND ListOfResponses:");
        Assertions.assertEquals(0, wrapper.getPaginationInfo().getTotalItems(), "Wrapper has not received proper NOT_FOUND PaginationInfo:");
    }

    @Test
    void createVendor() {
        // Preparing a third Vendor to be inserted.
        VendorRequestDto vendor3 = new VendorRequestDto();
        vendor3.setName("Vendor3");
        vendor3.setDescription(RandomStringUtils.randomAlphabetic(10));

        // Testing the insertion.
        Long beforeInsertion = vendorRepository.count();
        VendorResponseWrapper createdWrapper = vendorServices.createVendor(vendor3);
        Long afterInsertion = vendorRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, createdWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", createdWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(createdWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        VendorResponseDto created = createdWrapper.getResponse();

        Assertions.assertNotNull(created, "Vendor has not been created successfully (null test):");
        Assertions.assertNotNull(created.getId(), "The created Vendor received no identifier:");
        Assertions.assertEquals(vendor3.getName(), created.getName(), "The created Vendor has incorrect name:");
        Assertions.assertEquals(vendor3.getDescription(), created.getDescription(), "The created Vendor has incorrect description:");
        Assertions.assertNotNull(created.getCreationDate(), "The created Vendor did not receive a creation timestamp:");
        Assertions.assertNotNull(created.getLatestUpdateDate(), "The created Vendor did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeInsertion + 1L), afterInsertion, "The number of entities has not increased by one:");

        // Testing also the "Conflict" scenario by attempting to re-insert the same information.
        beforeInsertion = vendorRepository.count();
        VendorResponseWrapper createdWrapper2 = vendorServices.createVendor(vendor3);
        afterInsertion = vendorRepository.count();

        Assertions.assertEquals(ResponseCode.CONFLICT, createdWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(createdWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeInsertion, afterInsertion, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.createVendor(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method createVendor()."));

        // Cleaning up.
        vendorRepository.deleteById(created.getId());
    }

    @Test
    void updateVendor() {
        // Preparing to update the second Vendor.
        Vendor retrievedVendor2 = vendorRepository.findFirstByName("Vendor2");

        VendorRequestDto updateRequest = new VendorRequestDto();
        updateRequest.setName("Vendor2"); // Unchanged.
        updateRequest.setDescription("updatedDesc");

        // Testing the update.
        Long beforeUpdate = vendorRepository.count();
        VendorResponseWrapper updatedWrapper = vendorServices.updateVendor(updateRequest, retrievedVendor2.getId());
        Long afterUpdate = vendorRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, updatedWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", updatedWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(updatedWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        VendorResponseDto updated = updatedWrapper.getResponse();

        Assertions.assertNotNull(updated, "Vendor has not been updated successfully (null test):");
        Assertions.assertNotNull(updated.getId(), "The updated Vendor received no identifier:");
        Assertions.assertEquals(retrievedVendor2.getId(), updated.getId(), "The updated Vendor has changed identifier.");
        Assertions.assertEquals("Vendor2", updated.getName(), "The updated Vendor has incorrect name:");
        Assertions.assertEquals("updatedDesc", updated.getDescription(), "The updated Vendor has incorrect description:");
        Assertions.assertNotNull(updated.getCreationDate(), "The updated Vendor did not receive a creation timestamp:");
        Assertions.assertEquals(retrievedVendor2.getCreationDate(), updated.getCreationDate(), "The updated Vendor changed timestamp:");
        Assertions.assertNotNull(updated.getLatestUpdateDate(), "The updated Vendor did not receive an update timestamp:");
        Assertions.assertNotEquals(retrievedVendor2.getLatestUpdateDate(), updated.getLatestUpdateDate(), "The updated Vendor still bears the same latest update timestamp:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed:");

        // Testing also the "Conflict" scenario by attempting to update the second vendor with a name that already exists.
        updateRequest.setName("Vendor1");
        VendorResponseWrapper updatedWrapper2 = vendorServices.updateVendor(updateRequest, retrievedVendor2.getId());

        Assertions.assertEquals(ResponseCode.CONFLICT, updatedWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(updatedWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        VendorResponseWrapper updatedWrapper3 = vendorServices.updateVendor(updateRequest, random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, updatedWrapper3.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + random + "'."), updatedWrapper3.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(updatedWrapper3.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.updateVendor(updateRequest, " "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateVendor()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.updateVendor(updateRequest, null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateVendor()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> vendorServices.updateVendor(updateRequest, RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method updateVendor()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.updateVendor(null, retrievedVendor2.getId()),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateVendor()."));
    }

    @Test
    void deleteVendor() {
        // Testing the deletion of the first Vendor.
        Long beforeDeletion = vendorRepository.count();
        VendorResponseWrapper wrapper = vendorServices.deleteVendor(vendor1.getId());
        Long afterDeletion = vendorRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        VendorResponseDto deleted = wrapper.getResponse();

        Assertions.assertNotNull(deleted, "Vendor has not been deleted successfully (null test):");
        Assertions.assertEquals(vendor1.getId(), deleted.getId(), "The deleted Vendor has incorrect identifier:");
        Assertions.assertEquals(vendor1.getName(), deleted.getName(), "The deleted Vendor has incorrect name:");
        Assertions.assertEquals(vendor1.getDescription(), deleted.getDescription(), "The deleted Vendor has incorrect description:");
        Assertions.assertNotNull(deleted.getCreationDate(), "The deleted Vendor did not receive a creation timestamp:");
        Assertions.assertNotNull(deleted.getLatestUpdateDate(), "The deleted Vendor did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeDeletion - 1L), afterDeletion, "The number of entities has not decreased by one:");

        // Testing the retrieval of the first Vendor. It must return nothing.
        Vendor retrieved = vendorRepository.findFirstByName("Vendor1");
        Assertions.assertNull(retrieved, "The supposedly deleted Vendor can still be retrieved:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = vendorServices.deleteVendor(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveVendorByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> vendorServices.retrieveVendorByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveVendorByDatabaseID()."));
    }

    @Test
    void deleteAllVendors() {
        // Testing the deletion of both Test Vendors.
        Long beforeDeletion = vendorRepository.count();
        VendorResponseWrapper wrapper = vendorServices.deleteAllVendors();
        Long afterDeletion = vendorRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotEquals(beforeDeletion, afterDeletion, "The number of entities has not changed:");
        Assertions.assertEquals(0, afterDeletion, "The number of entities is not zero:");
    }
}