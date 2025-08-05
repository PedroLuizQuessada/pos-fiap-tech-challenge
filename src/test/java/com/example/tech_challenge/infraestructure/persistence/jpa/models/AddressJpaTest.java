package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.AddressHelper.*;
import static com.example.tech_challenge.helper.AddressHelper.VALID_ZIP_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressJpaTest {

    @Test
    public void newAddressJpaWithValidArgumentsShouldReturnSuccessTest() {
        AddressJpa addressJpa = new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO);

        assertNotNull(addressJpa);
        assertEquals(VALID_ID, addressJpa.getId());
        assertEquals(VALID_STATE, addressJpa.getState());
        assertEquals(VALID_CITY, addressJpa.getCity());
        assertEquals(VALID_STREET, addressJpa.getStreet());
        assertEquals(VALID_NUMBER, addressJpa.getNumber());
        assertEquals(VALID_ZIP_CODE, addressJpa.getZipCode());
        assertEquals(VALID_ADITIONAL_INFO, addressJpa.getAditionalInfo());
    }

    @Test
    public void newAddressJpaWithNoArgumentsShouldReturnSuccessTest() {
        AddressJpa addressJpaEmpty = new AddressJpa();

        assertNotNull(addressJpaEmpty);
    }

    @Test
    public void newAddressJpaWithNullStateShouldThrowExceptionTest() {
        String invalidStateMessage = "O endereço deve possuir um estado para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, null, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStateMessage);
    }

    @Test
    public void newAddressJpaWithTooLongStateShouldThrowExceptionTest() {
        String invalidStateTooLong = "Estado muito longo, Estado muito longo, Estado muito longo, Estado muito longo, Estado muito longo, ";
        String invalidStateTooLongMessage = "O estado do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, invalidStateTooLong, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStateTooLongMessage);
    }

    @Test
    public void newAddressJpaWithNullCityShouldThrowExceptionTest() {
        String invalidCityMessage = "O endereço deve possuir uma cidade para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, null, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidCityMessage);
    }

    @Test
    public void newAddressJpaWithTooLongCityShouldThrowExceptionTest() {
        String invalidCityTooLong = "Cidade muito longo, Cidade muito longo, Cidade muito longo, Cidade muito longo, ";
        String invalidCityTooLongMessage = "A cidade do endereço deve possuir até 45 caracteres para ser armazenada no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, invalidCityTooLong, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidCityTooLongMessage);
    }

    @Test
    public void newAddressJpaWithNullStreetShouldThrowExceptionTest() {
        String invalidStreetMessage = "O endereço deve possuir uma rua para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, null, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStreetMessage);
    }

    @Test
    public void newAddressJpaWithTooLongStreetShouldThrowExceptionTest() {
        String invalidStreetTooLong = "Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, ";
        String invalidStreetTooLongMessage = "A rua do endereço deve possuir até 45 caracteres para ser armazenada no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, invalidStreetTooLong, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStreetTooLongMessage);
    }

    @Test
    public void newAddressJpaWithNullNumberShouldThrowExceptionTest() {
        String invalidNumberMessage = "O endereço deve possuir um número para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, null, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidNumberMessage);
    }

    @Test
    public void newAddressJpaWithTooLongNumberShouldThrowExceptionTest() {
        String invalidNumberTooLong = "Número muito longo, Número muito longo, Número muito longo, Número muito longo, Número muito longo, ";
        String invalidNumberTooLongMessage = "O número do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, invalidNumberTooLong, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidNumberTooLongMessage);
    }

    @Test
    public void newAddressJpaWithNullZipCodeShouldThrowExceptionTest() {
        String invalidZipCodeMessage = "O endereço deve possuir um CEP para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, null, VALID_ADITIONAL_INFO), invalidZipCodeMessage);
    }

    @Test
    public void newAddressJpaWithTooLongZipCodeShouldThrowExceptionTest() {
        String invalidZipCodeTooLong = "CEP muito longo, CEP muito longo, CEP muito longo, CEP muito longo, CEP muito longo, ";
        String invalidZipCodeTooLongMessage = "O CEP do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, invalidZipCodeTooLong, null), invalidZipCodeTooLongMessage);
    }

    @Test
    public void newAddressJpaWithTooLongAditionalInfoShouldThrowExceptionTest() {
        String invalidAditionalInfoTooLong = "Complemento muito longo, Complemento muito longo, Complemento muito longo, Complemento muito longo, ";
        String invalidAditionalInfoTooLongMessage = "O complemento do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, invalidAditionalInfoTooLong), invalidAditionalInfoTooLongMessage);
    }

}
