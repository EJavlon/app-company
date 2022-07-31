package com.codingbat.appcompany.service;

import com.codingbat.appcompany.entity.Address;
import com.codingbat.appcompany.payload.AddressDto;
import com.codingbat.appcompany.payload.ApiResponse;
import com.codingbat.appcompany.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressSevice {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddress(Integer id) {
        return addressRepository.findById(id).orElse(null);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndHauseNumber(addressDto.getStreet(), addressDto.getHauseNumber());
        if (exists) return new ApiResponse("Such an address exists",false);

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHauseNumber(addressDto.getHauseNumber());
        addressRepository.save(address);

        return new ApiResponse("Address seccessfully saved",true);
    }

    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) return new ApiResponse("No such address found",false);

        boolean exists = addressRepository.existsByStreetAndHauseNumberAndIdNot(addressDto.getStreet(), addressDto.getHauseNumber(), id);
        if(exists) return new ApiResponse("Such an address exists",false);

        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHauseNumber(addressDto.getHauseNumber());
        addressRepository.save(address);

        return new ApiResponse("Address seccessfully edited",true);
    }

    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address seccessfully edited",true);
        }catch (Exception e){
            return new ApiResponse("No such address found",false);
        }
    }
}
