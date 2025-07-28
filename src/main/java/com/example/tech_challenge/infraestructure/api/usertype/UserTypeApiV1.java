package com.example.tech_challenge.infraestructure.api.usertype;

import com.example.tech_challenge.controllers.UserTypeController;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tipos-usuario")
@Tag(name = "User Type API V1", description = "Versão 1 do controlador referente a tipos de usuário")
public class UserTypeApiV1 {

    private final UserTypeController userTypeController;

    public UserTypeApiV1(UserDataSource userDataSource, UserTypeDataSource userTypeDataSource) {
        this.userTypeController = new UserTypeController(userDataSource, userTypeDataSource);
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
    @PostMapping("/admin")
    public ResponseEntity<UserTypeResponse> create(@RequestBody @Valid UserTypeRequest userTypeRequest) {
        log.info("Admin creating user type: {}", userTypeRequest.name());
        UserTypeResponse userTypeResponse = userTypeController.createUserType(userTypeRequest);
        log.info("Admin created user type: {}", userTypeResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userTypeResponse);
    }

    @Operation(summary = "Atualiza o nome de um tipo de usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Nome do tipo de usuário atualizado com sucesso",
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
    @PutMapping("/admin/{id}")
    public ResponseEntity<UserTypeResponse> update(@RequestBody @Valid UserTypeRequest userTypeRequest,
                                                   @PathVariable("id") Long id) {
        log.info("Admin updating user type name: {}", id);
        UserTypeResponse userTypeResponse = userTypeController.updateUserTypeName(userTypeRequest, id);
        log.info("Admin updated user type name: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userTypeResponse);
    }

    @Operation(summary = "Consulta todos os tipos de usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Tipos de usuário consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserTypeResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> findAll(@RequestParam("page") int page,
                                                          @RequestParam("size") int size) {
        log.info("User finding all user type");
        List<UserTypeResponse> userTypeResponseList = userTypeController.findAllUserTypes(page, size);
        log.info("User found {} user types", userTypeResponseList.size());

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
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.info("User deleting user type: {}", id);
        userTypeController.deleteUserType(id);
        log.info("User deleted user type: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
