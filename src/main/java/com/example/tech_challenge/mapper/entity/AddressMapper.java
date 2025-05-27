package com.example.tech_challenge.mapper.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import com.example.tech_challenge.exception.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class AddressMapper implements EntityMapper<Address> {

    private final Validator validator;

    public Address toAddressEntity(AddressRequest addressRequest) {
        Address address = new Address(addressRequest.state(), addressRequest.city(), addressRequest.street(),
                addressRequest.number(), addressRequest.zipCode(), addressRequest.aditionalInfo());

        this.validateEntity(address);

        return address;
    }

    public Address toAddressEntity(AddressRequest addressRequest, Long id) {
        Address address = new Address(id, addressRequest.state(), addressRequest.city(), addressRequest.street(),
                addressRequest.number(), addressRequest.zipCode(), addressRequest.aditionalInfo());

        this.validateEntity(address);

        return address;
    }

    @Override
    public void validateEntity(Address entity) throws ConstraintViolationException {
        Set<ConstraintViolation<Address>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            violations.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
    }
}
