package com.leadtek.tsu.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.tsu.entity.SemesterOrder;
import com.leadtek.tsu.entity.StudentInfo;



@Repository
public interface SemesterOrderRepository extends PagingAndSortingRepository<SemesterOrder, String>, QuerydslPredicateExecutor<SemesterOrder> {

}
