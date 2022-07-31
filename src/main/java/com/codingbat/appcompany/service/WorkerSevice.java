package com.codingbat.appcompany.service;

import com.codingbat.appcompany.entity.Address;
import com.codingbat.appcompany.entity.Department;
import com.codingbat.appcompany.entity.Worker;
import com.codingbat.appcompany.payload.ApiResponse;
import com.codingbat.appcompany.payload.WorkerDto;
import com.codingbat.appcompany.repository.AddressRepository;
import com.codingbat.appcompany.repository.DepartmentRepository;
import com.codingbat.appcompany.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerSevice {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DepartmentRepository departmentRepository;


    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorker(Integer id) {
        return  workerRepository.findById(id).orElse(null);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()) return new ApiResponse("Address not found",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) return new ApiResponse("Department not found",false);

        boolean exists = workerRepository.existsByNameAndAddressId(workerDto.getName(),workerDto.getAddressId());
        if (exists) return new ApiResponse("Such a worker exists",false);

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(optionalAddress.get());
        workerRepository.save(worker);

        return new ApiResponse("Worker seccessfully saved",true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) return new ApiResponse("No such worker found",false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()) return new ApiResponse("Address not found",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) return new ApiResponse("Department not found",false);

        boolean exists = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(),id);
        if (exists) return new ApiResponse("Such a worker exists",false);

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(optionalAddress.get());
        workerRepository.save(worker);

        return new ApiResponse("Worker seccessfully edited",true);
    }

    public ApiResponse deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) return new ApiResponse("Worker not found",false);

        workerRepository.delete(optionalWorker.get());
        return new ApiResponse("Worker seccessfully deleted",true);

    }
}
