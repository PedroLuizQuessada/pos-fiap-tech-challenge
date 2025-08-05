package com.example.tech_challenge.mappers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.BadArgumentException;

class UserTypeMapperTest {

    private static final Long ID = 77L;
    private static final String NAME = "OWNER";

    @Nested
    class MappingTests {

        @Test
        void toEntity_fromDto_success() {
            var dto = new UserTypeDto(ID, NAME);
            UserType ent = UserTypeMapper.toEntity(dto);

            assertThat(ent.getId()).isEqualTo(ID);
            assertThat(ent.getName()).isEqualTo(NAME);
        }

        @Test
        void toEntity_fromRequest_success() {
            var req = new UserTypeRequest(NAME);
            UserType ent = UserTypeMapper.toEntity(req);

            assertThat(ent.getId()).isNull();
            assertThat(ent.getName()).isEqualTo(NAME);
        }

        @Test
        void toDto_fromEntity_success() {
            UserType ent = new UserType(ID, NAME);
            var dto = UserTypeMapper.toDto(ent);

            assertThat(dto.id()).isEqualTo(ID);
            assertThat(dto.name()).isEqualTo(NAME);
        }

        @Test
        void toResponse_withoutAdmin_hasNullId() {
            UserType ent = new UserType(ID, NAME);
            UserTypeResponse resp = UserTypeMapper.toResponse(ent);

            assertThat(resp.id()).isNull();
            assertThat(resp.name()).isEqualTo(NAME);
        }

        @Test
        void toAdminResponse_containsId() {
            UserType ent = new UserType(ID, NAME);
            UserTypeResponse resp = UserTypeMapper.toAdminResponse(ent);

            assertThat(resp.id()).isEqualTo(ID);
            assertThat(resp.name()).isEqualTo(NAME);
        }
    }

    @Nested
    class ValidationTests {

        @Test
        void constructor_nullName_throws() {
            assertThatThrownBy(() -> new UserType(ID, null))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("tipo de usuário deve possuir um nome");
        }

        @Test
        void constructor_emptyName_throws() {
            assertThatThrownBy(() -> new UserType(ID, ""))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("deve possuir um nome");
        }

        @Test
        void constructor_nameTooLong_throws() {
            String longName = "X".repeat(46);
            assertThatThrownBy(() -> new UserType(ID, longName))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("até 45 caracteres");
        }
    }
}
