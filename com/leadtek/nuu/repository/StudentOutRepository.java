package com.leadtek.nuu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.SemesterOrder;
import com.leadtek.nuu.entity.StudentOut;



@Repository
public interface StudentOutRepository extends PagingAndSortingRepository<StudentOut, String> {

}
