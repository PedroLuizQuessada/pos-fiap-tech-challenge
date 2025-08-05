package com.example.tech_challenge.mappers;

import static org.assertj.core.api.Assertions.*;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.dtos.responses.AddressResponse;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.exceptions.BadArgumentException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class AddressMapperTest {

    // Exemplos válidos usados várias vezes
    private static final Long TEST_ID        = 42L;
    private static final String STATE        = "SP";
    private static final String CITY         = "São Paulo";
    private static final String STREET       = "Av. Paulista";
    private static final String NUMBER       = "123";
    private static final String ZIP          = "01310-000";
    private static final String COMPLEMENT   = "Apto 101";

    private static AddressDto makeDto(Long id) {
        return new AddressDto(id, STATE, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
    }

    private static AddressRequest makeReq() {
        return new AddressRequest(STATE, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
    }

    @Nested
    class MappingTests {
        @Test
        void toEntity_fromDto_hasAllFields() {
            AddressDto dto = makeDto(TEST_ID);
            Address ent = AddressMapper.toEntity(dto);
            assertThat(ent.getId()).isEqualTo(TEST_ID);
            assertThat(ent.getState()).isEqualTo(STATE);
            assertThat(ent.getCity()).isEqualTo(CITY);
            assertThat(ent.getStreet()).isEqualTo(STREET);
            assertThat(ent.getNumber()).isEqualTo(NUMBER);
            assertThat(ent.getZipCode()).isEqualTo(ZIP);
            assertThat(ent.getAditionalInfo()).isEqualTo(COMPLEMENT);
        }

        @Test
        void toEntity_fromRequest_withoutId() {
            AddressRequest req = makeReq();
            Address ent = AddressMapper.toEntity(req);
            assertThat(ent.getId()).isNull();
            assertThat(ent.getCity()).isEqualTo(CITY);
        }

        @Test
        void toEntity_fromRequest_withId() {
            AddressRequest req = makeReq();
            Address ent = AddressMapper.toEntity(req, TEST_ID);
            assertThat(ent.getId()).isEqualTo(TEST_ID);
            assertThat(ent.getStreet()).isEqualTo(STREET);
        }

        @Test
        void toDto_fromEntity() {
            Address ent = new Address(TEST_ID, STATE, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
            AddressDto dto = AddressMapper.toDto(ent);
            assertThat(dto.id()).isEqualTo(TEST_ID);
            assertThat(dto.city()).isEqualTo(CITY);
            assertThat(dto.aditionalInfo()).isEqualTo(COMPLEMENT);
        }

        @Test
        void toDto_fromRequest() {
            AddressRequest req = makeReq();
            AddressDto dto = AddressMapper.toDto(req);
            assertThat(dto.id()).isNull();
            assertThat(dto.state()).isEqualTo(STATE);
            assertThat(dto.zipCode()).isEqualTo(ZIP);
        }

        @Test
        void toResponse_withoutAdminId() {
            Address ent = new Address(TEST_ID, STATE, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
            AddressResponse resp = AddressMapper.toResponse(ent);
            assertThat(resp.id()).isNull();
            assertThat(resp.city()).isEqualTo(CITY);
        }

        @Test
        void toAdminResponse_withId() {
            Address ent = new Address(TEST_ID, STATE, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
            AddressResponse resp = AddressMapper.toAdminResponse(ent);
            assertThat(resp.id()).isEqualTo(TEST_ID);
            assertThat(resp.state()).isEqualTo(STATE);
        }
    }

    @Nested
    class ValidationTests {
        @Test
        void invalid_nullState_throws() {
            AddressRequest req = new AddressRequest(null, CITY, STREET, NUMBER, ZIP, COMPLEMENT);
            assertThatThrownBy(() -> AddressMapper.toEntity(req))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("estado");
        }

        @Test
        void invalid_longCity_throws() {
            String longCity = IntStream.range(0, 50).mapToObj(i -> "X").reduce("", String::concat);
            AddressRequest req = new AddressRequest(STATE, longCity, STREET, NUMBER, ZIP, COMPLEMENT);
            assertThatThrownBy(() -> AddressMapper.toEntity(req))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("cidade");
        }

        @Test
        void invalid_nullZip_throws() {
            AddressRequest req = new AddressRequest(STATE, CITY, STREET, NUMBER, null, COMPLEMENT);
            assertThatThrownBy(() -> AddressMapper.toEntity(req))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("CEP");
        }

        @Test
        void invalid_complementTooLong_throws() {
            String longComp = IntStream.range(0, 46).mapToObj(i -> "C").reduce("", String::concat);
            AddressRequest req = new AddressRequest(STATE, CITY, STREET, NUMBER, ZIP, longComp);
            assertThatThrownBy(() -> AddressMapper.toEntity(req))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("complemento");
        }
    }
}
