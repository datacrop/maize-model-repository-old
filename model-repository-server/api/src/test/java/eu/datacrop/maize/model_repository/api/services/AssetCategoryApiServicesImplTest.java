package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.AssetCategoryResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.AssetCategoryPersistenceLayerDaos;
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
@SpringBootTest(classes = AssetCategoryApiServicesImpl.class)
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
class AssetCategoryApiServicesImplTest {

    @Autowired
    AssetCategoryApiServices apiServices;

    @Autowired
    AssetCategoryPersistenceLayerDaos persistenceDao;

    AssetCategoryResponseWrapper assetCategory1;
    AssetCategoryResponseWrapper assetCategory2;

    AssetCategoryRequestDto assetCategory3Request;

    @BeforeEach
    void setUp() {

        // Creating two different dummy AssetCategories using the above (and different names, descriptions, organizations).
        AssetCategoryRequestDto syst = new AssetCategoryRequestDto();
        syst.setName("AssetCategory1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        assetCategory1 = persistenceDao.createAssetCategory(syst);

        syst = new AssetCategoryRequestDto();
        syst.setName("AssetCategory2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        assetCategory2 = persistenceDao.createAssetCategory(syst);
    }

    @AfterEach
    void tearDown() {
        persistenceDao.deleteAllAssetCategories();
    }

    private void resetAssetCategory3() {
        // Creating a dummy location.
        LocationRequestDto location = new LocationRequestDto(37.568180, 22.808661, "");

        // Creating a structure to be used as "Additional Information".
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));
        List<Integer> ints = Arrays.asList(10, 20, 30, 40, 50, 60);
        info.add(ints);

        AssetCategoryRequestDto syst = new AssetCategoryRequestDto();
        syst.setName("AssetCategory3");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        assetCategory3Request = syst;
    }

