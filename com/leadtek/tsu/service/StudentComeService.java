package com.leadtek.tsu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leadtek.tsu.entity.FilterStu;
import com.leadtek.tsu.entity.QStudentInfo;
import com.leadtek.tsu.repository.CourseScoreRepository;
import com.leadtek.tsu.repository.ReliefRepository;
import com.leadtek.tsu.repository.ScholarshipRepository;
import com.leadtek.tsu.repository.SemesterOrderRepository;
import com.leadtek.tsu.repository.StudentInfoRepository;
import com.leadtek.tsu.repository.StudentOutRepository;
import com.leadtek.tsu.repository.StudentRestRepository;
import com.leadtek.tsu.repository.TeacherProjectRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;

@Service
public class StudentComeService extends StudentBasicService {

	public StudentComeService(StudentInfoRepository studentInfoRepository,
			SemesterOrderRepository semesterOrderRepository, StudentRestRepository studentRestRepository,
			StudentOutRepository studentOutRepository, CourseScoreRepository courseScoreRepository,
			TeacherProjectRepository teacherProjectRepository, ReliefRepository reliefRepository,
			ScholarshipRepository scholarshipRepository) {
		super(studentInfoRepository, semesterOrderRepository, studentRestRepository, studentOutRepository,
				courseScoreRepository, teacherProjectRepository, reliefRepository, scholarshipRepository);
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> stuComeSchool(FilterStu filter) {
		QStudentInfo item = QStudentInfo.studentInfo;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditemSchool(item, filter);
		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.std_xsh_name, item.lat, item.lng, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.std_xsh_name, item.lat, item.lng).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("name", tuple.get(item.std_xsh_name));
					map.put("lat", tuple.get(item.lat));
					map.put("lng", tuple.get(item.lng));
					map.put("z", tuple.get(count));
					return map;
				}).collect(Collectors.toList());

		return result;
	}

	public List<Map<String, Object>> stuComeDis(FilterStu filter) {
		QStudentInfo item = QStudentInfo.studentInfo;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.std_xsh_city, item.SID.countDistinct().as(count)).where(builder).groupBy(item.std_xsh_city)
				.fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("name", tuple.get(item.std_xsh_city));
					map.put("z", tuple.get(count));
					return map;
				}).collect(Collectors.toList());

		// 前端格式

		return result;
	}

	public Map<String, Object> stuComeChannel(FilterStu filter, String channel) {
		QStudentInfo item = QStudentInfo.studentInfo;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

		if ("default-default".equals(channel)) {
			BooleanBuilder builder = builditem2(item, filter, null);
			List<Map<String, Object>> resultby2n3 = queryFactory.selectFrom(item)
					.select(item.std_year, item.st2_name, item.SID.countDistinct().as(count)).where(builder)
					.groupBy(item.std_year, item.st2_name).fetch().stream().map(tuple -> {
						Map<String, Object> map = new LinkedHashMap<>();

							map.put("year", tuple.get(item.std_year).toString());
							map.put("st2_name", tuple.get(item.st2_name));
							map.put("count", tuple.get(count).intValue());
						
						return map;
					}).collect(Collectors.toList());

			// 前端格式
			Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

			for (Map<String, Object> reitem : resultby2n3) {

				if (resu.get(reitem.get("year")) == null) {
					resu.put((String) reitem.get("year"), new HashMap<String, Integer>());
					if (resu.get((String) reitem.get("year")).get(reitem.get("st2_name")) == null) {
						resu.get((String) reitem.get("year")).put((String) reitem.get("st2_name"),
								(Integer) reitem.get("count"));
					}
				} else {
					if (resu.get((String) reitem.get("year")).get(reitem.get("st2_name")) == null) {
						resu.get((String) reitem.get("year")).put((String) reitem.get("st2_name"),
								(Integer) reitem.get("count"));
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
		} else {

			String temp[] = channel.split("-");
			List<Integer> enrolleryear = new ArrayList<Integer>();
			enrolleryear.add(Integer.valueOf(temp[0]));
			filter.setEnrolleryear(enrolleryear);

			BooleanBuilder builder = builditem2(item, filter, temp[1]);

			List<Map<String, Object>> resultby2 = queryFactory.selectFrom(item)
					.select(item.std_year, item.st3_name, item.SID.countDistinct().as(count)).where(builder)
					.groupBy(item.std_year, item.st3_name).fetch().stream().map(tuple -> {
						Map<String, Object> map = new LinkedHashMap<>();
						map.put("year", tuple.get(item.std_year).toString());
						map.put("st3_name", tuple.get(item.st3_name));
						map.put("count", tuple.get(count).intValue());
						return map;
					}).collect(Collectors.toList());

			// 前端格式
			Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

			for (Map<String, Object> reitem : resultby2) {

				if (resu.get(reitem.get("year")) == null) {
					resu.put((String) reitem.get("year"), new HashMap<String, Integer>());
					if (resu.get((String) reitem.get("year")).get(reitem.get("st3_name")) == null) {
						resu.get((String) reitem.get("year")).put((String) reitem.get("st3_name"),
								(Integer) reitem.get("count"));
					}
				} else {
					if (resu.get((String) reitem.get("year")).get(reitem.get("st3_name")) == null) {
						resu.get((String) reitem.get("year")).put((String) reitem.get("st3_name"),
								(Integer) reitem.get("count"));
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

	}

	public BooleanBuilder builditem(QStudentInfo item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
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

		if (!StringUtils.isEmpty(filter.getFromChannel()) && !filter.getFromChannel().isEmpty()) {
			builder.and(item.st2_name.in(filter.fromChannel));
		}

		if (!StringUtils.isEmpty(filter.getSex()) && !filter.getSex().isEmpty()) {
			builder.and(item.std_sex.in(filter.sex));
		}
		builder.and(item.std_xsh_city.notIn(""));

		return builder;
	}

	public BooleanBuilder builditemSchool(QStudentInfo item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
		}

		if (!StringUtils.isEmpty(filter.getColleage()) && !filter.getColleage().isEmpty()) {
			builder.and(item.dpt_name.in(filter.colleage));
		}

		if (!StringUtils.isEmpty(filter.getDept()) && !filter.getDept().isEmpty()) {
			builder.and(item.unt_name.in(filter.dept));
		}

		if (!StringUtils.isEmpty(filter.getDegree()) && !filter.getDegree().isEmpty()) {
			builder.and(item.dgr_name.in(filter.degree));
		} else {
			builder.and(item.dgr_name.in("大學日間部"));
		}

		if (!StringUtils.isEmpty(filter.getFromChannel()) && !filter.getFromChannel().isEmpty()) {
			builder.and(item.st2_name.in(filter.fromChannel));
		}

		if (!StringUtils.isEmpty(filter.getSex()) && !filter.getSex().isEmpty()) {
			builder.and(item.std_sex.in(filter.sex));
		}
		builder.and(item.std_xsh_city.notIn(""));
		builder.and(item.lat.notIn("0"));
		builder.and(item.lng.notIn("0"));
		return builder;
	}

	public BooleanBuilder builditem2(QStudentInfo item, FilterStu filter, String channel) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(channel) && !channel.isEmpty()) {
			builder.and(item.st2_name.eq(channel));
		}

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
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

		if (!StringUtils.isEmpty(filter.getFromChannel()) && !filter.getFromChannel().isEmpty()) {
			builder.and(item.st2_name.in(filter.fromChannel));
		}

		if (!StringUtils.isEmpty(filter.getSex()) && !filter.getSex().isEmpty()) {
			builder.and(item.std_sex.in(filter.sex));
		}

		return builder;
	}

	private Map<String, Integer> calMap(Map<String, Map<String, Object>> maintemp) {

		Map<String, Integer> temp = new HashMap<>();
		maintemp.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				Integer tempcal = (Integer) u.get("cnt");
				if (temp.get("total") == null) {
					temp.put("total", tempcal);
				} else {
					Integer cal = temp.get("total");
					cal += tempcal;
					temp.put("total", cal);
				}
			}
		});

		return temp;
	}

	public Map<String, ArrayList<Object>> transMap(Map<String, Map<String, Object>> subTemp) {

		Map<String, ArrayList<Object>> tempxx = new HashMap<String, ArrayList<Object>>();
		subTemp.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				ArrayList<Object> inside = new ArrayList<Object>();
				u.forEach(new BiConsumer<String, Object>() {
					@Override
					public void accept(String t, Object u) {
						Map<String, Object> temp = new HashMap<>();

						temp.put("dep", t);
						temp.put("cnt", u);
						inside.add(temp);

					}
				});
				tempxx.put(t, inside);
			}
		});

		return tempxx;
	}

}
