package com.leadtek.tsu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.tsu.entity.Relief;

@Repository
public interface ReliefRepository extends JpaSpecificationExecutor<Relief>, PagingAndSortingRepository<Relief, String>,
		QuerydslPredicateExecutor<Relief> {

}
