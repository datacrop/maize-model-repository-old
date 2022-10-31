package eu.datacrop.maize.model_repository.persistence.daos;

import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;

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
     *****************************************************************************************************************/
    SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID);

}
