package eu.datacrop.maize.model_repository.api.controllers;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.services.SystemApiServices;
import eu.datacrop.maize.model_repository.commons.dtos.requests.SystemRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.SystemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Tag(name = "System", description = "An Internet-Of-Things System that is monitored for various purposes.")
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
    @Operation(summary = "Retrieve System by UUID", description = "Retrieves an existing System using its UUID as unique identifier.")
    @GetMapping(path = "/{systemID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ System has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SystemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No System with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveSystemByID(@PathVariable String systemID) {
        log.info("Received GET request for System with SystemID: {}.", systemID);
        return services.retrieveSystemByDatabaseID(systemID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing System using its name as
     * unique identifier.
     *
     * @param  name A human-readable name that uniquely identifies an existing System in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve System by Name", description = "Retrieves an existing System using its Name as unique identifier.")
    @GetMapping(path = "/{name}/name/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ System has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SystemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No System with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveSystemByName(@PathVariable String name) {
        log.info("Received GET request for System with Name: {}.", name);
        return services.retrieveSystemByName(name);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve all existing Systems.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all Systems", description = "Retrieves all Systems ever persisted.")
    @GetMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Systems have been successfully retrieved.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SystemResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Systems have been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveAllSystems(@Parameter(description = "Page number, default is 0") @RequestParam(value = "page", defaultValue = "0") int page,
                                             @Parameter(description = "Size of page, default is 10") @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received GET request for all Systems with {}/{} pagination.", page, size);
        return services.retrieveAllSystems(page, size);
    }

    /******************************************************************************************************************
     * Method to intercept a POST Request that aims to persist a new System.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Persist new System", description = "Persists a new System.")
    @PostMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED ~ System has been successfully created.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SystemResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ System creation aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity createSystem(@RequestBody SystemRequestDto requestDto) {
        log.info("Received POST request for new System with Name: '{}'.", requestDto.getName());
        return services.createSystem(requestDto);
    }

    /******************************************************************************************************************
     * Method to intercept a PUT Request that aims to update an existing System using its databaseID as
     * unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the System, not null.
     * @param systemID A UUID that uniquely identifies an existing System in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update System by UUID", description = ("Updates an existing System using its UUID as " +
            "unique identifier."))
    @PutMapping(path = "/{systemID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ System has been successfully updated.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SystemResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No System with the specified identifier has been found available to update.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ System update aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity updateSystem(@RequestBody SystemRequestDto requestDto, @PathVariable String systemID) {
        log.info("Received PUT request to update System with ID: '{}'.", systemID);
        return services.updateSystem(requestDto, systemID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete an existing System using its databaseID as
     * unique identifier.
     *
     * @param  systemID A UUID that uniquely identifies an existing System in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete System by UUID", description = "Deletes an existing System using its UUID as unique identifier.")
    @DeleteMapping(path = "/{systemID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ System has been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SystemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on System to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No System with the specified identifier has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteSystem(@PathVariable String systemID) {
        log.info("Received DELETE request for System with SystemID: {}.", systemID);
        return services.deleteSystem(systemID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete all existing System entities.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete All Systems", description = "Deletes all Systems that have been ever persisted.")
    @DeleteMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK ~ All Systems have been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No System has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteAllSystems() {
        log.info("Received DELETE request for all System entities.");
        return services.deleteAllSystems();
    }
}
