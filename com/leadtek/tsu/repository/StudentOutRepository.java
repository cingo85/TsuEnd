package com.leadtek.tsu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.tsu.entity.SemesterOrder;
import com.leadtek.tsu.entity.StudentOut;



@Repository
public interface StudentOutRepository extends PagingAndSortingRepository<StudentOut, String> {

}
