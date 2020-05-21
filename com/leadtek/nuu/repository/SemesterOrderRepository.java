package com.leadtek.nuu.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.SemesterOrder;
import com.leadtek.nuu.entity.StudentInfo;



@Repository
public interface SemesterOrderRepository extends PagingAndSortingRepository<SemesterOrder, String>, QuerydslPredicateExecutor<SemesterOrder> {

}
