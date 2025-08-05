package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.AddressDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressGatewayTest {

    @Mock
    private AddressDataSource addressDataSource;

    @InjectMocks
    private AddressGateway addressGateway;

    @Test
    public void deleteWithValidArgumentsShouldReturnSuccess() {
        doNothing().when(addressDataSource).deleteAddress(any());

        addressGateway.delete(null);

        verify(addressDataSource, times(1)).deleteAddress(any());
    }
}
