package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.AssetCategoryResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.AssetCategoryErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.AssetCategoryResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.AssetCategoryResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.entities.AssetCategory;
import eu.datacrop.maize.model_repository.mongodb.repositories.AssetCategoryRepository;
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
@SpringBootTest(classes = AssetCategoryServicesImpl.class)
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
class AssetCategoryServicesImplTest {

    @Autowired
    AssetCategoryRepository assetCategoryRepository;

    @Autowired
    AssetCategoryServices assetCategoryServices;

    AssetCategory assetCategory1;
    AssetCategory assetCategory2;

    @BeforeEach
    void setUp() {

        // Creating two different dummy AssetCategories using the above (and different names, descriptions, organizations).
        AssetCategory syst = new AssetCategory();
        syst.setName("AssetCategory1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        assetCategory1 = assetCategoryRepository.save(syst);

        syst = new AssetCategory();
        syst.setName("AssetCategory2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        assetCategory2 = assetCategoryRepository.save(syst);
    }

    @AfterEach
    void tearDown() {
        assetCategoryRepository.delete(assetCategory1);
        assetCategoryRepository.delete(assetCategory2);
    }

    @Test
    void retrieveAssetCategoryByDatabaseID() {

        // Testing the retrieval of the first AssetCategory.
        AssetCategoryResponseWrapper wrapper = assetCategoryServices.retrieveAssetCategoryByDatabaseID(assetCategory1.getId());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        AssetCategoryResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "AssetCategory has not been retrieved successfully (null test):");
        Assertions.assertEquals(assetCategory1.getId(), retrieved.getId(), "The retrieved AssetCategory has incorrect identifier:");
        Assertions.assertEquals(assetCategory1.getName(), retrieved.getName(), "The retrieved AssetCategory has incorrect name:");
        Assertions.assertEquals(assetCategory1.getDescription(), retrieved.getDescription(), "The retrieved AssetCategory has incorrect description:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved AssetCategory did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved AssetCategory did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = assetCategoryServices.retrieveAssetCategoryByDatabaseID(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveAssetCategoryByDatabaseID()."));
    }

