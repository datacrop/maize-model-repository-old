package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.auxiliaries.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.exceptions.NonUuidArgumentException;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;
import eu.datacrop.maize.model_repository.commons.wrappers.PaginationInfo;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliaries.Location;
import eu.datacrop.maize.model_repository.mongodb.model.entities.System;
import eu.datacrop.maize.model_repository.mongodb.repositories.SystemRepository;
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
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("devmongo")
@SpringBootTest(classes = SystemServicesImpl.class)
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
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"eu.datacrop.maize.model_repository.mongodb.repositories"})
class SystemServicesImplTest {

    @Autowired
    SystemRepository systemRepository;

    @Autowired
    SystemServices systemServices;

    System system1;
    System system2;

    @BeforeEach
    void setUp() {
        // Creating a dummy location.
        Location location = new Location(37.568180, 22.808661, "");

        // Creating a structure to be used as "Additional Information".
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));
        List<Integer> ints = Arrays.asList(10, 20, 30, 40, 50, 60);
        info.add(ints);

        // Creating two different dummy Systems using the above (and different names, descriptions, organizations).
        System syst = new System();
        syst.setName("System1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        syst.setLocation(location);
        syst.setOrganization(RandomStringUtils.randomAlphabetic(10));
        syst.setAdditionalInformation(info);
        system1 = systemRepository.save(syst);

        syst = new System();
        syst.setName("System2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        syst.setLocation(location);
        syst.setOrganization(RandomStringUtils.randomAlphabetic(10));
        syst.setAdditionalInformation(info);
        system2 = systemRepository.save(syst);
    }

    @AfterEach
    void tearDown() {
        systemRepository.delete(system1);
        systemRepository.delete(system2);
    }

    @Test
    void retrieveSystemByDatabaseID() {

        // Testing the retrieval of the first System.
        SystemResponseWrapper wrapper = systemServices.retrieveSystemByDatabaseID(system1.getId());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        SystemResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "System has not been retrieved successfully (null test):");
        Assertions.assertEquals(system1.getId(), retrieved.getId(), "The retrieved System has incorrect identifier:");
        Assertions.assertEquals(system1.getName(), retrieved.getName(), "The retrieved System has incorrect name:");
        Assertions.assertEquals(system1.getDescription(), retrieved.getDescription(), "The retrieved System has incorrect description:");
        Assertions.assertEquals(system1.getLocation().getLatitude(), retrieved.getLocation().getLatitude(), "The retrieved System has incorrect latitude:");
        Assertions.assertEquals(system1.getLocation().getLongitude(), retrieved.getLocation().getLongitude(), "The retrieved System has incorrect longitude:");
        Assertions.assertEquals(system1.getLocation().getVirtualLocation(), retrieved.getLocation().getVirtualLocation(), "The retrieved System has incorrect virtual location:");
        Assertions.assertEquals(system1.getOrganization(), retrieved.getOrganization(), "The retrieved System has incorrect organization:");
        Assertions.assertTrue(system1.getAdditionalInformation().containsAll(retrieved.getAdditionalInformation()), "The retrieved System has incorrect info:");
        Assertions.assertEquals(system1.getAdditionalInformation().size(), retrieved.getAdditionalInformation().size(), "The retrieved System has incorrect info size:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved System did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved System did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = systemServices.retrieveSystemByDatabaseID(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveSystemByDatabaseID()."));
    }

    @Test
    void retrieveSystemByName() {

        // Testing the retrieval of the first System.
        SystemResponseWrapper wrapper = systemServices.retrieveSystemByName(system1.getName());

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        SystemResponseDto retrieved = wrapper.getResponse();

        Assertions.assertNotNull(retrieved, "System has not been retrieved successfully (null test):");
        Assertions.assertEquals(system1.getId(), retrieved.getId(), "The retrieved System has incorrect identifier:");
        Assertions.assertEquals(system1.getName(), retrieved.getName(), "The retrieved System has incorrect name:");
        Assertions.assertEquals(system1.getDescription(), retrieved.getDescription(), "The retrieved System has incorrect description:");
        Assertions.assertEquals(system1.getLocation().getLatitude(), retrieved.getLocation().getLatitude(), "The retrieved System has incorrect latitude:");
        Assertions.assertEquals(system1.getLocation().getLongitude(), retrieved.getLocation().getLongitude(), "The retrieved System has incorrect longitude:");
        Assertions.assertEquals(system1.getLocation().getVirtualLocation(), retrieved.getLocation().getVirtualLocation(), "The retrieved System has incorrect virtual location:");
        Assertions.assertEquals(system1.getOrganization(), retrieved.getOrganization(), "The retrieved System has incorrect organization:");
        Assertions.assertTrue(system1.getAdditionalInformation().containsAll(retrieved.getAdditionalInformation()), "The retrieved System has incorrect info:");
        Assertions.assertEquals(system1.getAdditionalInformation().size(), retrieved.getAdditionalInformation().size(), "The retrieved System has incorrect info size:");
        Assertions.assertNotNull(retrieved.getCreationDate(), "The retrieved System did not receive a creation timestamp:");
        Assertions.assertNotNull(retrieved.getLatestUpdateDate(), "The retrieved System did not receive an update timestamp:");

        // Testing also the "Not Found" scenario.
        String random = RandomStringUtils.randomAlphabetic(10);
        wrapper = systemServices.retrieveSystemByName(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_NAME.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByName(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByName()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByName(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByName()."));
    }

    @Test
    void retrieveAllSystems() {

        // Testing the retrieval of both Systems.
        SystemResponsesWrapper wrapper = systemServices.retrieveAllSystems(0, 5);

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getListOfResponses(), "Wrapper has not received proper SUCCESS ListOfResponses:");
        Assertions.assertNotNull(wrapper.getPaginationInfo(), "Wrapper has not received proper SUCCESS PaginationInfo:");

        List<SystemResponseDto> retrievedList = wrapper.getListOfResponses();
        Assertions.assertEquals(2, retrievedList.size(), "Wrapper contains erroneous number of retrieved items:");

        PaginationInfo paginationInfo = wrapper.getPaginationInfo();
        Assertions.assertEquals(2, paginationInfo.getTotalItems(), "Pagination info contains invalid number of Total Items.");
        Assertions.assertEquals(1, paginationInfo.getTotalPages(), "Pagination info contains invalid number of Total Pages.");
        Assertions.assertEquals(0, paginationInfo.getCurrentPage(), "Pagination info contains invalid index of Current Page.");

        // Testing the retrieval of one System through Pagination.
        wrapper = systemServices.retrieveAllSystems(0, 1);

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
        systemRepository.delete(system1);
        systemRepository.delete(system2);
        wrapper = systemServices.retrieveAllSystems(0, 5);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(SystemErrorMessages.NO_SYSTEMS_FOUND.toString(), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertEquals(0, wrapper.getListOfResponses().size(), "Wrapper has not received proper NOT_FOUND ListOfResponses:");
        Assertions.assertEquals(0, wrapper.getPaginationInfo().getTotalItems(), "Wrapper has not received proper NOT_FOUND PaginationInfo:");
    }

    @Test
    void createSystem() {
        // Preparing a third System to be inserted.
        LocationRequestDto location = new LocationRequestDto(0.0, 0.0, "127.00.00.01:8080");
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));

        SystemRequestDto system3 = new SystemRequestDto();
        system3.setName("System3");
        system3.setDescription(RandomStringUtils.randomAlphabetic(10));
        system3.setLocation(location);
        system3.setOrganization(RandomStringUtils.randomAlphabetic(10));
        system3.setAdditionalInformation(info);

        // Testing the insertion.
        Long beforeInsertion = systemRepository.count();
        SystemResponseWrapper createdWrapper = systemServices.createSystem(system3);
        Long afterInsertion = systemRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, createdWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", createdWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(createdWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        SystemResponseDto created = createdWrapper.getResponse();

        Assertions.assertNotNull(created, "System has not been created successfully (null test):");
        Assertions.assertNotNull(created.getId(), "The created System received no identifier:");
        Assertions.assertEquals(system3.getName(), created.getName(), "The created System has incorrect name:");
        Assertions.assertEquals(system3.getDescription(), created.getDescription(), "The created System has incorrect description:");
        Assertions.assertEquals(system3.getLocation().getLatitude(), created.getLocation().getLatitude(), "The created System has incorrect latitude:");
        Assertions.assertEquals(system3.getLocation().getLongitude(), created.getLocation().getLongitude(), "The created System has incorrect longitude:");
        Assertions.assertEquals(system3.getLocation().getVirtualLocation(), created.getLocation().getVirtualLocation(), "The created System has incorrect virtual location:");
        Assertions.assertEquals(system3.getOrganization(), created.getOrganization(), "The created System has incorrect organization:");
        Assertions.assertTrue(system3.getAdditionalInformation().containsAll(created.getAdditionalInformation()), "The created System has incorrect info:");
        Assertions.assertEquals(system3.getAdditionalInformation().size(), created.getAdditionalInformation().size(), "The created System has incorrect info size:");
        Assertions.assertNotNull(created.getCreationDate(), "The created System did not receive a creation timestamp:");
        Assertions.assertNotNull(created.getLatestUpdateDate(), "The created System did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeInsertion + 1L), afterInsertion, "The number of entities has not increased by one:");

        // Testing also the "Conflict" scenario by attempting to re-insert the same information.
        beforeInsertion = systemRepository.count();
        SystemResponseWrapper createdWrapper2 = systemServices.createSystem(system3);
        afterInsertion = systemRepository.count();

        Assertions.assertEquals(ResponseCode.CONFLICT, createdWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(createdWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeInsertion, afterInsertion, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.createSystem(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method createSystem()."));

        // Cleaning up.
        systemRepository.deleteById(created.getId());
    }

    @Test
    void updateSystem() {
        // Preparing to update the second System.
        System retrievedSystem2 = systemRepository.findFirstByName("System2");

        SystemRequestDto updateRequest = new SystemRequestDto();
        updateRequest.setName("System2"); // Unchanged.
        updateRequest.setDescription("updatedDesc");
        updateRequest.setLocation(null);
        updateRequest.setOrganization("updatedOrg");
        Set<Object> addInfo = retrievedSystem2.getAdditionalInformation();
        int infoOriginalSize = addInfo.size();
        addInfo.add("something_new");
        updateRequest.setAdditionalInformation(addInfo);

        // Testing the update.
        Long beforeUpdate = systemRepository.count();
        SystemResponseWrapper updatedWrapper = systemServices.updateSystem(updateRequest, retrievedSystem2.getId());
        Long afterUpdate = systemRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, updatedWrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", updatedWrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(updatedWrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        SystemResponseDto updated = updatedWrapper.getResponse();

        Assertions.assertNotNull(updated, "System has not been updated successfully (null test):");
        Assertions.assertNotNull(updated.getId(), "The updated System received no identifier:");
        Assertions.assertEquals(retrievedSystem2.getId(), updated.getId(), "The updated System has changed identifier.");
        Assertions.assertEquals("System2", updated.getName(), "The updated System has incorrect name:");
        Assertions.assertEquals("updatedDesc", updated.getDescription(), "The updated System has incorrect description:");
        Assertions.assertNotEquals(retrievedSystem2.getLocation(), updated.getLocation(), "The updated System has incorrect location:");
        Assertions.assertEquals("updatedOrg", updated.getOrganization(), "The updated System has incorrect organization:");
        Assertions.assertTrue(updated.getAdditionalInformation().containsAll(updated.getAdditionalInformation()), "The updated System has incorrect info:");
        Assertions.assertEquals(infoOriginalSize + 1, updated.getAdditionalInformation().size(), "The created System has incorrect info size:");
        Assertions.assertNotNull(updated.getCreationDate(), "The updated System did not receive a creation timestamp:");
        Assertions.assertEquals(retrievedSystem2.getCreationDate(), updated.getCreationDate(), "The updated System changed timestamp:");
        Assertions.assertNotNull(updated.getLatestUpdateDate(), "The updated System did not receive an update timestamp:");
        Assertions.assertNotEquals(retrievedSystem2.getLatestUpdateDate(), updated.getLatestUpdateDate(), "The updated System still bears the same latest update timestamp:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed:");

        // Testing also the "Conflict" scenario by attempting to update the second system with a name that already exists.
        updateRequest.setName("System1");
        SystemResponseWrapper updatedWrapper2 = systemServices.updateSystem(updateRequest, retrievedSystem2.getId());

        Assertions.assertEquals(ResponseCode.CONFLICT, updatedWrapper2.getCode(), "Wrapper has not received proper CONFLICT ResponseCode:");
        Assertions.assertNull(updatedWrapper2.getResponse(), "Wrapper has not received proper CONFLICT Response:");
        Assertions.assertEquals(beforeUpdate, afterUpdate, "The number of entities has changed despite the CONFLICT:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        SystemResponseWrapper updatedWrapper3 = systemServices.updateSystem(updateRequest, random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, updatedWrapper3.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + random + "'."), updatedWrapper3.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(updatedWrapper3.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.updateSystem(updateRequest, " "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateSystem()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.updateSystem(updateRequest, null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateSystem()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> systemServices.updateSystem(updateRequest, RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method updateSystem()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.updateSystem(null, retrievedSystem2.getId()),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method updateSystem()."));
    }

    @Test
    void deleteSystem() {
        // Testing the deletion of the first System.
        Long beforeDeletion = systemRepository.count();
        SystemResponseWrapper wrapper = systemServices.deleteSystem(system1.getId());
        Long afterDeletion = systemRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotNull(wrapper.getResponse(), "Wrapper has not received proper SUCCESS Response:");

        SystemResponseDto deleted = wrapper.getResponse();

        Assertions.assertNotNull(deleted, "System has not been deleted successfully (null test):");
        Assertions.assertEquals(system1.getId(), deleted.getId(), "The deleted System has incorrect identifier:");
        Assertions.assertEquals(system1.getName(), deleted.getName(), "The deleted System has incorrect name:");
        Assertions.assertEquals(system1.getDescription(), deleted.getDescription(), "The deleted System has incorrect description:");
        Assertions.assertEquals(system1.getLocation().getLatitude(), deleted.getLocation().getLatitude(), "The deleted System has incorrect latitude:");
        Assertions.assertEquals(system1.getLocation().getLongitude(), deleted.getLocation().getLongitude(), "The deleted System has incorrect longitude:");
        Assertions.assertEquals(system1.getLocation().getVirtualLocation(), deleted.getLocation().getVirtualLocation(), "The deleted System has incorrect virtual location:");
        Assertions.assertEquals(system1.getOrganization(), deleted.getOrganization(), "The deleted System has incorrect organization:");
        Assertions.assertTrue(system1.getAdditionalInformation().containsAll(deleted.getAdditionalInformation()), "The deleted System has incorrect info:");
        Assertions.assertEquals(system1.getAdditionalInformation().size(), deleted.getAdditionalInformation().size(), "The deleted System has incorrect info size:");
        Assertions.assertNotNull(deleted.getCreationDate(), "The deleted System did not receive a creation timestamp:");
        Assertions.assertNotNull(deleted.getLatestUpdateDate(), "The deleted System did not receive an update timestamp:");
        Assertions.assertEquals(Long.valueOf(beforeDeletion - 1L), afterDeletion, "The number of entities has not decreased by one:");

        // Testing the retrieval of the first System. It must return nothing.
        System retrieved = systemRepository.findFirstByName("System1");
        Assertions.assertNull(retrieved, "The supposedly deleted System can still be retrieved:");

        // Testing also the "Not Found" scenario.
        String random = UUID.randomUUID().toString();
        wrapper = systemServices.deleteSystem(random);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, wrapper.getCode(), "Wrapper has not received proper NOT_FOUND ResponseCode:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString().concat("'" + random + "'."), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");

        // Testing also the "Invalid Parameter" scenario.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(" "),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByDatabaseID()."));

        thrown = assertThrows(
                IllegalArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(null),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid parameter detected for method retrieveSystemByDatabaseID()."));

        NonUuidArgumentException thrown2 = assertThrows(
                NonUuidArgumentException.class,
                () -> systemServices.retrieveSystemByDatabaseID(RandomStringUtils.randomAlphabetic(10)),
                "Invalid input parameter has not been detected."
        );
        Assertions.assertTrue(thrown2.getMessage().contains("Non-UUID parameter detected for method retrieveSystemByDatabaseID()."));
    }

    @Test
    void deleteAllSystems() {
        // Testing the deletion of both Test Systems.
        Long beforeDeletion = systemRepository.count();
        SystemResponseWrapper wrapper = systemServices.deleteAllSystems();
        Long afterDeletion = systemRepository.count();

        Assertions.assertEquals(ResponseCode.SUCCESS, wrapper.getCode(), "Wrapper has not received proper SUCCESS ResponseCode:");
        Assertions.assertEquals("Database transaction successfully concluded.", wrapper.getMessage(), "Wrapper has not received proper SUCCESS message:");
        Assertions.assertNotEquals(beforeDeletion, afterDeletion, "The number of entities has not changed:");
        Assertions.assertEquals(0, afterDeletion, "The number of entities is not zero:");
    }
}