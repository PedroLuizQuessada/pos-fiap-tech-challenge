package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.AddressHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    private final String validAditionalInfo2 = null;
    private final String invalidStateMessage = "O endereço deve possuir um estado.";
    private final String invalidCityMessage = "O endereço deve possuir uma cidade.";
    private final String invalidStreetMessage = "O endereço deve possuir uma rua.";
    private final String invalidNumberMessage = "O endereço deve possuir um número.";
    private final String invalidZipCodeMessage = "O endereço deve possuir um CEP.";

    @Test
    public void newAddressWithValidArgumentsShouldReturnSuccessTest() {
        Address address = new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO);

        assertNotNull(address);
        assertEquals(VALID_ID, address.getId());
        assertEquals(VALID_STATE, address.getState());
        assertEquals(VALID_CITY, address.getCity());
        assertEquals(VALID_STREET, address.getStreet());
        assertEquals(VALID_NUMBER, address.getNumber());
        assertEquals(VALID_ZIP_CODE, address.getZipCode());
        assertEquals(VALID_ADITIONAL_INFO, address.getAditionalInfo());
    }

    @Test
    public void newAddressWithNullStateShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, null, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStateMessage);
    }

    @Test
    public void newAddressWithEmptyStateShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, "", VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStateMessage);
    }

    @Test
    public void newAddressWithTooLongStateShouldThrowExceptionTest() {
        String invalidStateTooLong = "Estado muito longo, Estado muito longo, Estado muito longo, Estado muito longo, Estado muito longo, ";
        String invalidStateTooLongMessage = "O estado do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, invalidStateTooLong, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStateTooLongMessage);
    }

    @Test
    public void newAddressWithNullCityShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, null, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidCityMessage);
    }

    @Test
    public void newAddressWithEmptyCityShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, "", VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidCityMessage);
    }

    @Test
    public void newAddressWithTooLongCityShouldThrowExceptionTest() {
        String invalidCityTooLong = "Cidade muito longo, Cidade muito longo, Cidade muito longo, Cidade muito longo, ";
        String invalidCityTooLongMessage = "A cidade do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, invalidCityTooLong, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidCityTooLongMessage);
    }

    @Test
    public void newAddressWithNullStreetShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, null, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidNumberMessage);
    }

    @Test
    public void newAddressWithEmptyStreetShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, "", VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStreetMessage);
    }

    @Test
    public void newAddressWithTooLongStreetShouldThrowExceptionTest() {
        String invalidStreetTooLong = "Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, Rua muito longa, ";
        String invalidStreetTooLongMessage = "A rua do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, invalidStreetTooLong, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStreetTooLongMessage);
    }

    @Test
    public void newAddressWithNullNumberShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, null, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidStreetMessage);
    }

    @Test
    public void newAddressWithEmptyNumberShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, "", VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidNumberMessage);
    }

    @Test
    public void newAddressWithTooLongNumberShouldThrowExceptionTest() {
        String invalidNumberTooLong = "Número muito longo, Número muito longo, Número muito longo, Número muito longo, Número muito longo, ";
        String invalidNumberTooLongMessage = "O número do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, invalidNumberTooLong, VALID_ZIP_CODE, VALID_ADITIONAL_INFO), invalidNumberTooLongMessage);
    }

    @Test
    public void newAddressWithNullZipCodeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, null, VALID_ADITIONAL_INFO), invalidZipCodeMessage);
    }

    @Test
    public void newAddressWithEmptyZipCodeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, "", validAditionalInfo2), invalidZipCodeMessage);
    }

    @Test
    public void newAddressWithTooLongZipCodeShouldThrowExceptionTest() {
        String invalidZipCodeTooLong = "CEP muito longo, CEP muito longo, CEP muito longo, CEP muito longo, CEP muito longo, ";
        String invalidZipCodeTooLongMessage = "O CEP do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, invalidZipCodeTooLong, validAditionalInfo2), invalidZipCodeTooLongMessage);
    }

    @Test
    public void newAddressWithTooLongAditionalInfoShouldThrowExceptionTest() {
        String invalidAditionalInfoTooLong = "Complemento muito longo, Complemento muito longo, Complemento muito longo, Complemento muito longo, ";
        String invalidAditionalInfoTooLongMessage = "O complemento do endereço deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, invalidAditionalInfoTooLong), invalidAditionalInfoTooLongMessage);
    }
}
