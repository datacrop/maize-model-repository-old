package eu.datacrop.maize.model_repository.api.controllers;

import eu.datacrop.maize.model_repository.api.error.ErrorMessage;
import eu.datacrop.maize.model_repository.api.services.AssetCategoryApiServices;
import eu.datacrop.maize.model_repository.commons.dtos.requests.entities.AssetCategoryRequestDto;
import eu.datacrop.maize.model_repository.commons.dtos.responses.entities.AssetCategoryResponseDto;
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
 * This class intercepts API HTTP Requests pertaining to Asset Categories.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Slf4j
@RestController
@RequestMapping("model_repository/v1/asset_management/assetCategory")
@Tag(name = "Asset Category", description = "A grouping of Assets that exhibit similar characteristics.")
public class AssetCategoryController {

    @Autowired
    AssetCategoryApiServices services;


    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing Asset Category using its databaseID as
     * unique identifier.
     *
     * @param  assetCategoryID A UUID that uniquely identifies an existing Asset Category in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Asset Category by UUID", description = "Retrieves an existing Asset Category using its UUID as unique identifier.")
    @GetMapping(path = "/{assetCategoryID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Asset Category has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetCategoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Asset Category to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Category with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveAssetCategoryByID(@PathVariable String assetCategoryID) {
        log.info("Received GET request for AssetCategory with Asset CategoryID: {}.", assetCategoryID);
        return services.retrieveAssetCategoryByDatabaseID(assetCategoryID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve an existing Asset Category using its name as
     * unique identifier.
     *
     * @param  name A human-readable name that uniquely identifies an existing Asset Category in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Asset Category by Name", description = "Retrieves an existing Asset Category using its Name as unique identifier.")
    @GetMapping(path = "/{name}/name/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Asset Category has been successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetCategoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Asset Category to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Category with the specified identifier has been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveAssetCategoryByName(@PathVariable String name) {
        log.info("Received GET request for Asset Category with Name: {}.", name);
        return services.retrieveAssetCategoryByName(name);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to retrieve all existing Asset Categories.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all Asset Categories", description = "Retrieves all Asset Categories ever persisted.")
    @GetMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Asset Categories have been successfully retrieved.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AssetCategoryResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Categories have been found available to retrieve.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity retrieveAllAssetCategories(@Parameter(description = "Page number, default is 0") @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @Parameter(description = "Size of page, default is 10") @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Received GET request for all Asset Categories with {}/{} pagination.", page, size);
        return services.retrieveAllAssetCategories(page, size);
    }

    /******************************************************************************************************************
     * Method to intercept a POST Request that aims to persist a new Asset Category.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Persist new Asset Category", description = "Persists a new AssetCategory.")
    @PostMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED ~ Asset Category has been successfully created.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AssetCategoryResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Asset Category to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ Asset Category creation aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity createAssetCategory(@RequestBody AssetCategoryRequestDto requestDto) {
        log.info("Received POST request for new Asset Category with Name: '{}'.", requestDto.getName());
        return services.createAssetCategory(requestDto);
    }

    /******************************************************************************************************************
     * Method to intercept a PUT Request that aims to update an existing Asset Category using its databaseID as
     * unique identifier.
     *
     * @param requestDto A data transfer object with values for the attributes of the Asset Category, not null.
     * @param assetCategoryID A UUID that uniquely identifies an existing Asset Category in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Asset Category by UUID", description = ("Updates an existing Asset Category using its UUID as " +
            "unique identifier."))
    @PutMapping(path = "/{assetCategoryID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Asset Category has been successfully updated.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AssetCategoryResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Asset Category to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Category with the specified identifier has been found available to update.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict ~ Asset Category update aborted due to Name conflict.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity updateAssetCategory(@RequestBody AssetCategoryRequestDto requestDto, @PathVariable String assetCategoryID) {
        log.info("Received PUT request to update Asset Category with ID: '{}'.", assetCategoryID);
        return services.updateAssetCategory(requestDto, assetCategoryID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete an existing Asset Category using its databaseID as
     * unique identifier.
     *
     * @param  assetCategoryID A UUID that uniquely identifies an existing Asset Category in the persistence layer, not null.
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Asset Category by UUID", description = "Deletes an existing Asset Category using its UUID as unique identifier.")
    @DeleteMapping(path = "/{assetCategoryID}/id/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK ~ Asset Category has been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetCategoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad_Request ~ Erroneous request operation on Asset Category to be aborted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Category with the specified identifier has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteAssetCategory(@PathVariable String assetCategoryID) {
        log.info("Received DELETE request for Asset Category with Asset CategoryID: {}.", assetCategoryID);
        return services.deleteAssetCategory(assetCategoryID);
    }

    /******************************************************************************************************************
     * Method to intercept a GET Request that aims to delete all existing Asset Category entities.
     *
     * @return A data structure to be transmitted from server to client as response.
     *****************************************************************************************************************/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete All Asset Categories", description = "Deletes all Asset Categories that have been ever persisted.")
    @DeleteMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK ~ All Asset Categories have been successfully deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not_Found ~ No Asset Category has been found available to delete.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal_Server_Error ~ Internal Server Error occurred.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity deleteAllAssetCategories() {
        log.info("Received DELETE request for all Asset Category entities.");
        return services.deleteAllAssetCategories();
    }
}
