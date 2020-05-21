package com.leadtek.nuu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leadtek.nuu.entity.FilterStu;
import com.leadtek.nuu.entity.QRelief;
import com.leadtek.nuu.entity.QScholarship;
import com.leadtek.nuu.repository.CourseScoreRepository;
import com.leadtek.nuu.repository.ReliefRepository;
import com.leadtek.nuu.repository.ScholarshipRepository;
import com.leadtek.nuu.repository.SemesterOrderRepository;
import com.leadtek.nuu.repository.StudentInfoRepository;
import com.leadtek.nuu.repository.StudentOutRepository;
import com.leadtek.nuu.repository.StudentRestRepository;
import com.leadtek.nuu.repository.TeacherProjectRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;

@Service
public class WeakStudentService extends StudentBasicService {

	public WeakStudentService(StudentInfoRepository studentInfoRepository,
			SemesterOrderRepository semesterOrderRepository, StudentRestRepository studentRestRepository,
			StudentOutRepository studentOutRepository, CourseScoreRepository courseScoreRepository,
			TeacherProjectRepository teacherProjectRepository, ReliefRepository reliefRepository,
			ScholarshipRepository scholarshipRepository) {
		super(studentInfoRepository, semesterOrderRepository, studentRestRepository, studentOutRepository,
				courseScoreRepository, teacherProjectRepository, reliefRepository, scholarshipRepository);
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> weakcountType(FilterStu filter) {
		QRelief item = QRelief.relief;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result3 = queryFactory.selectFrom(item)
				.select(item.dpt_name, item.unt_name, item.red_name, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.dpt_name, item.unt_name, item.red_name).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("twolevel", tuple.get(item.dpt_name));
					map.put("threelevel", tuple.get(item.unt_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> result2 = queryFactory.selectFrom(item)
				.select(item.dpt_name, item.red_name, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.dpt_name, item.red_name).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("twolevel", tuple.get(item.dpt_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> result1 = queryFactory.selectFrom(item)
				.select(item.red_name, item.SID.countDistinct().as(count)).where(builder).groupBy(item.red_name).fetch()
				.stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Map<String, Object>> maintemp = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> maintemp2 = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> maintemp3 = new LinkedHashMap<String, Map<String, Object>>();

		for (Map<String, Object> reitem : result1) {
			if (maintemp.get(reitem.get("onelevel")) == null) {
				maintemp.put((String) reitem.get("onelevel"), new HashMap<String, Object>());
				maintemp.get((String) reitem.get("onelevel")).put("name", (String) reitem.get("onelevel"));
				maintemp.get((String) reitem.get("onelevel")).put("cnt", reitem.get("count"));
			} else {
				maintemp.get((String) reitem.get("onelevel")).put("name", (String) reitem.get("onelevel"));
				maintemp.get((String) reitem.get("onelevel")).put("cnt", reitem.get("count"));
			}
		}

		for (Map<String, Object> reitem : result2) {
			if (maintemp2.get(reitem.get("onelevel") + "_sub") == null) {
				maintemp2.put((String) reitem.get("onelevel") + "_sub", new HashMap<String, Object>());
				if (maintemp2.get(reitem.get("onelevel") + "_sub").get(reitem.get("twolevel")) == null) {
					maintemp2.get(reitem.get("onelevel") + "_sub").put((String) reitem.get("twolevel"),
							reitem.get("count"));
				}
			} else {
				if (maintemp2.get(reitem.get("onelevel") + "_sub").get(reitem.get("twolevel")) == null) {
					maintemp2.get(reitem.get("onelevel") + "_sub").put((String) reitem.get("twolevel"),
							reitem.get("count"));
				}
			}
		}

		for (Map<String, Object> reitem : result3) {
			if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel")) == null) {
				maintemp3.put((String) reitem.get("onelevel") + "_sub_" + (String) reitem.get("twolevel"),
						new HashMap<String, Object>());
				if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
						.get(reitem.get("threelevel")) == null) {
					maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
							.put((String) reitem.get("threelevel"), reitem.get("count"));
				}
			} else {
				if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
						.get(reitem.get("threelevel")) == null) {
					maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
							.put((String) reitem.get("threelevel"), reitem.get("count"));
				}
			}
		}
		Map<String, ArrayList<Object>> codingCourse1Sub = transMapThird(maintemp2, maintemp3);
		Map<String, Integer> total = calMap(maintemp);

		result.putAll(total);
		result.putAll(maintemp);
		result.putAll(codingCourse1Sub);
		return result;
	}

	public Map<String, Object> weakcountChannel(FilterStu filter) {
		QRelief item = QRelief.relief;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem(item, filter);
		List<Map<String, Object>> result3 = queryFactory.selectFrom(item)
				.select(item.red_name, item.st2_name, item.st3_name, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.red_name, item.st2_name, item.st3_name).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("twolevel", tuple.get(item.st2_name));
					map.put("threelevel", tuple.get(item.st3_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> result2 = queryFactory.selectFrom(item)
				.select(item.red_name, item.st2_name, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.red_name, item.st2_name).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("twolevel", tuple.get(item.st2_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> result1 = queryFactory.selectFrom(item)
				.select(item.red_name, item.SID.countDistinct().as(count)).where(builder).groupBy(item.red_name).fetch()
				.stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("onelevel", tuple.get(item.red_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Map<String, Object>> maintemp = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> maintemp2 = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> maintemp3 = new LinkedHashMap<String, Map<String, Object>>();

		for (Map<String, Object> reitem : result1) {
			if (maintemp.get(reitem.get("onelevel")) == null) {
				maintemp.put((String) reitem.get("onelevel"), new HashMap<String, Object>());
				maintemp.get((String) reitem.get("onelevel")).put("name", (String) reitem.get("onelevel"));
				maintemp.get((String) reitem.get("onelevel")).put("cnt", reitem.get("count"));
			} else {
				maintemp.get((String) reitem.get("onelevel")).put("name", (String) reitem.get("onelevel"));
				maintemp.get((String) reitem.get("onelevel")).put("cnt", reitem.get("count"));
			}
		}

		for (Map<String, Object> reitem : result2) {
			if (maintemp2.get(reitem.get("onelevel") + "_sub") == null) {
				maintemp2.put((String) reitem.get("onelevel") + "_sub", new HashMap<String, Object>());
				if (maintemp2.get(reitem.get("onelevel") + "_sub").get(reitem.get("twolevel")) == null) {
					maintemp2.get(reitem.get("onelevel") + "_sub").put((String) reitem.get("twolevel"),
							reitem.get("count"));
				}
			} else {
				if (maintemp2.get(reitem.get("onelevel") + "_sub").get(reitem.get("twolevel")) == null) {
					maintemp2.get(reitem.get("onelevel") + "_sub").put((String) reitem.get("twolevel"),
							reitem.get("count"));
				}
			}
		}

		for (Map<String, Object> reitem : result3) {
			if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel")) == null) {
				maintemp3.put((String) reitem.get("onelevel") + "_sub_" + (String) reitem.get("twolevel"),
						new HashMap<String, Object>());
				if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
						.get(reitem.get("threelevel")) == null) {
					maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
							.put((String) reitem.get("threelevel"), reitem.get("count"));
				}
			} else {
				if (maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
						.get(reitem.get("threelevel")) == null) {
					maintemp3.get(reitem.get("onelevel") + "_sub_" + reitem.get("twolevel"))
							.put((String) reitem.get("threelevel"), reitem.get("count"));
				}
			}
		}
		Map<String, ArrayList<Object>> codingCourse1Sub = transMapThird(maintemp2, maintemp3);
		Map<String, Integer> total = calMap(maintemp);

		result.putAll(total);
		result.putAll(maintemp);
		result.putAll(codingCourse1Sub);
		return result;

	}

	public Map<String, Object> ScholarshipcountType(FilterStu filter) {
		QScholarship item = QScholarship.scholarship;
		NumberPath<Integer> count = Expressions.numberPath(Integer.class, "c");

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.scho_type1, item.scho_amt.sum().as(count))
				.groupBy(item.yms_year, item.yms_sms, item.scho_type1).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("yearterm", tuple.get(item.yms_year) + tuple.get(item.yms_sms));
					map.put("type", tuple.get(item.scho_type1));
					map.put("count", tuple.get(count));
					return map;
				}).collect(Collectors.toList());

		// 前端格式

		Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

		for (Map<String, Object> reitem : result) {

			if (resu.get(reitem.get("yearterm")) == null) {
				resu.put((String) reitem.get("yearterm"), new HashMap<String, Integer>());
				if (resu.get((String) reitem.get("yearterm")).get(reitem.get("type")) == null) {
					resu.get((String) reitem.get("yearterm")).put((String) reitem.get("type"),
							(Integer) reitem.get("count"));
				}

				if (resu.get((String) reitem.get("yearterm")).get("allcnt") == null) {
					resu.get((String) reitem.get("yearterm")).put("allcnt", (Integer) reitem.get("count"));
				} else {
					Integer temp = resu.get((String) reitem.get("yearterm")).get("allcnt");
					temp += (Integer) reitem.get("count");
					resu.get((String) reitem.get("yearterm")).put("allcnt", temp);
				}
			} else {
				if (resu.get((String) reitem.get("yearterm")).get(reitem.get("type")) == null) {
					resu.get((String) reitem.get("yearterm")).put((String) reitem.get("type"),
							(Integer) reitem.get("count"));
				}

				if (resu.get((String) reitem.get("yearterm")).get("allcnt") == null) {
					resu.get((String) reitem.get("yearterm")).put("allcnt", (Integer) reitem.get("count"));
				} else {
					Integer temp = resu.get((String) reitem.get("yearterm")).get("allcnt");
					temp += (Integer) reitem.get("count");
					resu.get((String) reitem.get("yearterm")).put("allcnt", temp);
				}

			}
		}

		ArrayList<Map<String, Integer>> result_array = new ArrayList<>();

		Map<String, Object> finresult = new HashMap<String, Object>();
		resu.forEach(new BiConsumer<String, Map<String, Integer>>() {

			@Override
			public void accept(String t, Map<String, Integer> u) {
				u.put("yearterm", Integer.valueOf(t));
				result_array.add(u);
			}
		});
		finresult.put("lineData", result_array);

		return finresult;

	}

	public Map<String, Object> ScholarshipcountDept(FilterStu filter) {
		QScholarship item = QScholarship.scholarship;
		Path<Long> count = Expressions.numberPath(Long.class, "c");

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.scho_type1, item.SID.countDistinct().as(count))
				.groupBy(item.yms_year, item.yms_sms, item.scho_type1).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("yearterm", tuple.get(item.yms_year) + tuple.get(item.yms_sms));
					map.put("type", tuple.get(item.scho_type1));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式

		Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

		for (Map<String, Object> reitem : result) {

			if (resu.get(reitem.get("yearterm")) == null) {
				resu.put((String) reitem.get("yearterm"), new HashMap<String, Integer>());
				if (resu.get((String) reitem.get("yearterm")).get(reitem.get("type")) == null) {
					resu.get((String) reitem.get("yearterm")).put((String) reitem.get("type"),
							(Integer) reitem.get("count"));
				}

				if (resu.get((String) reitem.get("yearterm")).get("allcnt") == null) {
					resu.get((String) reitem.get("yearterm")).put("allcnt", (Integer) reitem.get("count"));
				} else {
					Integer temp = resu.get((String) reitem.get("yearterm")).get("allcnt");
					if (temp != null)
						temp += (Integer) reitem.get("count");
					resu.get((String) reitem.get("yearterm")).put("allcnt", temp);
				}
			} else {
				if (resu.get((String) reitem.get("yearterm")).get(reitem.get("type")) == null) {
					resu.get((String) reitem.get("yearterm")).put((String) reitem.get("type"),
							(Integer) reitem.get("count"));
				}

				if (resu.get((String) reitem.get("yearterm")).get("allcnt") == null) {
					resu.get((String) reitem.get("yearterm")).put("allcnt", (Integer) reitem.get("count"));
				} else {
					Integer temp = resu.get((String) reitem.get("yearterm")).get("allcnt");
					if ((Integer) reitem.get("count") != null)
						temp += (Integer) reitem.get("count");
					resu.get((String) reitem.get("yearterm")).put("allcnt", temp);
				}

			}
		}

		ArrayList<Map<String, Integer>> result_array = new ArrayList<>();

		Map<String, Object> finresult = new HashMap<String, Object>();
		resu.forEach(new BiConsumer<String, Map<String, Integer>>() {

			@Override
			public void accept(String t, Map<String, Integer> u) {
				u.put("yearterm", Integer.valueOf(t));
				result_array.add(u);
			}
		});
		finresult.put("lineData", result_array);

		return finresult;

	}

	public BooleanBuilder builditem(QRelief item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(item.yms_sms.eq("1"));
		
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

	public Map<String, ArrayList<Object>> transMapThird(Map<String, Map<String, Object>> subTemp,
			Map<String, Map<String, Object>> maintemp3) {

		Map<String, ArrayList<Object>> tempxx = new HashMap<String, ArrayList<Object>>();
		subTemp.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String first_sub, Map<String, Object> u) {
				ArrayList<Object> inside = new ArrayList<Object>();
				u.forEach(new BiConsumer<String, Object>() {
					@Override
					public void accept(String first_info, Object u) {
						Map<String, Object> temp = new LinkedHashMap<>();

						Map<String, Object> sec_sub = maintemp3.get(first_sub + "_" + first_info);
						ArrayList<Object> sec_inside = new ArrayList<Object>();
						sec_sub.forEach(new BiConsumer<String, Object>() {
							@Override
							public void accept(String t, Object u) {
								Map<String, Object> sec_temp = new LinkedHashMap<>();
								sec_temp.put("name", t);
								sec_temp.put("val", u);
								sec_inside.add(sec_temp);
							}
						});
						if (!"".equals(first_info)) {
							temp.put("dep", first_info);
							temp.put("cnt", u);
							temp.put("sub", sec_inside);
							inside.add(temp);

						}

					}
				});
				tempxx.put(first_sub, inside);
			}
		});

		return tempxx;
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

	private Map<String, Integer> calMap1(Map<String, Map<String, Object>> maintemp) {

		Map<String, Integer> temp = new HashMap<>();
		maintemp.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				Integer tempcal = (Integer) u.get("val");
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

}
