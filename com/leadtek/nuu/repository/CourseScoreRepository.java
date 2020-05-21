package com.leadtek.nuu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.CourseInfo;



@Repository
public interface CourseScoreRepository extends JpaSpecificationExecutor<CourseInfo>,PagingAndSortingRepository<CourseInfo, String> {

}
