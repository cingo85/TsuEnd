package com.leadtek.tsu.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.leadtek.tsu.entity.FilterStu;
import com.leadtek.tsu.entity.QSemesterOrder;
import com.leadtek.tsu.entity.QTeacherProject;
import com.leadtek.tsu.repository.CourseScoreRepository;
import com.leadtek.tsu.repository.ReliefRepository;
import com.leadtek.tsu.repository.ScholarshipRepository;
import com.leadtek.tsu.repository.SemesterOrderRepository;
import com.leadtek.tsu.repository.StudentInfoRepository;
import com.leadtek.tsu.repository.StudentOutRepository;
import com.leadtek.tsu.repository.StudentRestRepository;
import com.leadtek.tsu.repository.TeacherProjectRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;

@Service
public class TeacherProjectService extends StudentBasicService {

	public TeacherProjectService(StudentInfoRepository studentInfoRepository,
			SemesterOrderRepository semesterOrderRepository, StudentRestRepository studentRestRepository,
			StudentOutRepository studentOutRepository, CourseScoreRepository courseScoreRepository,
			TeacherProjectRepository teacherProjectRepository, ReliefRepository reliefRepository,
			ScholarshipRepository scholarshipRepository) {
		super(studentInfoRepository, semesterOrderRepository, studentRestRepository, studentOutRepository,
				courseScoreRepository, teacherProjectRepository, reliefRepository, scholarshipRepository);
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> stuTeacherPaper(FilterStu filter) {
		QTeacherProject item = QTeacherProject.teacherProject;
		Path<Integer> c_count = Expressions.numberPath(Integer.class, "c");
		Path<Integer> d_count = Expressions.numberPath(Integer.class, "d");
		Path<Integer> e_count = Expressions.numberPath(Integer.class, "e");

		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.syear, item.project_c_times.sum().as(c_count), item.project_d_times.sum().as(d_count),
						item.project_e_times.sum().as(e_count))
				.where(builder).groupBy(item.syear).orderBy(item.syear.asc()).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.syear).toString());
					map.put("total", (Integer) tuple.get(c_count));
					map.put("本年教師期刊論文次數", tuple.get(d_count));
					map.put("本年教師研討會論文次數", tuple.get(e_count));
					return map;
				}).collect(Collectors.toList());

		return result;
	}

	public List<Map<String, Object>> stuTeacherService(FilterStu filter) {
		QTeacherProject item = QTeacherProject.teacherProject;
		Path<Integer> a_count = Expressions.numberPath(Integer.class, "a");
		Path<Integer> b_count = Expressions.numberPath(Integer.class, "b");
		Path<Integer> c_count = Expressions.numberPath(Integer.class, "c");
		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.syear, item.project_c_times.sum().as(c_count), item.project_a_times.sum().as(a_count),
						item.project_b_times.sum().as(b_count))
				.where(builder).groupBy(item.syear).orderBy(item.syear.asc()).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.syear).toString());
					map.put("total", (Integer) tuple.get(c_count));
					map.put("本年教師專業技術服務次數", tuple.get(a_count));
					map.put("本年教師學術活動次數", tuple.get(b_count));
					return map;
				}).collect(Collectors.toList());

		return result;
	}

	public List<Map<String, Object>> stuTeacherPatent(FilterStu filter) {
		QTeacherProject item = QTeacherProject.teacherProject;
		Path<Integer> c_count = Expressions.numberPath(Integer.class, "c");
		Path<Integer> f_count = Expressions.numberPath(Integer.class, "f");
		Path<Integer> g_count = Expressions.numberPath(Integer.class, "g");
		Path<Integer> h_count = Expressions.numberPath(Integer.class, "h");
		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.syear, item.project_c_times.sum().as(c_count), item.project_a_times.sum().as(f_count),
						item.project_b_times.sum().as(g_count), item.project_b_times.sum().as(h_count))
				.where(builder).groupBy(item.syear).orderBy(item.syear.asc()).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.syear).toString());
					map.put("total", (Integer) tuple.get(c_count));
					map.put("本學年教師著作、專書次數", tuple.get(f_count));
					map.put("本學年教師專利次數", tuple.get(g_count));
					map.put("本學年教師榮獲獎項次數", tuple.get(h_count));
					return map;
				}).collect(Collectors.toList());

		return result;
	}

	public Map<String, Object> teacherStatus(FilterStu filter) {
		QTeacherProject item = QTeacherProject.teacherProject;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem2(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.syear, item.teacherstatus, item.emp_id.countDistinct().as(count)).where(builder)
				.groupBy(item.syear, item.teacherstatus).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.syear));
					map.put("status", tuple.get(item.teacherstatus));
					map.put("cnt", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式

		Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

		for (Map<String, Object> reitem : result) {

			if (resu.get(reitem.get("year")) == null) {
				resu.put((String) reitem.get("year"), new HashMap<String, Integer>());
				if (resu.get((String) reitem.get("year")).get(reitem.get("status")) == null) {
					resu.get((String) reitem.get("year")).put((String) reitem.get("status"),
							(Integer) reitem.get("cnt"));
				}
			} else {
				if (resu.get((String) reitem.get("year")).get(reitem.get("status")) == null) {
					resu.get((String) reitem.get("year")).put((String) reitem.get("status"),
							(Integer) reitem.get("cnt"));
				}
			}
		}

		ArrayList<Map<String, Integer>> result_array = new ArrayList<>();

		Map<String, Object> finresult = new HashMap<String, Object>();
		resu.forEach(new BiConsumer<String, Map<String, Integer>>() {

			@Override
			public void accept(String t, Map<String, Integer> u) {
				u.put("year", Integer.valueOf(t));
				result_array.add(u);
			}
		});
		finresult.put("lineData", result_array);
		return finresult;
	}

	public Map<String, Object> studentTeacher(FilterStu filter) {

		QTeacherProject item = QTeacherProject.teacherProject;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem2(item, filter);

		List<Map<String, Object>> tearesult = queryFactory.selectFrom(item)
				.select(item.syear, item.emp_id.countDistinct().as(count)).where(builder).groupBy(item.syear).fetch()
				.stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item.syear));
					map.put("cnt", tuple.get(count).doubleValue());
					return map;
				}).collect(Collectors.toList());

		QSemesterOrder item2 = QSemesterOrder.semesterOrder;
		NumberPath<Long> count2 = Expressions.numberPath(Long.class, "c");
		List<Map<String, Object>> sturesult2 = queryFactory.selectFrom(item2)
				.select(item2.yms_year, item2.SID.countDistinct().as(count2)).where(builder).groupBy(item2.yms_year)
				.fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("year", tuple.get(item2.yms_year));
					map.put("cnt", tuple.get(count2).doubleValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式

//		List<Map<String,Object>> finresult = new ArrayList<>();

		Map<String, Map<String, Object>> resu = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> tea_temp : tearesult) {
			if (resu.get(tea_temp.get("year")) == null) {
				resu.put((String) tea_temp.get("year"), new HashMap<String, Object>());
				if (resu.get((String) tea_temp.get("year")).get("teacnt") == null) {
					resu.get((String) tea_temp.get("year")).put("teacnt", (Double) tea_temp.get("cnt"));
				}
			} else {
				if (resu.get((String) tea_temp.get("year")).get("teacnt") == null) {
					resu.get((String) tea_temp.get("year")).put("teacnt", (Double) tea_temp.get("cnt"));
				}
			}
		}

		for (Map<String, Object> stu_temp : sturesult2) {
			if (resu.get(stu_temp.get("year")) != null) {
				if (resu.get((String) stu_temp.get("year")).get("stucnt") == null) {
					resu.get((String) stu_temp.get("year")).put("stucnt", (Double) stu_temp.get("cnt"));
				}
			}
		}

		ArrayList<Map<String, Object>> result_array = new ArrayList<>();

		Map<String, Object> finresult = new HashMap<String, Object>();
		resu.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				u.put("year", Integer.valueOf(t));

				double te = (double) u.get("teacnt");
				double st = (double) u.get("stucnt");
				double d1 = 0.0;

				DecimalFormat df = new DecimalFormat("##.00");
				d1 = Double.parseDouble(df.format(st / te));

				u.put("percent", d1);

				result_array.add(u);
			}
		});
		finresult.put("lineData", result_array);

		return finresult;

	}

	public BooleanBuilder builditem(QTeacherProject item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		builder.and(item.teacherstatus.eq("專任"));

//		if (!StringUtils.isEmpty(filter.getColleage()) && !filter.getColleage().isEmpty()) {
//			builder.and(item.dpt_name.in(filter.colleage));
//		}
//
//		if (!StringUtils.isEmpty(filter.getDept()) && !filter.getDept().isEmpty()) {
//			builder.and(item.unt_name.in(filter.dept));
//		}

//		builder.and(item.dpt_name.notIn(""));
		return builder;
	}

	public BooleanBuilder builditem2(QTeacherProject item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

//		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
//			builder.and(item.std_year.in(filter.enrolleryear));
//		}
//
//		if (!StringUtils.isEmpty(filter.getColleage()) && !filter.getColleage().isEmpty()) {
//			builder.and(item.dpt_name.in(filter.colleage));
//		}
//
//		if (!StringUtils.isEmpty(filter.getDept()) && !filter.getDept().isEmpty()) {
//			builder.and(item.unt_name.in(filter.dept));
//		}
//
//		if (!StringUtils.isEmpty(filter.getDegree()) && !filter.getDegree().isEmpty()) {
//			builder.and(item.dgr_name.in(filter.degree));
//		}
//
//		if (!StringUtils.isEmpty(filter.getFromChannel()) && !filter.getFromChannel().isEmpty()) {
//			builder.and(item.st2_name.in(filter.fromChannel));
//		}
//
//		if (!StringUtils.isEmpty(filter.getSex()) && !filter.getSex().isEmpty()) {
//			builder.and(item.std_sex.in(filter.sex));
//		}
//		builder.and(item.dpt_name.notIn(""));
		return builder;
	}

}
