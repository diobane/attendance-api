package com.attendance_api.api.swagger;

import com.attendance_api.api.dto.ChildDetailsResponseDTO;
import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
                    Fetches a paginated list of child records filtered.
                    The search is case-insensitive and matches partial names
                    (e.g., searching 'Monica' returns 'Monica Jesus' and 'Monica Bergamo').
                    If no filter is provided, all children are returned.
                    Pagination is controlled via 'page', 'size' and 'sort' query parameters.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Children retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChildResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<Page<ChildResponseDTO>> searchChildren(
            @ParameterObject ChildFilterDTO filter,
            @ParameterObject Pageable pageable);

    @Operation(
            summary = "Get detailed information of a child",
            description = "Returns the full details of a child by ID, including team, family and responsibles with contact information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Child details retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChildDetailsResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Child not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<ChildDetailsResponseDTO> getChildDetails(
            @Parameter(description = "ID of the child to retrieve", example = "1")
            Long childId);

    @Operation(
            summary = "Update the team of a child",
            description = "Update the team of a child"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Child details retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Child not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    ResponseEntity<Void> updateChildTeam(@PathVariable Long childId, @NotNull @RequestParam Long teamId);
}