package com.leadtek.nuu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leadtek.nuu.entity.TeacherProject;

public interface TeacherProjectRepository extends JpaSpecificationExecutor<TeacherProject>, PagingAndSortingRepository<TeacherProject, String>, QuerydslPredicateExecutor<TeacherProject>{

}
