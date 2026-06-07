package com.attendance_api.api.swagger;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Child", description = "Manage the child registers")
public interface ChildControllerSwagger {
    @Operation(
            summary = "Get total count of registered children",
            description = "Returns the total number of children currently registered in the database."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Count retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class, example = "42")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<Long> getRegisteredChildren();

    @Operation(
            summary = "Search children by filters",
            description = """
                    Fetches a list of child records filtered.
                    The search is case-insensitive and matches partial names
                    (e.g., searching 'Monica' returns 'Monica Jesus' and 'Monica Bergamo').
                    If no filter is provided, all children are returned.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Children retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ChildResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<List<ChildResponseDTO>> searchChildren(@ParameterObject ChildFilterDTO filter);
}
