package eu.datacrop.maize.model_repository.api.controllers;

import eu.datacrop.maize.model_repository.api.services.SystemApiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**********************************************************************************************************************
 * This class intercepts API HTTP Requests pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@RestController
@RequestMapping("v1/model_repository/asset_management/system")
public class SystemController {

    @Autowired
    SystemApiServices services;


    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing System using its databaseID as
     * unique identifier.
     *
     * @param  systemID A UUID that uniquely identifies an existing System in the database, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @GetMapping(path = "/{systemID}/id/")
    public ResponseEntity retrieveSystemByID(@PathVariable String systemID) {
        log.info("Received GET request for System with SystemID: {}.", systemID);
        return services.retrieveSystemByDatabaseID(systemID);
    }
}
