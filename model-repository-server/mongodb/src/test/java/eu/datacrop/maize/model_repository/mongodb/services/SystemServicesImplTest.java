package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.responses.SystemResponseDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.SystemErrorMessages;
import eu.datacrop.maize.model_repository.commons.util.DateFormatter;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.mongodb.model.System;
import eu.datacrop.maize.model_repository.mongodb.model.auxiliary.Location;
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

@ActiveProfiles("devmongo")
@SpringBootTest(classes = SystemServicesImpl.class)
@SpringBootApplication
@ComponentScan(basePackages = {
        "eu.datacrop.maize.model_repository.commons.dtos",
        "eu.datacrop.maize.model_repository.commons.enums",
        "eu.datacrop.maize.model_repository.commons.error",
        "eu.datacrop.maize.model_repository.commons.error.exceptions",
        "eu.datacrop.maize.model_repository.commons.error.messages",
        "eu.datacrop.maize.model_repository.commons.util",
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
})
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"eu.datacrop.maize.model_repository.mongodb.repositories"})
@EntityScan(basePackages = {
        "eu.datacrop.maize.model_repository.mongodb.model",
        "eu.datacrop.maize.model_repository.mongodb.model.auxiliary"
})
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
        Assertions.assertEquals(SystemErrorMessages.NOT_FOUND_ID.toString().concat(random), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");
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
        Assertions.assertEquals(SystemErrorMessages.NOT_FOUND_NAME.toString().concat(random), wrapper.getMessage(), "Wrapper has not received proper NOT_FOUND message:");
        Assertions.assertNull(wrapper.getResponse(), "Wrapper has not received proper NOT_FOUND Response:");
    }
}