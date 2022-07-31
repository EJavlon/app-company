package com.codingbat.appcompany.service;

import com.codingbat.appcompany.entity.Address;
import com.codingbat.appcompany.entity.Company;
import com.codingbat.appcompany.payload.ApiResponse;
import com.codingbat.appcompany.payload.CompanyDto;
import com.codingbat.appcompany.repository.AddressRepository;
import com.codingbat.appcompany.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanySevice {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists) return new ApiResponse("Such a company exists",false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()) return new ApiResponse("Address not found",false);

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);

        return new ApiResponse("Company seccessfully saved",true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) return new ApiResponse("No such company found",false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()) return new ApiResponse("Address not found",false);

        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(),id);
        if(exists) return new ApiResponse("Such an company exists",false);

        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);

        return new ApiResponse("Company seccessfully edited",true);
    }

    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) return new ApiResponse("Company not found",false);

        companyRepository.deleteById(id);
        return new ApiResponse("Company seccessfully deleted",true);
    }
}
