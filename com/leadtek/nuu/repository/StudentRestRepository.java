package com.leadtek.nuu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.SemesterOrder;
import com.leadtek.nuu.entity.StudentRest;



@Repository
public interface StudentRestRepository extends PagingAndSortingRepository<StudentRest, String> {

}
