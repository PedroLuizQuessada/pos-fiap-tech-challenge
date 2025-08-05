package com.example.tech_challenge.mappers;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

import com.example.tech_challenge.dtos.*;
import com.example.tech_challenge.dtos.requests.*;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.dtos.responses.*;

import java.sql.Date;
import java.time.Instant;

class UserMapperTest {

    // Valores padrão válidos usados na maioria dos testes
    private final Long USER_ID = 99L;
    private final String NAME = "Alice";
    private final String EMAIL = "alice@example.com";
    private final String LOGIN = "alice123";
    private final String PASSWORD = "password";
    private final Date UPDATED = new Date(Date.from(Instant.now()).getTime());

    // Endereços e tipos para tests de mapeamento
    private static AddressDto makeAddressDto(long id) {
        return new AddressDto(id, "SP", "São Paulo", "Rua A", "100", "01000-000", "apt");
    }

    private static AddressRequest makeAddressReq() {
        return new AddressRequest("SP", "São Paulo", "Rua A", "100", "01000-000", "apt");
    }

    private static UserTypeDto makeTypeDto(long id, String name) {
        return new UserTypeDto(id, name);
    }

    private static UserType makeTypeEntity(long id, String name) {
        return new UserType(id, name);
    }

    @Nested
    class MappingEntityToDto {
        @Test
        void toDto_and_toAdminResponse_roundtrip() {
            Address address = AddressMapper.toEntity(makeAddressDto(55L));
            UserType type = makeTypeEntity(77L, "OWNER");
            User user = new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, UPDATED, address, type, false);

            UserDto dto = UserMapper.toDto(user);
            assertThat(dto.id()).isEqualTo(USER_ID);
            assertThat(dto.email()).isEqualTo(EMAIL);
            assertThat(dto.address().id()).isEqualTo(55L);
            assertThat(dto.userType().id()).isEqualTo(77L);

            UserResponse respRes = UserMapper.toResponse(user);
            assertThat(respRes.id()).isNull();
            assertThat(respRes.name()).isEqualTo(NAME);
            assertThat(respRes.userType().id()).isNull();

            UserResponse respAdm = UserMapper.toAdminResponse(user);
            assertThat(respAdm.id()).isEqualTo(USER_ID);
            assertThat(respAdm.userType().id()).isEqualTo(77L);
        }

        @Test
        void toEntity_fromDto_and_map_back_response() {
            AddressDto addressDto = makeAddressDto(33L);
            UserTypeDto utDto = makeTypeDto(44L, "OWNER");
            UserDto userDto = new UserDto(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, UPDATED, addressDto, utDto);

            User user = UserMapper.toEntity(userDto, false);
            assertThat(user.getId()).isEqualTo(USER_ID);
            assertThat(user.getAddress().getStreet()).isEqualTo("Rua A");
            assertThat(user.getUserType().getName()).isEqualTo("OWNER");

            UserResponse resp = UserMapper.toAdminResponse(user);
            assertThat(resp.login()).isEqualTo(LOGIN);
            assertThat(resp.address().street()).isEqualTo("Rua A");
            assertThat(resp.userType().name()).isEqualTo("OWNER");
        }

        @Test
        void toEntity_fromCreateUserRequest_encodePassword_false() {
            AddressRequest req = makeAddressReq();
            CreateUserRequest cReq = new CreateUserRequest(NAME, EMAIL, LOGIN, req, PASSWORD, "OWNER");
            UserType type = makeTypeEntity(2L, "OWNER");

            User user = UserMapper.toEntity(cReq, type, false);
            assertThat(user.getId()).isNull();
            assertThat(user.getName()).isEqualTo(NAME);
            assertThat(user.getPassword()).isEqualTo(PASSWORD);
        }

        @Test
        void toEntity_adminRequest_encodePassword_true_matchesBCrypt() {
            AdminCreateUserRequest aReq = new AdminCreateUserRequest(NAME, EMAIL, LOGIN, makeAddressReq(), PASSWORD, 2L);
            UserType type = makeTypeEntity(3L, "OWNER");

            User user = UserMapper.toEntity(aReq, type, true);
            assertThat(user.getId()).isNull();
            assertThat(user.getName()).isEqualTo(NAME);
            assertThat(user.getUserType().getName()).isEqualTo("OWNER");
            assertThat(user.getPassword()).isNotEqualTo(PASSWORD);
            assertThat(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                    .matches(PASSWORD, user.getPassword()))
                    .isTrue();
        }
    }

    @Nested
    class ValidationTests {
        @Test
        void invalid_name_throws_exception() {
            CreateUserRequest bad = new CreateUserRequest("Al", EMAIL, LOGIN, null, PASSWORD, "OWNER");
            assertThatThrownBy(() ->
                    UserMapper.toEntity(bad, makeTypeEntity(1L, "OWNER"), false)
            ).isInstanceOf(com.example.tech_challenge.exceptions.BadArgumentException.class)
                    .hasMessageContaining("nome do usuário");
        }

        @Test
        void invalid_email_throws_exception() {
            CreateUserRequest bad = new CreateUserRequest(NAME, "not-an-email", LOGIN, null, PASSWORD, "OWNER");
            assertThatThrownBy(() ->
                    UserMapper.toEntity(bad, makeTypeEntity(1L, "OWNER"), false)
            ).hasMessageContaining("E-mail inválido");
        }

        @Test
        void invalid_password_and_login_and_userType_null_combines_messages() {
            AdminCreateUserRequest bad = new AdminCreateUserRequest(NAME, EMAIL, "", null, "", null);
            assertThatThrownBy(() ->
                    UserMapper.toEntity(bad, null, false)
            ).isInstanceOf(com.example.tech_challenge.exceptions.BadArgumentException.class)
                    .hasMessageContaining("possuir um login")
                    .hasMessageContaining("possuir uma senha")
                    .hasMessageContaining("possuir um tipo de usuário");
        }
    }
}
