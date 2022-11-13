package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.VendorResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.VendorErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.VendorResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.VendorPersistenceLayerDaos;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VendorApiServicesImpl.class)
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
        "eu.datacrop.maize.model_repository.persistence.daos",
        "eu.datacrop.maize.model_repository.persistence.mongo_implementation",
        "eu.datacrop.maize.model_repository.persistence.mysql_implementation",
        "eu.datacrop.maize.model_repository.persistence.validators",
        "eu.datacrop.maize.model_repository.services.persistence",
        "eu.datacrop.maize.model_repository.api",
        "eu.datacrop.maize.model_repository.api.config",
        "eu.datacrop.maize.model_repository.api.controllers",
        "eu.datacrop.maize.model_repository.api.error",
        "eu.datacrop.maize.model_repository.api.services",
        "org.springframework.data.annotation"
})
@EntityScan(basePackages = {
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary"
})
class VendorApiServicesImplTest {

    @Autowired
    VendorApiServices apiServices;

    @Autowired
    VendorPersistenceLayerDaos persistenceDao;

    VendorResponseWrapper vendor1;
    VendorResponseWrapper vendor2;

    VendorRequestDto vendor3Request;

    @BeforeEach
    void setUp() {

        // Creating two different dummy Vendors using the above (and different names, descriptions, organizations).
        VendorRequestDto syst = new VendorRequestDto();
        syst.setName("Vendor1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        vendor1 = persistenceDao.createVendor(syst);

        syst = new VendorRequestDto();
        syst.setName("Vendor2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        vendor2 = persistenceDao.createVendor(syst);
    }

    @AfterEach
    void tearDown() {
        persistenceDao.deleteAllVendors();
    }

    private void resetVendor3() {
        // Creating a dummy location.
        LocationRequestDto location = new LocationRequestDto(37.568180, 22.808661, "");

        // Creating a structure to be used as "Additional Information".
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));
        List<Integer> ints = Arrays.asList(10, 20, 30, 40, 50, 60);
        info.add(ints);

        VendorRequestDto syst = new VendorRequestDto();
        syst.setName("Vendor3");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        vendor3Request = syst;
    }

