package eu.datacrop.maize.model_repository.persistence.daos;

import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.collection.SystemResponsesWrapper;
import eu.datacrop.maize.model_repository.commons.wrappers.single.entities.SystemResponseWrapper;

/**********************************************************************************************************************
 * This interface defines the services offered by the persistence layer pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public interface SystemPersistenceLayerDaos {

    /******************************************************************************************************************
     * Method to retrieve an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to retrieve an existing System using its name as unique identifier.
     *
     * @param name A string that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the retrieved System or failure messages.
     *
     * @throws IllegalArgumentException - if name is null.
     *****************************************************************************************************************/
    SystemResponseWrapper retrieveSystemByName(String name) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to retrieve all Systems paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Systems or failure messages.
     *****************************************************************************************************************/
    SystemResponsesWrapper retrieveAllSystems(int page, int size);

    /******************************************************************************************************************
     * Method to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A wrapped data transfer object with either information on the created System or failure messages.
     *
     * @throws IllegalArgumentException - if requestDto is null.
     *****************************************************************************************************************/
    SystemResponseWrapper createSystem(SystemRequestDto requestDto) throws IllegalArgumentException;

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
    SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete an existing System using its databaseID as unique identifier.
     *
     * @param databaseID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A wrapped data transfer object with either information on the deleted System or failure messages.
     *
     * @throws IllegalArgumentException - if databaseID is null or empty string.
     *****************************************************************************************************************/
    SystemResponseWrapper deleteSystem(String databaseID) throws IllegalArgumentException;

    /******************************************************************************************************************
     * Method to delete all existing Systems.
     *
     * @return A wrapped data transfer object with either a success message or failure messages.
     *****************************************************************************************************************/
    SystemResponseWrapper deleteAllSystems();

}
