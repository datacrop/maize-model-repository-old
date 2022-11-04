package eu.datacrop.maize.model_repository.api.controllers;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.services.SystemApiServices;
import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**********************************************************************************************************************
 * This class intercepts API HTTP Requests pertaining to IoT Systems.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
@RestController
@RequestMapping("model_repository/v1/asset_management/system")
@Api(value = "System_ControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE,
        description = "An Internet-Of-Things System that is monitored for various purposes.", tags = "System")
public class SystemController {

    @Autowired
    SystemApiServices services;


    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing System using its databaseID as
     * unique identifier.
     *
     * @param  systemID A UUID that uniquely identifies an existing System in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieves a specific System using its UUID unique identifier.", response = SystemRequestDto.class)
    @GetMapping(path = "/{systemID}/id/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK ~ System has been successfully retrieved.",
                    response = SystemRequestDto.class),
            @ApiResponse(code = 400, message = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Not_Found ~ No System with the specified identifier has been found available to retrieve.",
                    response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal_Server_Error ~ Internal Server Error occurred.",
                    response = ErrorMessage.class)
    })
    public ResponseEntity retrieveSystemByID(@PathVariable String systemID) {
        log.info("Received GET request for System with SystemID: {}.", systemID);
        return services.retrieveSystemByDatabaseID(systemID);
    }
}