    @Test
    void retrieveAssetCategoryByName() {

        // Testing the retrieval of the first AssetCategory.
        AssetCategoryResponseWrapper wrapper = assetCategoryServices.retrieveAssetCategoryByName(assetCategory1.getName());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        AssetCategoryResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "AssetCategory has not been retrieved successfully (null test):");
        Assertions.assertEquals(assetCategory1.getId(), retrieved.getId(), "The retrieved AssetCategory has incorrect identifier:");
        Assertions.assertEquals(assetCategory1.getName(), retrieved.getName(), "The retrieved AssetCategory has incorrect name:");
        Assertions.assertEquals(assetCategory1.getDescription(), retrieved.getDescription(), "The retrieved AssetCategory has incorrect description:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved AssetCategory did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved AssetCategory did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = RandomStringUtils.randomAlphabetic(10);
        wrapper = assetCategoryServices.retrieveAssetCategoryByName(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_NAME.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByName(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByName()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByName(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByName()."));
    }

    @Test
    void retrieveAllAssetCategories() {

        // Testing the retrieval of both AssetCategories.
        AssetCategoryResponsesWrapper wrapper = assetCategoryServices.retrieveAllAssetCategories(0, 5);

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getListOfResponses(), "Wrapper has not received proper SUCCESS ListOfResponses:");
        Assertions.assertNotNull(wrapper.getPaginationInfo(), "Wrapper has not received proper SUCCESS PaginationInfo:");

        List<AssetCategoryResponseDto> retrievedList = wrapper.getListOfResponses();
        Assertions.assertEquals(2, retrievedList.size(), "Wrapper contains erroneous number of retrieved items:");

        PaginationInfo paginationInfo = wrapper.getPaginationInfo();
        Assertions.assertEquals(2, paginationInfo.getTotalItems(), "Pagination info contains invalid number of Total Items.");
        Assertions.assertEquals(1, paginationInfo.getTotalPages(), "Pagination info contains invalid number of Total Pages.");
        Assertions.assertEquals(0, paginationInfo.getCurrentPage(), "Pagination info contains invalid index of Current Page.");

        // Testing the retrieval of one AssetCategory through Pagination.
        wrapper = assetCategoryServices.retrieveAllAssetCategories(0, 1);

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
        assetCategoryRepository.delete(assetCategory1);
        assetCategoryRepository.delete(assetCategory2);
        wrapper = assetCategoryServices.retrieveAllAssetCategories(0, 5);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(AssetCategoryErrorMessages.NO_ASSET_CATEGORIES_FOUND.toString(), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertEquals(0, wrapper.getListOfResponses().size(), "Wrapper has not received proper NOT_FOUND ListOfResponses:");
        Assertions.assertEquals(0, wrapper.getPaginationInfo().getTotalItems(), "Wrapper has not received proper NOT_FOUND PaginationInfo:");
    }

    @Test
    void createAssetCategory() {
        // Preparing a third AssetCategory to be inserted.
        AssetCategoryRequestDto assetCategory3 = new AssetCategoryRequestDto();
        assetCategory3.setName("AssetCategory3");
        assetCategory3.setDescription(RandomStringUtils.randomAlphabetic(10));

        // Testing the insertion.
        Long beforeInsertion = assetCategoryRepository.count();
        AssetCategoryResponseWrapper createdWrapper = assetCategoryServices.createAssetCategory(assetCategory3);
        Long afterInsertion = assetCategoryRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, createdWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", createdWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(createdWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        AssetCategoryResponseDto created = createdWrapper.getResponse();

        Assertions.assertNotNull(created, "AssetCategory has not been created successfully (null test):");
        Assertions.assertNotNull(created.getId(), "The created AssetCategory received no identifier:");
        Assertions.assertEquals(assetCategory3.getName(), created.getName(), "The created AssetCategory has incorrect name:");
        Assertions.assertEquals(assetCategory3.getDescription(), created.getDescription(), "The created AssetCategory has incorrect description:");
        Assertions.assertNotNull(created.getCreationDate(), "The created AssetCategory did not receive a creation timestamp:");
        Assertions.assertNotNull(created.getLatestUpdateDate(), "The created AssetCategory did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeInsertion + 1L), afterInsertion, "The number of entities has not increased by one:");

        // Testing also the "Conflict" scenario by attempting to re-insert the same information.
        beforeInsertion = assetCategoryRepository.count();
        AssetCategoryResponseWrapper createdWrapper2 = assetCategoryServices.createAssetCategory(assetCategory3);
        afterInsertion = assetCategoryRepository.count();

        Assertions.assertEquals(ResponseCode.CONFLICT, createdWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(createdWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeInsertion, afterInsertion, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.createAssetCategory(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method createAssetCategory()."));

        // Cleaning up.
        assetCategoryRepository.deleteById(created.getId());
    }

    @Test
    void updateAssetCategory() {
        // Preparing to update the second AssetCategory.
        AssetCategory retrievedAssetCategory2 = assetCategoryRepository.findFirstByName("AssetCategory2");

        AssetCategoryRequestDto updateRequest = new AssetCategoryRequestDto();
        updateRequest.setName("AssetCategory2"); // Unchanged.
        updateRequest.setDescription("updatedDesc");

        // Testing the update.
        Long beforeUpdate = assetCategoryRepository.count();
        AssetCategoryResponseWrapper updatedWrapper = assetCategoryServices.updateAssetCategory(updateRequest, retrievedAssetCategory2.getId());
        Long afterUpdate = assetCategoryRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, updatedWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", updatedWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(updatedWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        AssetCategoryResponseDto updated = updatedWrapper.getResponse();

        Assertions.assertNotNull(updated, "AssetCategory has not been updated successfully (null test):");
        Assertions.assertNotNull(updated.getId(), "The updated AssetCategory received no identifier:");
        Assertions.assertEquals(retrievedAssetCategory2.getId(), updated.getId(), "The updated AssetCategory has changed identifier.");
        Assertions.assertEquals("AssetCategory2", updated.getName(), "The updated AssetCategory has incorrect name:");
        Assertions.assertEquals("updatedDesc", updated.getDescription(), "The updated AssetCategory has incorrect description:");
        Assertions.assertNotNull(updated.getCreationDate(), "The updated AssetCategory did not receive a creation timestamp:");
        Assertions.assertEquals(retrievedAssetCategory2.getCreationDate(), updated.getCreationDate(), "The updated AssetCategory changed timestamp:");
        Assertions.assertNotNull(updated.getLatestUpdateDate(), "The updated AssetCategory did not receive an update timestamp:");
        Assertions.assertNotEquals(retrievedAssetCategory2.getLatestUpdateDate(), updated.getLatestUpdateDate(), "The updated AssetCategory still bears the same latest update timestamp:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed:");

        // Testing also the "Conflict" scenario by attempting to update the second assetCategory with a name that already exists.
        updateRequest.setName("AssetCategory1");
        AssetCategoryResponseWrapper updatedWrapper2 = assetCategoryServices.updateAssetCategory(updateRequest, retrievedAssetCategory2.getId());

        Assertions.assertEquals(ResponseCode.CONFLICT, updatedWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(updatedWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        AssetCategoryResponseWrapper updatedWrapper3 = assetCategoryServices.updateAssetCategory(updateRequest, random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, updatedWrapper3.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + random + "'."), updatedWrapper3.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(updatedWrapper3.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.updateAssetCategory(updateRequest, " "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateAssetCategory()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.updateAssetCategory(updateRequest, null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateAssetCategory()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> assetCategoryServices.updateAssetCategory(updateRequest, RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method updateAssetCategory()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.updateAssetCategory(null, retrievedAssetCategory2.getId()),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateAssetCategory()."));
    }

    @Test
    void deleteAssetCategory() {
        // Testing the deletion of the first AssetCategory.
        Long beforeDeletion = assetCategoryRepository.count();
        AssetCategoryResponseWrapper wrapper = assetCategoryServices.deleteAssetCategory(assetCategory1.getId());
        Long afterDeletion = assetCategoryRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        AssetCategoryResponseDto deleted = wrapper.getResponse();

        Assertions.assertNotNull(deleted, "AssetCategory has not been deleted successfully (null test):");
        Assertions.assertEquals(assetCategory1.getId(), deleted.getId(), "The deleted AssetCategory has incorrect identifier:");
        Assertions.assertEquals(assetCategory1.getName(), deleted.getName(), "The deleted AssetCategory has incorrect name:");
        Assertions.assertEquals(assetCategory1.getDescription(), deleted.getDescription(), "The deleted AssetCategory has incorrect description:");
        Assertions.assertNotNull(deleted.getCreationDate(), "The deleted AssetCategory did not receive a creation timestamp:");
        Assertions.assertNotNull(deleted.getLatestUpdateDate(), "The deleted AssetCategory did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeDeletion - 1L), afterDeletion, "The number of entities has not decreased by one:");

        // Testing the retrieval of the first AssetCategory. It must return nothing.
        AssetCategory retrieved = assetCategoryRepository.findFirstByName("AssetCategory1");
        Assertions.assertNull(retrieved, "The supposedly deleted AssetCategory can still be retrieved:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = assetCategoryServices.deleteAssetCategory(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(AssetCategoryErrorMessages.ASSET_CATEGORY_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveAssetCategoryByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> assetCategoryServices.retrieveAssetCategoryByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveAssetCategoryByDatabaseID()."));
    }

    @Test
    void deleteAllAssetCategories() {
        // Testing the deletion of both Test AssetCategories.
        Long beforeDeletion = assetCategoryRepository.count();
        AssetCategoryResponseWrapper wrapper = assetCategoryServices.deleteAllAssetCategories();
        Long afterDeletion = assetCategoryRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotEquals(beforeDeletion, afterDeletion, "The number of entities has not changed:");
        Assertions.assertEquals(0, afterDeletion, "The number of entities is not zero:");
    }
}