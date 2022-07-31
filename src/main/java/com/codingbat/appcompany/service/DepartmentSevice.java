package com.codingbat.appcompany.service;

import com.codingbat.appcompany.entity.Company;
import com.codingbat.appcompany.entity.Department;
import com.codingbat.appcompany.payload.ApiResponse;
import com.codingbat.appcompany.payload.DepartmentDto;
import com.codingbat.appcompany.repository.CompanyRepository;
import com.codingbat.appcompany.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentSevice {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(),departmentDto.getCompanyId());
        if (exists) return new ApiResponse("Such a department exists",false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) return new ApiResponse("Company not found",false);

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);

        return new ApiResponse("Department seccessfully saved",true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) return new ApiResponse("No such department found",false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) return new ApiResponse("Company not found",false);

        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(),departmentDto.getCompanyId(),id);
        if(exists) return new ApiResponse("Such an department exists",false);

        Department department = optionalDepartment.get();
        department.setCompany(optionalCompany.get());
        department.setId(optionalDepartment.get().getId());
        departmentRepository.save(department);

        return new ApiResponse("Department seccessfully edited",true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) return new ApiResponse("Department not found",false);

        departmentRepository.delete(optionalDepartment.get());
        return new ApiResponse("Department seccessfully deleted",true);

    }
}
