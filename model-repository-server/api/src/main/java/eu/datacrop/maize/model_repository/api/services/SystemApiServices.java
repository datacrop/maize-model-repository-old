package eu.datacrop.maize.model_repository.api.services;

import org.springframework.http.ResponseEntity;

/**********************************************************************************************************************
 * This interface defines the services offered by the API layer pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
public interface SystemApiServices {

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing IoT System using its databaseID
     * as unique identifier.
     *
     * @param systemID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveSystemByDatabaseID(String systemID);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve an existing IoT System using its name
     * as unique identifier.
     *
     * @param name A human-readable string that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity retrieveSystemByName(String name);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to retrieve all Systems paginated.
     *
     * @param page The page to retrieve.
     * @param size The intended size of pages.
     * @return A wrapped data transfer object with either information on the retrieved Systems or failure messages.
     *****************************************************************************************************************/
    ResponseEntity retrieveAllSystems(int page, int size);

    /******************************************************************************************************************
     * Method that connects to the persistence layer to delete an existing IoT System using its databaseID
     * as unique identifier.
     *
     * @param systemID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    ResponseEntity deleteSystem(String systemID);
}