    @Test
    void retrieveVendorByDatabaseID() {

        VendorResponseDto firstVendor = vendor1.getResponse();

        // Attempting to retrieve the first Vendor.
        ResponseEntity foundByID = apiServices.retrieveVendorByDatabaseID(firstVendor.getId());
        Assertions.assertNotNull(foundByID, "Vendor not retrieved successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByID.getStatusCode(), "Vendor has not been retrieved with HTTP Request Code 200:");
        VendorResponseDto retrievedVendor = (VendorResponseDto) foundByID.getBody();
        Assertions.assertNotNull(retrievedVendor, "Vendor not retrieved successfully by ID (null test 2):");

        // Checking the contents of the retrieved Vendor.
        Assertions.assertEquals(firstVendor.getId(), retrievedVendor.getId(), "The retrieved Vendor has incorrect identifier:");
        Assertions.assertEquals(firstVendor.getName(), retrievedVendor.getName(), "The retrieved Vendor has incorrect name:");
        Assertions.assertEquals(firstVendor.getDescription(), retrievedVendor.getDescription(), "The retrieved Vendor has incorrect description:");
        Assertions.assertEquals(
                firstVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved Vendor has incorrect creation date:");
        Assertions.assertEquals(
                firstVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved Vendor has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByID = apiServices.retrieveVendorByDatabaseID(null);
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty string identifier.
        foundByID = apiServices.retrieveVendorByDatabaseID("");
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty space string identifier.
        foundByID = apiServices.retrieveVendorByDatabaseID(" ");
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": non-UUID identifier.
        foundByID = apiServices.retrieveVendorByDatabaseID(RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": UUID identifier but non-existent object.
        foundByID = apiServices.retrieveVendorByDatabaseID(UUID.randomUUID().toString());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveVendorByName() {

        VendorResponseDto firstVendor = vendor1.getResponse();

        // Attempting to retrieve the first Vendor.
        ResponseEntity foundByName = apiServices.retrieveVendorByName(firstVendor.getName());
        Assertions.assertNotNull(foundByName, "Vendor not retrieved successfully by Name (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByName.getStatusCode(), "Vendor has not been retrieved with HTTP Request Code 200:");
        VendorResponseDto retrievedVendor = (VendorResponseDto) foundByName.getBody();
        Assertions.assertNotNull(retrievedVendor, "Vendor not retrieved successfully by Name (null test 2):");

        // Checking the contents of the retrieved Vendor.
        Assertions.assertEquals(firstVendor.getId(), retrievedVendor.getId(), "The retrieved Vendor has incorrect identifier:");
        Assertions.assertEquals(firstVendor.getName(), retrievedVendor.getName(), "The retrieved Vendor has incorrect name:");
        Assertions.assertEquals(firstVendor.getDescription(), retrievedVendor.getDescription(), "The retrieved Vendor has incorrect description:");
        Assertions.assertEquals(
                firstVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved Vendor has incorrect creation date:");
        Assertions.assertEquals(
                firstVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved Vendor has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByName = apiServices.retrieveVendorByName(null);
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty string identifier.
        foundByName = apiServices.retrieveVendorByName("");
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty space string identifier.
        foundByName = apiServices.retrieveVendorByName(" ");
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": UUID identifier but non-existent object.
        foundByName = apiServices.retrieveVendorByName(RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.VENDOR_NOT_FOUND_NAME.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_NAME.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveAllVendors() {

        // Attempting to retrieve both Vendors (with convenient pagination).
        ResponseEntity foundAll = apiServices.retrieveAllVendors(0, 5);
        Assertions.assertNotNull(foundAll, "Vendor list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Vendors have not been retrieved with HTTP Request Code 200:");
        List<Object> vendorList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(vendorList, "Vendor list not retrieved successfully (null test 2):");
        Assertions.assertEquals(2, vendorList.size(), "Vendor list has improper size.");

        // Attempting to retrieve the first Vendor (respecting pagination).
        foundAll = apiServices.retrieveAllVendors(0, 1);
        Assertions.assertNotNull(foundAll, "Vendor list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Vendors have not been retrieved with HTTP Request Code 200:");
        vendorList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(vendorList, "Vendor list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, vendorList.size(), "Vendor list has improper size.");

        // Attempting to retrieve the second Vendor (respecting pagination).
        foundAll = apiServices.retrieveAllVendors(1, 1);
        Assertions.assertNotNull(foundAll, "Vendor list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Vendors have not been retrieved with HTTP Request Code 200:");
        vendorList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(vendorList, "Vendor list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, vendorList.size(), "Vendor list has improper size.");

        // Checking "unhappy path": Attempting to retrieve both Vendors (with pagination that exceeds the limit).
        foundAll = apiServices.retrieveAllVendors(3, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.EXCEEDED_PAGE_LIMIT.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.EXCEEDED_PAGE_LIMIT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Cleaning the database.
        persistenceDao.deleteAllVendors();

        // Checking "unhappy path": Attempting to retrieve anything expecting to find nothing.
        foundAll = apiServices.retrieveAllVendors(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.NO_VENDORS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.NO_VENDORS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void createVendor() {

        // Attempting to create a third Vendor.
        resetVendor3();
        ResponseEntity created = apiServices.createVendor(vendor3Request);
        Assertions.assertNotNull(created, "Vendor not created successfully (null test 1):");
        Assertions.assertSame(HttpStatus.CREATED, created.getStatusCode(), "Vendor has not been created with HTTP Request Code 201:");
        VendorResponseDto createdVendor = (VendorResponseDto) created.getBody();
        Assertions.assertNotNull(createdVendor, "Vendor not created successfully (null test 2):");

        // Checking the contents of the created Vendor.
        Assertions.assertEquals(vendor3Request.getName(), createdVendor.getName(), "The created Vendor has incorrect name:");
        Assertions.assertEquals(vendor3Request.getDescription(), createdVendor.getDescription(), "The created Vendor has incorrect description:");
        Assertions.assertNotNull(createdVendor.getCreationDate(), "The created Vendor did not obtain a creation date:");
        Assertions.assertNotNull(createdVendor.getLatestUpdateDate(), "The created Vendor did not obtain a latest update date:");

        // Checking "unhappy path": null incoming data transfer object.
        created = apiServices.createVendor(null);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempting to recreate Vendor with the same name.
        resetVendor3();
        created = apiServices.createVendor(vendor3Request);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.CONFLICT, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 409:");
        errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(409, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.CONFLICT.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.DUPLICATE_VENDOR.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.DUPLICATE_VENDOR.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempting to create Vendor without name.
        resetVendor3();
        vendor3Request.setName(" ");
        created = apiServices.createVendor(vendor3Request);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.MANDATORY_FIELDS_MISSING.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.MANDATORY_FIELDS_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void updateVendor() {

        // Safekeeping original values of the second Vendor.
        String originalId = vendor2.getResponse().getId();
        String originalName = vendor2.getResponse().getName();
        String originalDescription = vendor2.getResponse().getDescription();
        LocalDateTime originalCreationDate = vendor2.getResponse().getCreationDate();
        LocalDateTime originalUpdateDate = vendor2.getResponse().getLatestUpdateDate();

        // Producing a new request.
        VendorRequestDto request = new VendorRequestDto();
        request.setName(originalName);
        request.setDescription("DescForVendor2"); // Update

        // Delay of one second.
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Attempting to update the second Vendor.
        ResponseEntity updated = apiServices.updateVendor(request, originalId);
        Assertions.assertNotNull(updated, "Vendor not updated successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, updated.getStatusCode(), "Vendor has not been updated with HTTP Request Code 200:");
        VendorResponseDto updatedVendor = (VendorResponseDto) updated.getBody();
        Assertions.assertNotNull(updatedVendor, "Vendor not updated successfully (null test 2):");

        // Checking the contents of the updates Vendor.
        Assertions.assertEquals(originalId, updatedVendor.getId(), "The UUID of the updated Vendor changed:");
        Assertions.assertEquals(originalName, updatedVendor.getName(), "The updatedVendor Vendor has incorrect name:");
        Assertions.assertEquals("DescForVendor2", updatedVendor.getDescription(), "The updatedVendor Vendor has incorrect description:");
        Assertions.assertEquals(
                originalCreationDate.truncatedTo(ChronoUnit.SECONDS),
                updatedVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The updated Vendor has incorrect creation date:");
        Assertions.assertNotEquals(
                originalUpdateDate.truncatedTo(ChronoUnit.SECONDS),
                updatedVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The updated Vendor has incorrect latest update date:");

        // Checking "unhappy path": null incoming data transfer object.
        updated = apiServices.updateVendor(null, originalId);
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty string identifier.
        updated = apiServices.updateVendor(request, "");
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty space string identifier.
        updated = apiServices.updateVendor(request, " ");
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": non-UUID identifier.
        updated = apiServices.updateVendor(request, RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": UUID identifier but non-existent object.
        updated = apiServices.updateVendor(request, UUID.randomUUID().toString());
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempt to update with name that belongs to another entity.
        request.setName("Vendor1");
        updated = apiServices.updateVendor(request, originalId);
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.CONFLICT, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(409, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.CONFLICT.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.DUPLICATE_VENDOR.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.DUPLICATE_VENDOR.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
        request.setName(originalName);

        // Checking "unhappy path": attempting to update Vendor without name.
        request.setName(" ");
        updated = apiServices.updateVendor(request, originalId);
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.MANDATORY_FIELDS_MISSING.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.MANDATORY_FIELDS_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
        request.setName(originalName);
    }

    @Test
    void deleteVendor() {

        VendorResponseDto firstVendor = vendor1.getResponse();

        // Attempting to delete the first Vendor.
        ResponseEntity deletedById = apiServices.deleteVendor(firstVendor.getId());
        Assertions.assertNotNull(deletedById, "Vendor not deleted successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, deletedById.getStatusCode(), "Vendor has not been deleted with HTTP Request Code 200:");
        VendorResponseDto deletedVendor = (VendorResponseDto) deletedById.getBody();
        Assertions.assertNotNull(deletedVendor, "Vendor not deleted successfully by ID (null test 2):");

        // Checking the contents of the retrieved Vendor.
        Assertions.assertEquals(firstVendor.getId(), deletedVendor.getId(), "The deleted Vendor has incorrect identifier:");
        Assertions.assertEquals(firstVendor.getName(), deletedVendor.getName(), "The deleted Vendor has incorrect name:");
        Assertions.assertEquals(firstVendor.getDescription(), deletedVendor.getDescription(), "The deleted Vendor has incorrect description:");
        Assertions.assertEquals(
                firstVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                deletedVendor.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted Vendor has incorrect creation date:");
        Assertions.assertEquals(
                firstVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                deletedVendor.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted Vendor has incorrect latest update date:");

        // Attempting to retrieve the first Vendor. It must fail.
        VendorResponseWrapper foundByID = persistenceDao.retrieveVendorByDatabaseID(firstVendor.getId());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(ResponseCode.NOT_FOUND, foundByID.getCode(), "Error message has not been formulated with HTTP Request Code 404:");

        // Checking "unhappy path": null identifier.
        deletedById = apiServices.deleteVendor(null);
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty string identifier.
        deletedById = apiServices.deleteVendor("");
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": empty space string identifier.
        deletedById = apiServices.deleteVendor(" ");
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": non-UUID identifier.
        deletedById = apiServices.deleteVendor(RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.IDENTIFIER_NOT_UUID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": UUID identifier but non-existent object.
        deletedById = apiServices.deleteVendor(UUID.randomUUID().toString());
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.VENDOR_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.VENDOR_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void deleteAllVendors() {

        // Attempting to delete both Vendors.
        ResponseEntity deleteAll = apiServices.deleteAllVendors();
        Assertions.assertNotNull(deleteAll, "Vendors have not been deleted successfully (null test 1):");
        Assertions.assertSame(HttpStatus.NO_CONTENT, deleteAll.getStatusCode(), "Vendors have not been deleted with HTTP Request Code 204:");
        String body = (String) deleteAll.getBody();
        Assertions.assertNotNull(body, "Vendors have not been deleted successfully (null test 2):");
        Assertions.assertEquals(body, "Successfully deleted all Vendors from the persistence layer.", "Deletion success message not received:");

        // Attempting to retrieve anything expecting to find nothing.
        ResponseEntity foundAll = apiServices.retrieveAllVendors(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.NO_VENDORS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.NO_VENDORS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": Attempting to re-delete expecting to find nothing.
        deleteAll = apiServices.deleteAllVendors();
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deleteAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deleteAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(VendorErrorMessages.NO_VENDORS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(VendorErrorMessages.NO_VENDORS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }
}