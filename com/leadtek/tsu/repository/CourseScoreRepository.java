package com.leadtek.tsu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.tsu.entity.CourseInfo;



@Repository
public interface CourseScoreRepository extends JpaSpecificationExecutor<CourseInfo>,PagingAndSortingRepository<CourseInfo, String> {

}
