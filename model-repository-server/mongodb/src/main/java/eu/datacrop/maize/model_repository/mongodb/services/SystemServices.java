package eu.datacrop.maize.model_repository.mongodb.services;

import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.wrappers.single.SystemResponseWrapper;

public interface SystemServices {

    SystemResponseWrapper retrieveSystemByDatabaseID(String databaseID);

    SystemResponseWrapper retrieveSystemByName(String name);

    SystemResponseWrapper retrieveAllSystems();

    SystemResponseWrapper retrieveAllSystems(int page, int size);

    SystemResponseWrapper createSystem(SystemRequestDto requestDto);

    SystemResponseWrapper updateSystem(SystemRequestDto requestDto, String databaseID);

    SystemResponseWrapper deleteSystem(String databaseID);

    SystemResponseWrapper deleteAllSystems();

}
