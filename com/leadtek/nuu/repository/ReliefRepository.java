package com.leadtek.nuu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.Relief;

@Repository
public interface ReliefRepository
		extends JpaSpecificationExecutor<Relief>, PagingAndSortingRepository<Relief, String>, QuerydslPredicateExecutor<Relief> {

//	List<StudentInfo> findAll(Specification<StudentInfo> spec);

//	public default Specification<StudentInfo> sp(FilterStu filter) {
//		Specification<StudentInfo> specification = new Specification<StudentInfo>() {
//
//
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Predicate toPredicate(Root<StudentInfo> root, CriteriaQuery<?> query,
//					CriteriaBuilder cb) {
//				List<Predicate> predicate = new ArrayList<>();
//
//				if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
//					List<String> temp = filter.getEnrolleryear();
//					final Path<FilterStu> group = root.get("enrolleryear");
//					predicate.add(group.in(temp));
//				}
//
//				if (!StringUtils.isEmpty(filter.getColleage()) && !filter.getColleage().isEmpty()) {
//					List<String> temp = filter.getColleage();
//					final Path<FilterStu> group = root.get("colleage");
//					predicate.add(group.in(temp));
//				}
//
//				if (!StringUtils.isEmpty(filter.getDept()) && !filter.getDept().isEmpty()) {
//					List<String> temp = filter.getDept();
//					final Path<FilterStu> group = root.get("dept");
//					predicate.add(group.in(temp));
//				}
//
//				if (!StringUtils.isEmpty(filter.getDegree()) && !filter.getDegree().isEmpty()) {
//					List<String> temp = filter.getDegree();
//					final Path<FilterStu> group = root.get("degree");
//					predicate.add(group.in(temp));
//				}
//
//				if (!StringUtils.isEmpty(filter.getFromChannel()) && !filter.getFromChannel().isEmpty()) {
//					List<String> temp = filter.getFromChannel();
//					final Path<FilterStu> group = root.get("fromChannel");
//					predicate.add(group.in(temp));
//				}
//
////				if (!StringUtils.isEmpty(filter.getStatus()) && !filter.getStatus().isEmpty()) {
////					List<String> temp = filter.getStatus();
////					final Path<FilterStu> group = root.get("status");
////					predicate.add(group.in(temp));
////				}
////
////				if (!StringUtils.isEmpty(filter.getSex()) && !filter.getSex().isEmpty()) {
////					List<String> temp = filter.getSex();
////					final Path<FilterStu> group = root.get("sex");
////					predicate.add(group.in(temp));
////				}
//				
//
//				Predicate[] predicates = new Predicate[predicate.size()];
//				return cb.and(predicate.toArray(predicates));
//			}
//		};
//
//		return specification;
//	}





}
