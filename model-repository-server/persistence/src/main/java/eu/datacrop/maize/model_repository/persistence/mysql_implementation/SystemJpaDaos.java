package eu.datacrop.maize.model_repository.persistence.mysql_implementation;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;
import eu.datacrop.maize.model_repository.persistence.daos.SystemPersistenceLayerDaos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("devmysql")
public class SystemJpaDaos implements SystemPersistenceLayerDaos {

    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper retrieveSystemByName(String name) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to retrieve all Systems paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Systems or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponsesWrapper retrieveAllSystems(int page, int size) {
        return null;
    }

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper createSystem(SystemRequestDto requestDto) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to update an existing System using its databaseID as unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the updated System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteSystem(String databaseID) throws IllegalArgumentException {
        return null;
    }

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    @Override
    public SystemResponseWrapper deleteAllSystems() {
        return null;
    }
}
