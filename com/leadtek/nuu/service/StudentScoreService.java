package com.leadtek.nuu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leadtek.nuu.entity.FilterStu;
import com.leadtek.nuu.entity.QCourseInfo;
import com.leadtek.nuu.entity.QSemesterOrder;
import com.leadtek.nuu.repository.CourseScoreRepository;
import com.leadtek.nuu.repository.ReliefRepository;
import com.leadtek.nuu.repository.ScholarshipRepository;
import com.leadtek.nuu.repository.SemesterOrderRepository;
import com.leadtek.nuu.repository.StudentInfoRepository;
import com.leadtek.nuu.repository.StudentOutRepository;
import com.leadtek.nuu.repository.StudentRestRepository;
import com.leadtek.nuu.repository.TeacherProjectRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;

@Service
public class StudentScoreService extends StudentBasicService {

	public StudentScoreService(StudentInfoRepository studentInfoRepository,
			SemesterOrderRepository semesterOrderRepository, StudentRestRepository studentRestRepository,
			StudentOutRepository studentOutRepository, CourseScoreRepository courseScoreRepository,
			TeacherProjectRepository teacherProjectRepository,ReliefRepository reliefRepository,ScholarshipRepository scholarshipRepository) {
		super(studentInfoRepository, semesterOrderRepository, studentRestRepository, studentOutRepository,
				courseScoreRepository, teacherProjectRepository,reliefRepository, scholarshipRepository);
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> scoreByDept(FilterStu filter) {
		QSemesterOrder item = QSemesterOrder.semesterOrder;

		BooleanBuilder builder = builditem2(item, filter);

		List<Map<String, Object>> result_temp = queryFactory.selectFrom(item)
				.select(item.unt_name, item.SID, item.src_tot_avg).where(builder)
				.groupBy(item.unt_name, item.SID, item.src_tot_avg).orderBy(item.unt_name.asc()).fetch().stream()
				.map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("unt_name", tuple.get(item.unt_name).toString());
					map.put("src_tot_avg", tuple.get(item.src_tot_avg));
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, List<Double>> resu = new LinkedHashMap<String, List<Double>>();

		for (Map<String, Object> reitem : result_temp) {
			if (resu.get((String) reitem.get("unt_name")) == null) {
				resu.put((String) reitem.get("unt_name"), new ArrayList<Double>());
				resu.get((String) reitem.get("unt_name")).add((Double) reitem.get("src_tot_avg"));

			} else {
				resu.get((String) reitem.get("unt_name")).add((Double) reitem.get("src_tot_avg"));
			}
		}

		List<String> dept = new ArrayList<String>();
		List<Object> deptScore = new ArrayList<>();
		resu.forEach(new BiConsumer<String, List<Double>>() {

			@Override
			public void accept(String t, List<Double> u) {

				dept.add(t);
				deptScore.add(boxplot(u));

			}
		});

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("categories", dept);
		result.put("data", deptScore);
		return result;
	}

	public Map<String, Object> scoreByChannel(FilterStu filter) {
		QSemesterOrder item = QSemesterOrder.semesterOrder;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem2(item, filter);
		List<Map<String, Object>> result_temp = queryFactory.selectFrom(item)
				.select(item.st2_name, item.SID, item.src_tot_avg).where(builder)
				.groupBy(item.st2_name, item.SID, item.src_tot_avg).orderBy(item.st2_name.asc()).fetch().stream()
				.map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("st2_name", tuple.get(item.st2_name).toString());
					map.put("src_tot_avg", Double.valueOf(tuple.get(item.src_tot_avg)));
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, List<Double>> resu = new LinkedHashMap<String, List<Double>>();

		for (Map<String, Object> reitem : result_temp) {
			if (resu.get((String) reitem.get("st2_name")) == null) {
				resu.put((String) reitem.get("st2_name"), new ArrayList<Double>());
				resu.get((String) reitem.get("st2_name")).add((Double) reitem.get("src_tot_avg"));
			} else {
				resu.get((String) reitem.get("st2_name")).add((Double) reitem.get("src_tot_avg"));
			}
		}

		List<String> dept = new ArrayList<String>();
		List<Object> deptScore = new ArrayList<>();
		resu.forEach(new BiConsumer<String, List<Double>>() {

			@Override
			public void accept(String t, List<Double> u) {

				dept.add(t);
				deptScore.add(boxplot(u));

			}
		});

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("categories", dept);
		result.put("data", deptScore);
		return result;
	}

	public List<Map<String, Object>> scoreLast10(FilterStu filter) {
		QCourseInfo item = QCourseInfo.courseInfo;

		BooleanBuilder builder = builditem5(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.sub_name, item.score_avg, item.dpt_name, item.unt_name,
						item.dgr_name,item.acptcnt,item.emp_poaddt_list,item.tit_name_list)
				.where(builder).groupBy(item.yms_year, item.yms_sms, item.sub_name, item.score_avg, item.dpt_name,
						item.unt_name, item.dgr_name,item.acptcnt,item.emp_poaddt_list,item.tit_name_list)
				.orderBy(item.score_avg.asc()).limit(10).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.yms_year).toString());
					map.put("sms", tuple.get(item.yms_sms).toString());
					map.put("sub_name", tuple.get(item.sub_name));
					map.put("score_avg", tuple.get(item.score_avg));
					map.put("dpt_name", tuple.get(item.dpt_name));
					map.put("unt_name", tuple.get(item.unt_name));
					map.put("dgr_name", tuple.get(item.dgr_name));
					map.put("acptcnt", tuple.get(item.acptcnt));
					map.put("tit_name_list", tuple.get(item.tit_name_list));
					map.put("emp_poaddt_list", tuple.get(item.emp_poaddt_list));
					return map;
				}).collect(Collectors.toList());
		;

		return result;
	}

	public List<Map<String, Object>> scoreTop10(FilterStu filter) {
		QCourseInfo item = QCourseInfo.courseInfo;

		BooleanBuilder builder = builditem5(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.sub_name, item.score_avg, item.dpt_name, item.unt_name,
						item.dgr_name,item.acptcnt,item.emp_poaddt_list,item.tit_name_list)
				.where(builder).groupBy(item.yms_year, item.yms_sms, item.sub_name, item.score_avg, item.dpt_name,
						item.unt_name, item.dgr_name,item.acptcnt,item.emp_poaddt_list,item.tit_name_list)
				.orderBy(item.score_avg.desc()).limit(10).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.yms_year).toString());
					map.put("sms", tuple.get(item.yms_sms).toString());
					map.put("sub_name", tuple.get(item.sub_name));
					map.put("score_avg", tuple.get(item.score_avg));
					map.put("dpt_name", tuple.get(item.dpt_name));
					map.put("unt_name", tuple.get(item.unt_name));
					map.put("dgr_name", tuple.get(item.dgr_name));
					map.put("acptcnt", tuple.get(item.acptcnt));
					map.put("tit_name_list", tuple.get(item.tit_name_list));
					map.put("emp_poaddt_list", tuple.get(item.emp_poaddt_list));
					return map;
				}).collect(Collectors.toList());

		return result;
	}

	public BooleanBuilder builditem5(QCourseInfo item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

	
		if (!StringUtils.isEmpty(filter.getYms_year()) && !filter.getYms_year().isEmpty()) {
			builder.and(item.yms_year.eq(filter.yms_year));
		}else {
			builder.and(item.yms_year.eq("107"));
		}
		
		if (!StringUtils.isEmpty(filter.getYms_sms()) && !filter.getYms_sms().isEmpty()) {
			builder.and(item.yms_sms.eq(filter.yms_sms));
		}else {
			builder.and(item.yms_sms.eq("1"));
		}
		
		if (!StringUtils.isEmpty(filter.getColleage()) && !filter.getColleage().isEmpty()) {
			builder.and(item.dpt_name.in(filter.colleage));
		}

		if (!StringUtils.isEmpty(filter.getDept()) && !filter.getDept().isEmpty()) {
			builder.and(item.unt_name.in(filter.dept));
		}

		if (!StringUtils.isEmpty(filter.getDegree()) && !filter.getDegree().isEmpty()) {
			builder.and(item.dgr_name.in(filter.degree));
		}
		
		if(!StringUtils.isEmpty(filter.getCrt_name()) && !filter.getCrt_name().isEmpty()) {
			builder.and(item.crt_name.eq(filter.getCrt_name()));
		}

		return builder;
	}

	// 箱型圖
	private List<Double> boxplot(List<Double> rate) {
		double j = 0;
		List<Double> temp = new ArrayList<Double>();
		if (rate.size() > 0) {
			int size = rate.size();
			Collections.sort(rate);
			if (size % 2 == 1) {
				j = rate.get((size - 1) / 2);
			} else {
				j = (rate.get(size / 2 - 1) + rate.get(size / 2) + 0.0) / 2;
			}

			int q1_temp = (int) Math.floor((size * 0.25));
			int q3_temp = (int) Math.floor((size * 0.75));
			double Q1 = rate.get(q1_temp);
			double Q3 = rate.get(q3_temp);

			Double max = Collections.max(rate);
			Double min = Collections.min(rate);

			temp.add(min);
			temp.add(Q1);
			temp.add(j);
			temp.add(Q3);
			temp.add(max);
		}

		return temp;
	}

}
