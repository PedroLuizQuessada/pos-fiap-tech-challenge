package com.example.tech_challenge.infraestructure.api.usertype;

import com.example.tech_challenge.controllers.RequesterController;
import com.example.tech_challenge.controllers.UserTypeController;
import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tipos-usuario")
@Tag(name = "User Type API V1", description = "Versão 1 do controlador referente a tipos de usuário")
public class UserTypeApiV1 {

    private final UserTypeController userTypeController;
    private final RequesterController requesterController;

    public UserTypeApiV1(UserDataSource userDataSource, UserTypeDataSource userTypeDataSource,
                         RequesterDataSource requesterDataSource, TokenDataSource tokenDataSource) {
        this.userTypeController = new UserTypeController(userDataSource, userTypeDataSource);
        this.requesterController = new RequesterController(requesterDataSource, tokenDataSource);
    }

    @Operation(summary = "Cria um tipo de usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Tipo de usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para o tipo de usuário a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<UserTypeResponse> create(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid UserTypeRequest userTypeRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} creating user type: {}", requesterResponse.login(), userTypeRequest.name());
        UserTypeResponse userTypeResponse = userTypeController.createUserType(userTypeRequest);
        log.info("User {} created user type: {}", requesterResponse.login(), userTypeResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userTypeResponse);
    }

    @Operation(summary = "Atualiza um tipo de usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Tipo de usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do tipo de usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Tipo de usuário a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserTypeResponse> update(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid UserTypeRequest userTypeRequest, @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} updating user type: {}", requesterResponse.login(), id);
        UserTypeResponse userTypeResponse = userTypeController.updateUserType(userTypeRequest, id);
        log.info("User {} updated user type: {}", requesterResponse.login(), id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userTypeResponse);
    }

    @Operation(summary = "Admin consulta todos os tipos de usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Tipos de usuário consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserTypeResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> findAll(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestHeader(name = "Authorization", required = false) String token) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} finding all user type", requesterResponse.login());
        List<UserTypeResponse> userTypeResponseList = userTypeController.findAllUserTypes();
        log.info("User {} found {} user types", requesterResponse.login(), userTypeResponseList.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userTypeResponseList);
    }

    @Operation(summary = "Apaga um tipo de usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Tipo de usuário apagado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Existem usuários associados ao tipo de usuário",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Tipo de usuário a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestHeader(name = "Authorization", required = false) String token,
                                       @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} deleting user type: {}", requesterResponse.login(), id);
        userTypeController.deleteUserType(id);
        log.info("User {} deleted user type: {}", requesterResponse.login(), id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    private RequesterResponse getRequester(UserDetails userDetails, String token) {
        return (!Objects.isNull(userDetails)) ?
                requesterController.getRequester(userDetails.getAuthorities().stream().findFirst().isPresent() ?
                        String.valueOf(userDetails.getAuthorities().stream().findFirst().get()) : null, userDetails.getUsername()) :
                requesterController.getRequester(token);
    }
}
