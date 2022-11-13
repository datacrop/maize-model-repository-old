package eu.datacrop.maize.model_repository.api.controllers;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.services.VendorApiServices;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.VendorRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.VendorResponseDto;
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
 * This class intercepts API HTTP Requests pertaining to Vendors.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@RestController
@RequestMapping("model_repository/v1/asset_management/vendor")
@Tag(name = "Vendor", description = "An individual or company that procures goods or services.")
public class VendorController {

    @Autowired
    VendorApiServices services;


    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing Vendor using its databaseID as
     * unique identifier.
     *
     * @param  vendorID A UUID that uniquely identifies an existing Vendor in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Vendor by UUID", description = "Retrieves an existing Vendor using its UUID as unique identifier.")
    @GetMapping(path = "/{vendorID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Vendor has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Vendor to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendor with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveVendorByID(@PathVariable String vendorID) {
        log.info("Received GET request for Vendor with VendorID: {}.", vendorID);
        return services.retrieveVendorByDatabaseID(vendorID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing Vendor using its name as
     * unique identifier.
     *
     * @param  name A human-readable name that uniquely identifies an existing Vendor in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Vendor by Name", description = "Retrieves an existing Vendor using its Name as unique identifier.")
    @GetMapping(path = "/{name}/name/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Vendor has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Vendor to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendor with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveVendorByName(@PathVariable String name) {
        log.info("Received GET request for Vendor with Name: {}.", name);
        return services.retrieveVendorByName(name);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve all existing Vendors.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all Vendors", description = "Retrieves all Vendors ever persisted.")
    @GetMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Vendors have been successfully retrieved.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendorResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendors have been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveAllVendors(@Parameter(description = "Page number, default is 0") @RequestParam(value = "page", defaultValue = "0") int page,
                                             @Parameter(description = "Size of page, default is 10") @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received GET request for all Vendors with {}/{} pagination.", page, size);
        return services.retrieveAllVendors(page, size);
    }

    /******************************************************************************************************************
     * Method to intercept a POST Request that aims to persist a new Vendor.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Persist new Vendor", description = "Persists a new Vendor.")
    @PostMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created ~ Vendor has been successfully created.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendorResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Vendor to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ Vendor creation aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity createVendor(@RequestBody VendorRequestDto requestDto) {
        log.info("Received POST request for new Vendor with Name: '{}'.", requestDto.getName());
        return services.createVendor(requestDto);
    }

    /******************************************************************************************************************
     * Method to intercept a PUT Request that aims to update an existing Vendor using its databaseID as
     * unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Vendor, not null.
     * @param vendorID A UUID that uniquely identifies an existing Vendor in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Vendor by UUID", description = ("Updates an existing Vendor using its UUID as " +
            "unique identifier."))
    @PutMapping(path = "/{vendorID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Vendor has been successfully updated.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendorResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Vendor to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendor with the specified identifier has been found available to update.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ Vendor update aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity updateVendor(@RequestBody VendorRequestDto requestDto, @PathVariable String vendorID) {
        log.info("Received PUT request to update Vendor with ID: '{}'.", vendorID);
        return services.updateVendor(requestDto, vendorID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete an existing Vendor using its databaseID as
     * unique identifier.
     *
     * @param  vendorID A UUID that uniquely identifies an existing Vendor in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Vendor by UUID", description = "Deletes an existing Vendor using its UUID as unique identifier.")
    @DeleteMapping(path = "/{vendorID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Vendor has been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Vendor to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendor with the specified identifier has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteVendor(@PathVariable String vendorID) {
        log.info("Received DELETE request for Vendor with VendorID: {}.", vendorID);
        return services.deleteVendor(vendorID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete all existing Vendor entities.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete All Vendors", description = "Deletes all Vendors that have been ever persisted.")
    @DeleteMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No_Content ~ All Vendors have been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Vendor has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteAllVendors() {
        log.info("Received DELETE request for all Vendor entities.");
        return services.deleteAllVendors();
    }
}
