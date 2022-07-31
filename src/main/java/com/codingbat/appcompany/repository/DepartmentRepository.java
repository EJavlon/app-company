package com.codingbat.appcompany.repository;

import com.codingbat.appcompany.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    boolean existsByNameAndCompanyId(String name, Integer company_id);

    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);

}