    @Test
    void retrieveAssetCategoryByDatabaseID() {

        AssetCategoryResponseDto firstAssetCategory = assetCategory1.getResponse();

        // Attempting to retrieve the first AssetCategory.
        ResponseEntity foundByID = apiServices.retrieveAssetCategoryByDatabaseID(firstAssetCategory.getId());
        Assertions.assertNotNull(foundByID, "AssetCategory not retrieved successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByID.getStatusCode(), "AssetCategory has not been retrieved with HTTP Request Code 200:");
        AssetCategoryResponseDto retrievedAssetCategory = (AssetCategoryResponseDto) foundByID.getBody();
        Assertions.assertNotNull(retrievedAssetCategory, "AssetCategory not retrieved successfully by ID (null test 2):");

        // Checking the contents of the retrieved AssetCategory.
        Assertions.assertEquals(firstAssetCategory.getId(), retrievedAssetCategory.getId(), "The retrieved AssetCategory has incorrect identifier:");
        Assertions.assertEquals(firstAssetCategory.getName(), retrievedAssetCategory.getName(), "The retrieved AssetCategory has incorrect name:");
        Assertions.assertEquals(firstAssetCategory.getDescription(), retrievedAssetCategory.getDescription(), "The retrieved AssetCategory has incorrect description:");
        Assertions.assertEquals(
                firstAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved AssetCategory has incorrect creation date:");
        Assertions.assertEquals(
                firstAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved AssetCategory has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByID = apiServices.retrieveAssetCategoryByDatabaseID(null);
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
        foundByID = apiServices.retrieveAssetCategoryByDatabaseID("");
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
        foundByID = apiServices.retrieveAssetCategoryByDatabaseID(" ");
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
        foundByID = apiServices.retrieveAssetCategoryByDatabaseID(RandomStringUtils.randomAlphabetic(10));
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
        foundByID = apiServices.retrieveAssetCategoryByDatabaseID(UUID.randomUUID().toString());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveAssetCategoryByName() {

        AssetCategoryResponseDto firstAssetCategory = assetCategory1.getResponse();

        // Attempting to retrieve the first AssetCategory.
        ResponseEntity foundByName = apiServices.retrieveAssetCategoryByName(firstAssetCategory.getName());
        Assertions.assertNotNull(foundByName, "AssetCategory not retrieved successfully by Name (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByName.getStatusCode(), "AssetCategory has not been retrieved with HTTP Request Code 200:");
        AssetCategoryResponseDto retrievedAssetCategory = (AssetCategoryResponseDto) foundByName.getBody();
        Assertions.assertNotNull(retrievedAssetCategory, "AssetCategory not retrieved successfully by Name (null test 2):");

        // Checking the contents of the retrieved AssetCategory.
        Assertions.assertEquals(firstAssetCategory.getId(), retrievedAssetCategory.getId(), "The retrieved AssetCategory has incorrect identifier:");
        Assertions.assertEquals(firstAssetCategory.getName(), retrievedAssetCategory.getName(), "The retrieved AssetCategory has incorrect name:");
        Assertions.assertEquals(firstAssetCategory.getDescription(), retrievedAssetCategory.getDescription(), "The retrieved AssetCategory has incorrect description:");
        Assertions.assertEquals(
                firstAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved AssetCategory has incorrect creation date:");
        Assertions.assertEquals(
                firstAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved AssetCategory has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByName = apiServices.retrieveAssetCategoryByName(null);
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
        foundByName = apiServices.retrieveAssetCategoryByName("");
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
        foundByName = apiServices.retrieveAssetCategoryByName(" ");
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
        foundByName = apiServices.retrieveAssetCategoryByName(RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_NAME.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_NAME.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveAllAssetCategories() {

        // Attempting to retrieve both AssetCategories (with convenient pagination).
        ResponseEntity foundAll = apiServices.retrieveAllAssetCategories(0, 5);
        Assertions.assertNotNull(foundAll, "AssetCategory list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "AssetCategories have not been retrieved with HTTP Request Code 200:");
        List<Object> assetCategoryList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(assetCategoryList, "AssetCategory list not retrieved successfully (null test 2):");
        Assertions.assertEquals(2, assetCategoryList.size(), "AssetCategory list has improper size.");

        // Attempting to retrieve the first AssetCategory (respecting pagination).
        foundAll = apiServices.retrieveAllAssetCategories(0, 1);
        Assertions.assertNotNull(foundAll, "AssetCategory list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "AssetCategories have not been retrieved with HTTP Request Code 200:");
        assetCategoryList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(assetCategoryList, "AssetCategory list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, assetCategoryList.size(), "AssetCategory list has improper size.");

        // Attempting to retrieve the second AssetCategory (respecting pagination).
        foundAll = apiServices.retrieveAllAssetCategories(1, 1);
        Assertions.assertNotNull(foundAll, "AssetCategory list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "AssetCategories have not been retrieved with HTTP Request Code 200:");
        assetCategoryList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(assetCategoryList, "AssetCategory list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, assetCategoryList.size(), "AssetCategory list has improper size.");

        // Checking "unhappy path": Attempting to retrieve both AssetCategories (with pagination that exceeds the limit).
        foundAll = apiServices.retrieveAllAssetCategories(3, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.EXCEEDED_PAGE_LIMIT.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.EXCEEDED_PAGE_LIMIT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Cleaning the database.
        persistenceDao.deleteAllAssetCategories();

        // Checking "unhappy path": Attempting to retrieve anything expecting to find nothing.
        foundAll = apiServices.retrieveAllAssetCategories(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void createAssetCategory() {

        // Attempting to create a third AssetCategory.
        resetAssetCategory3();
        ResponseEntity created = apiServices.createAssetCategory(assetCategory3Request);
        Assertions.assertNotNull(created, "AssetCategory not created successfully (null test 1):");
        Assertions.assertSame(HttpStatus.CREATED, created.getStatusCode(), "AssetCategory has not been created with HTTP Request Code 201:");
        AssetCategoryResponseDto createdAssetCategory = (AssetCategoryResponseDto) created.getBody();
        Assertions.assertNotNull(createdAssetCategory, "AssetCategory not created successfully (null test 2):");

        // Checking the contents of the created AssetCategory.
        Assertions.assertEquals(assetCategory3Request.getName(), createdAssetCategory.getName(), "The created AssetCategory has incorrect name:");
        Assertions.assertEquals(assetCategory3Request.getDescription(), createdAssetCategory.getDescription(), "The created AssetCategory has incorrect description:");
        Assertions.assertNotNull(createdAssetCategory.getCreationDate(), "The created AssetCategory did not obtain a creation date:");
        Assertions.assertNotNull(createdAssetCategory.getLatestUpdateDate(), "The created AssetCategory did not obtain a latest update date:");

        // Checking "unhappy path": null incoming data transfer object.
        created = apiServices.createAssetCategory(null);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        ErrorMessage errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.getErrorMessage(), errorMessage.getMessage(), "The error message has incorrect message:");
        Assertions.assertEquals(ErrorMessages.MISSING_DATA_INPUT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempting to recreate AssetCategory with the same name.
        resetAssetCategory3();
        created = apiServices.createAssetCategory(assetCategory3Request);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.CONFLICT, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 409:");
        errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(409, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.CONFLICT.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempting to create AssetCategory without name.
        resetAssetCategory3();
        assetCategory3Request.setName(" ");
        created = apiServices.createAssetCategory(assetCategory3Request);
        Assertions.assertNotNull(created, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, created.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) created.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void updateAssetCategory() {

        // Safekeeping original values of the second AssetCategory.
        String originalId = assetCategory2.getResponse().getId();
        String originalName = assetCategory2.getResponse().getName();
        String originalDescription = assetCategory2.getResponse().getDescription();
        LocalDateTime originalCreationDate = assetCategory2.getResponse().getCreationDate();
        LocalDateTime originalUpdateDate = assetCategory2.getResponse().getLatestUpdateDate();

        // Producing a new request.
        AssetCategoryRequestDto request = new AssetCategoryRequestDto();
        request.setName(originalName);
        request.setDescription("DescForAssetCategory2"); // Update

        // Delay of one second.
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Attempting to update the second AssetCategory.
        ResponseEntity updated = apiServices.updateAssetCategory(request, originalId);
        Assertions.assertNotNull(updated, "AssetCategory not updated successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, updated.getStatusCode(), "AssetCategory has not been updated with HTTP Request Code 200:");
        AssetCategoryResponseDto updatedAssetCategory = (AssetCategoryResponseDto) updated.getBody();
        Assertions.assertNotNull(updatedAssetCategory, "AssetCategory not updated successfully (null test 2):");

        // Checking the contents of the updates AssetCategory.
        Assertions.assertEquals(originalId, updatedAssetCategory.getId(), "The UUID of the updated AssetCategory changed:");
        Assertions.assertEquals(originalName, updatedAssetCategory.getName(), "The updatedAssetCategory AssetCategory has incorrect name:");
        Assertions.assertEquals("DescForAssetCategory2", updatedAssetCategory.getDescription(), "The updatedAssetCategory AssetCategory has incorrect description:");
        Assertions.assertEquals(
                originalCreationDate.truncatedTo(ChronoUnit.SECONDS),
                updatedAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The updated AssetCategory has incorrect creation date:");
        Assertions.assertNotEquals(
                originalUpdateDate.truncatedTo(ChronoUnit.SECONDS),
                updatedAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The updated AssetCategory has incorrect latest update date:");

        // Checking "unhappy path": null incoming data transfer object.
        updated = apiServices.updateAssetCategory(null, originalId);
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
        updated = apiServices.updateAssetCategory(request, "");
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
        updated = apiServices.updateAssetCategory(request, " ");
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
        updated = apiServices.updateAssetCategory(request, RandomStringUtils.randomAlphabetic(10));
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
        updated = apiServices.updateAssetCategory(request, UUID.randomUUID().toString());
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": attempt to update with name that belongs to another entity.
        request.setName("AssetCategory1");
        updated = apiServices.updateAssetCategory(request, originalId);
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.CONFLICT, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(409, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.CONFLICT.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.DUPLICATE_ASSET_CATEGORY.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
        request.setName(originalName);

        // Checking "unhappy path": attempting to update AssetCategory without name.
        request.setName(" ");
        updated = apiServices.updateAssetCategory(request, originalId);
        Assertions.assertNotNull(updated, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.BAD_REQUEST, updated.getStatusCode(), "Error message has not been formulated with HTTP Request Code 400:");
        errorMessage = (ErrorMessage) updated.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(400, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.MANDATORY_FIELDS_MISSING.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
        request.setName(originalName);
    }

    @Test
    void deleteAssetCategory() {

        AssetCategoryResponseDto firstAssetCategory = assetCategory1.getResponse();

        // Attempting to delete the first AssetCategory.
        ResponseEntity deletedById = apiServices.deleteAssetCategory(firstAssetCategory.getId());
        Assertions.assertNotNull(deletedById, "AssetCategory not deleted successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, deletedById.getStatusCode(), "AssetCategory has not been deleted with HTTP Request Code 200:");
        AssetCategoryResponseDto deletedAssetCategory = (AssetCategoryResponseDto) deletedById.getBody();
        Assertions.assertNotNull(deletedAssetCategory, "AssetCategory not deleted successfully by ID (null test 2):");

        // Checking the contents of the retrieved AssetCategory.
        Assertions.assertEquals(firstAssetCategory.getId(), deletedAssetCategory.getId(), "The deleted AssetCategory has incorrect identifier:");
        Assertions.assertEquals(firstAssetCategory.getName(), deletedAssetCategory.getName(), "The deleted AssetCategory has incorrect name:");
        Assertions.assertEquals(firstAssetCategory.getDescription(), deletedAssetCategory.getDescription(), "The deleted AssetCategory has incorrect description:");
        Assertions.assertEquals(
                firstAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                deletedAssetCategory.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted AssetCategory has incorrect creation date:");
        Assertions.assertEquals(
                firstAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                deletedAssetCategory.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted AssetCategory has incorrect latest update date:");

        // Attempting to retrieve the first AssetCategory. It must fail.
        AssetCategoryResponseWrapper foundByID = persistenceDao.retrieveAssetCategoryByDatabaseID(firstAssetCategory.getId());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(ResponseCode.NOT_FOUND, foundByID.getCode(), "Error message has not been formulated with HTTP Request Code 404:");

        // Checking "unhappy path": null identifier.
        deletedById = apiServices.deleteAssetCategory(null);
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
        deletedById = apiServices.deleteAssetCategory("");
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
        deletedById = apiServices.deleteAssetCategory(" ");
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
        deletedById = apiServices.deleteAssetCategory(RandomStringUtils.randomAlphabetic(10));
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
        deletedById = apiServices.deleteAssetCategory(UUID.randomUUID().toString());
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void deleteAllAssetCategories() {

        // Attempting to delete both AssetCategories.
        ResponseEntity deleteAll = apiServices.deleteAllAssetCategories();
        Assertions.assertNotNull(deleteAll, "AssetCategories have not been deleted successfully (null test 1):");
        Assertions.assertSame(HttpStatus.NO_CONTENT, deleteAll.getStatusCode(), "AssetCategories have not been deleted with HTTP Request Code 204:");
        String body = (String) deleteAll.getBody();
        Assertions.assertNotNull(body, "AssetCategories have not been deleted successfully (null test 2):");
        Assertions.assertEquals(body, "Successfully deleted all Asset Categories from the persistence layer.", "Deletion success message not received:");

        // Attempting to retrieve anything expecting to find nothing.
        ResponseEntity foundAll = apiServices.retrieveAllAssetCategories(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": Attempting to re-delete expecting to find nothing.
        deleteAll = apiServices.deleteAllAssetCategories();
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deleteAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deleteAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }
}