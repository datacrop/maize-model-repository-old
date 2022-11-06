package eu.datacrop.maize.model_repository.api.services;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.error.ErrorMessages;
import eu.datacrop.maize.model_repository.commons.dtos.requests.LocationRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.daos.SystemMongoDbDao;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApiServicesImpl.class)
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
        "eu.datacrop.maize.model_repository.mongodb.converters.auxiliary",
        "eu.datacrop.maize.model_repository.mongodb.daos",
        "eu.datacrop.maize.model_repository.mongodb.listeners",
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary",
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
@ActiveProfiles("devmongo")
//@EnableMongoRepositories(basePackages = {"eu.datacrop.maize.model_repository.mongodb.repositories"})
class SystemApiServicesImplTest {

    @Autowired
    SystemApiServices apiServices;

    @Autowired
    SystemMongoDbDao mongoDbDao;

    SystemResponseWrapper system1;
    SystemResponseWrapper system2;
    SystemResponseWrapper system3;

    @BeforeEach
    void setUp() {

        // Creating a dummy location.
        LocationRequestDto location = new LocationRequestDto(37.568180, 22.808661, "");

        // Creating a structure to be used as "Additional Information".
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));
        List<Integer> ints = Arrays.asList(10, 20, 30, 40, 50, 60);
        info.add(ints);

        // Creating two different dummy Systems using the above (and different names, descriptions, organizations).
        SystemRequestDto syst = new SystemRequestDto();
        syst.setName("System1");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        syst.setLocation(location);
        syst.setOrganization(RandomStringUtils.randomAlphabetic(10));
        syst.setAdditionalInformation(info);
        system1 = mongoDbDao.createSystem(syst);

        syst = new SystemRequestDto();
        syst.setName("System2");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        syst.setLocation(location);
        syst.setOrganization(RandomStringUtils.randomAlphabetic(10));
        syst.setAdditionalInformation(info);
        system2 = mongoDbDao.createSystem(syst);
    }

    @AfterEach
    void tearDown() {
        mongoDbDao.deleteAllSystems();
    }

    private void resetSystem3() {
        // Creating a dummy location.
        LocationRequestDto location = new LocationRequestDto(37.568180, 22.808661, "");

        // Creating a structure to be used as "Additional Information".
        Set<Object> info = new HashSet<Object>();
        info.add(UUID.randomUUID());
        info.add(RandomStringUtils.randomAlphabetic(10));
        info.add(LocalDateTime.now().format(DateFormatter.formatter));
        List<Integer> ints = Arrays.asList(10, 20, 30, 40, 50, 60);
        info.add(ints);

        SystemRequestDto syst = new SystemRequestDto();
        syst.setName("System3");
        syst.setDescription(RandomStringUtils.randomAlphabetic(10));
        syst.setLocation(location);
        syst.setOrganization(RandomStringUtils.randomAlphabetic(10));
        syst.setAdditionalInformation(info);
        system3 = mongoDbDao.createSystem(syst);
    }

    @Test
    void retrieveSystemByDatabaseID() {

        SystemResponseDto firstSystem = system1.getResponse();

        // Attempting to retrieve the first System.
        ResponseEntity foundByID = apiServices.retrieveSystemByDatabaseID(firstSystem.getId());
        Assertions.assertNotNull(foundByID, "System not retrieved successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByID.getStatusCode(), "System has not been retrieved with HTTP Request Code 200:");
        SystemResponseDto retrievedSystem = (SystemResponseDto) foundByID.getBody();
        Assertions.assertNotNull(retrievedSystem, "System not retrieved successfully by ID (null test 2):");

        // Checking the contents of the retrieved System.
        Assertions.assertEquals(firstSystem.getId(), retrievedSystem.getId(), "The retrieved System has incorrect identifier:");
        Assertions.assertEquals(firstSystem.getName(), retrievedSystem.getName(), "The retrieved System has incorrect name:");
        Assertions.assertEquals(firstSystem.getDescription(), retrievedSystem.getDescription(), "The retrieved System has incorrect description:");
        Assertions.assertEquals(firstSystem.getLocation(), retrievedSystem.getLocation(), "The retrieved System has incorrect location:");
        Assertions.assertEquals(firstSystem.getLocation().getLatitude(), retrievedSystem.getLocation().getLatitude(), "The retrieved System has incorrect latitude:");
        Assertions.assertEquals(firstSystem.getLocation().getLongitude(), retrievedSystem.getLocation().getLongitude(), "The retrieved System has incorrect longitude:");
        Assertions.assertEquals(firstSystem.getLocation().getVirtualLocation(), retrievedSystem.getLocation().getVirtualLocation(), "The retrieved System has incorrect virtual location:");
        Assertions.assertEquals(firstSystem.getOrganization(), retrievedSystem.getOrganization(), "The retrieved System has incorrect organization:");
        Assertions.assertEquals(firstSystem.getAdditionalInformation(), retrievedSystem.getAdditionalInformation(), "The retrieved System has incorrect additional information:");
        Assertions.assertEquals(
                firstSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved System has incorrect creation date:");
        Assertions.assertEquals(
                firstSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved System has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByID = apiServices.retrieveSystemByDatabaseID(null);
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
        foundByID = apiServices.retrieveSystemByDatabaseID("");
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
        foundByID = apiServices.retrieveSystemByDatabaseID(" ");
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
        foundByID = apiServices.retrieveSystemByDatabaseID(RandomStringUtils.randomAlphabetic(10));
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
        foundByID = apiServices.retrieveSystemByDatabaseID(UUID.randomUUID().toString());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByID.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByID.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveSystemByName() {

        SystemResponseDto firstSystem = system1.getResponse();

        // Attempting to retrieve the first System.
        ResponseEntity foundByName = apiServices.retrieveSystemByName(firstSystem.getName());
        Assertions.assertNotNull(foundByName, "System not retrieved successfully by Name (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundByName.getStatusCode(), "System has not been retrieved with HTTP Request Code 200:");
        SystemResponseDto retrievedSystem = (SystemResponseDto) foundByName.getBody();
        Assertions.assertNotNull(retrievedSystem, "System not retrieved successfully by Name (null test 2):");

        // Checking the contents of the retrieved System.
        Assertions.assertEquals(firstSystem.getId(), retrievedSystem.getId(), "The retrieved System has incorrect identifier:");
        Assertions.assertEquals(firstSystem.getName(), retrievedSystem.getName(), "The retrieved System has incorrect name:");
        Assertions.assertEquals(firstSystem.getDescription(), retrievedSystem.getDescription(), "The retrieved System has incorrect description:");
        Assertions.assertEquals(firstSystem.getLocation(), retrievedSystem.getLocation(), "The retrieved System has incorrect location:");
        Assertions.assertEquals(firstSystem.getLocation().getLatitude(), retrievedSystem.getLocation().getLatitude(), "The retrieved System has incorrect latitude:");
        Assertions.assertEquals(firstSystem.getLocation().getLongitude(), retrievedSystem.getLocation().getLongitude(), "The retrieved System has incorrect longitude:");
        Assertions.assertEquals(firstSystem.getLocation().getVirtualLocation(), retrievedSystem.getLocation().getVirtualLocation(), "The retrieved System has incorrect virtual location:");
        Assertions.assertEquals(firstSystem.getOrganization(), retrievedSystem.getOrganization(), "The retrieved System has incorrect organization:");
        Assertions.assertEquals(firstSystem.getAdditionalInformation(), retrievedSystem.getAdditionalInformation(), "The retrieved System has incorrect additional information:");
        Assertions.assertEquals(
                firstSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved System has incorrect creation date:");
        Assertions.assertEquals(
                firstSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                retrievedSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The retrieved System has incorrect latest update date:");

        // Checking "unhappy path": null identifier.
        foundByName = apiServices.retrieveSystemByName(null);
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
        foundByName = apiServices.retrieveSystemByName("");
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
        foundByName = apiServices.retrieveSystemByName(" ");
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
        foundByName = apiServices.retrieveSystemByName(RandomStringUtils.randomAlphabetic(10));
        Assertions.assertNotNull(foundByName, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundByName.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundByName.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.SYSTEM_NOT_FOUND_NAME.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_NAME.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void retrieveAllSystems() {

        // Attempting to retrieve both Systems (with convenient pagination).
        ResponseEntity foundAll = apiServices.retrieveAllSystems(0, 5);
        Assertions.assertNotNull(foundAll, "System list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Systems have not been retrieved with HTTP Request Code 200:");
        List<Object> systemList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(systemList, "System list not retrieved successfully (null test 2):");
        Assertions.assertEquals(2, systemList.size(), "System list has improper size.");

        // Attempting to retrieve the first System (respecting pagination).
        foundAll = apiServices.retrieveAllSystems(0, 1);
        Assertions.assertNotNull(foundAll, "System list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Systems have not been retrieved with HTTP Request Code 200:");
        systemList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(systemList, "System list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, systemList.size(), "System list has improper size.");

        // Attempting to retrieve the second System (respecting pagination).
        foundAll = apiServices.retrieveAllSystems(1, 1);
        Assertions.assertNotNull(foundAll, "System list not retrieved successfully (null test 1):");
        Assertions.assertSame(HttpStatus.OK, foundAll.getStatusCode(), "Systems have not been retrieved with HTTP Request Code 200:");
        systemList = (List<Object>) foundAll.getBody();
        Assertions.assertNotNull(systemList, "System list not retrieved successfully (null test 2):");
        Assertions.assertEquals(1, systemList.size(), "System list has improper size.");

        // Checking "unhappy path": Attempting to retrieve both Systems (with pagination that exceeds the limit).
        foundAll = apiServices.retrieveAllSystems(3, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.EXCEEDED_PAGE_LIMIT.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.EXCEEDED_PAGE_LIMIT.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Cleaning the database.
        mongoDbDao.deleteAllSystems();

        // Checking "unhappy path": Attempting to retrieve anything expecting to find nothing.
        foundAll = apiServices.retrieveAllSystems(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.NO_SYSTEMS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.NO_SYSTEMS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void createSystem() {
    }

    @Test
    void updateSystem() {
    }

    @Test
    void deleteSystem() {

        SystemResponseDto firstSystem = system1.getResponse();

        // Attempting to delete the first System.
        ResponseEntity deletedById = apiServices.deleteSystem(firstSystem.getId());
        Assertions.assertNotNull(deletedById, "System not deleted successfully by ID (null test 1):");
        Assertions.assertSame(HttpStatus.OK, deletedById.getStatusCode(), "System has not been deleted with HTTP Request Code 200:");
        SystemResponseDto deletedSystem = (SystemResponseDto) deletedById.getBody();
        Assertions.assertNotNull(deletedSystem, "System not deleted successfully by ID (null test 2):");

        // Checking the contents of the retrieved System.
        Assertions.assertEquals(firstSystem.getId(), deletedSystem.getId(), "The deleted System has incorrect identifier:");
        Assertions.assertEquals(firstSystem.getName(), deletedSystem.getName(), "The deleted System has incorrect name:");
        Assertions.assertEquals(firstSystem.getDescription(), deletedSystem.getDescription(), "The deleted System has incorrect description:");
        Assertions.assertEquals(firstSystem.getLocation(), deletedSystem.getLocation(), "The deleted System has incorrect location:");
        Assertions.assertEquals(firstSystem.getLocation().getLatitude(), deletedSystem.getLocation().getLatitude(), "The deleted System has incorrect latitude:");
        Assertions.assertEquals(firstSystem.getLocation().getLongitude(), deletedSystem.getLocation().getLongitude(), "The deleted System has incorrect longitude:");
        Assertions.assertEquals(firstSystem.getLocation().getVirtualLocation(), deletedSystem.getLocation().getVirtualLocation(), "The deleted System has incorrect virtual location:");
        Assertions.assertEquals(firstSystem.getOrganization(), deletedSystem.getOrganization(), "The deleted System has incorrect organization:");
        Assertions.assertEquals(firstSystem.getAdditionalInformation(), deletedSystem.getAdditionalInformation(), "The deleted System has incorrect additional information:");
        Assertions.assertEquals(
                firstSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                deletedSystem.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted System has incorrect creation date:");
        Assertions.assertEquals(
                firstSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                deletedSystem.getLatestUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                "The deleted System has incorrect latest update date:");

        // Attempting to retrieve the first System. It must fail.
        SystemResponseWrapper foundByID = mongoDbDao.retrieveSystemByDatabaseID(firstSystem.getId());
        Assertions.assertNotNull(foundByID, "Error message has not been formulated:");
        Assertions.assertSame(ResponseCode.NOT_FOUND, foundByID.getCode(), "Error message has not been formulated with HTTP Request Code 404:");

        // Checking "unhappy path": null identifier.
        deletedById = apiServices.deleteSystem(null);
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
        deletedById = apiServices.deleteSystem("");
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
        deletedById = apiServices.deleteSystem(" ");
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
        deletedById = apiServices.deleteSystem(RandomStringUtils.randomAlphabetic(10));
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
        deletedById = apiServices.deleteSystem(UUID.randomUUID().toString());
        Assertions.assertNotNull(deletedById, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deletedById.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deletedById.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.SYSTEM_NOT_FOUND_ID.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");
    }

    @Test
    void deleteAllSystems() {

        // Attempting to delete both Systems.
        ResponseEntity deleteAll = apiServices.deleteAllSystems();
        Assertions.assertNotNull(deleteAll, "Systems have not been deleted successfully (null test 1):");
        Assertions.assertSame(HttpStatus.NO_CONTENT, deleteAll.getStatusCode(), "Systems have not been deleted with HTTP Request Code 204:");
        String body = (String) deleteAll.getBody();
        Assertions.assertNotNull(body, "Systems have not been deleted successfully (null test 2):");
        Assertions.assertEquals(body, "Successfully deleted all Systems from the persistence layer.", "Deletion success message not received:");

        // Attempting to retrieve anything expecting to find nothing.
        ResponseEntity foundAll = apiServices.retrieveAllSystems(0, 5);
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, foundAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        ErrorMessage errorMessage = (ErrorMessage) foundAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.NO_SYSTEMS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.NO_SYSTEMS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

        // Checking "unhappy path": Attempting to re-delete expecting to find nothing.
        deleteAll = apiServices.deleteAllSystems();
        Assertions.assertNotNull(foundAll, "Error message has not been formulated:");
        Assertions.assertSame(HttpStatus.NOT_FOUND, deleteAll.getStatusCode(), "Error message has not been formulated with HTTP Request Code 404:");
        errorMessage = (ErrorMessage) deleteAll.getBody();
        Assertions.assertNotNull(errorMessage, "Error message has not been returned:");
        Assertions.assertEquals(404, errorMessage.getHttpCode(), "The error message has incorrect http code:");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(), errorMessage.getHttpText(), "The error message has incorrect http status:");
        Assertions.assertTrue(errorMessage.getMessage().contains(SystemErrorMessages.NO_SYSTEMS_FOUND.toString()), "The error message has incorrect message:");
        Assertions.assertEquals(SystemErrorMessages.NO_SYSTEMS_FOUND.name(), errorMessage.getMessageKey(), "The error message has incorrect message key:");
        Assertions.assertNotNull(errorMessage.getTimestamp(), "The error message did not receive timestamp:");

    }


}