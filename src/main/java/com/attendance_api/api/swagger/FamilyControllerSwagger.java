package com.attendance_api.api.swagger;

import com.attendance_api.api.dto.FamilyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Family", description = "Manage the family register")
public interface FamilyControllerSwagger {

    @Operation(
            summary = "Registers a new family",
            description = "This endpoint receives a family along with its respective list of children, " +
                    "responsibles, and contacts, performing a cascade persistence in the database."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Family created successfully",
                    content = @Content(schema = @Schema(implementation = FamilyDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data or violation of the unique key (FAMILY_KEY)",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<FamilyDTO> registerFamily(
            @Parameter(description = "Request body containing the data of the family to be created", required = true)
            @RequestBody FamilyDTO familyDTO
    );

    @Operation(
            summary = "Find a family by its unique key",
            description = "Fetches a family record along with all its children, responsibles, and contacts using the provided FAMILY_KEY."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Family found successfully",
                    content = @Content(schema = @Schema(implementation = FamilyDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Family not found for the given key",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<FamilyDTO> getFamilyByKey(
            @Parameter(description = "The unique alphanumeric key of the family (FAMILY_KEY)", required = true, example = "4d6c9e98-2962-47d7-8251-baac45225d28")
            @PathVariable String key
    );
}