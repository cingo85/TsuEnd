package com.leadtek.tsu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leadtek.tsu.entity.FilterStu;
import com.leadtek.tsu.entity.QSemesterOrder;
import com.leadtek.tsu.entity.QStudentInfo;
import com.leadtek.tsu.entity.QStudentOut;
import com.leadtek.tsu.entity.QStudentRest;
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
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class StudentBasicService {

	@PersistenceContext
	private EntityManager entityManager;

	private final StudentInfoRepository studentInfoRepository;

	private final SemesterOrderRepository semesterOrderRepository;

	private final StudentRestRepository studentRestRepository;

	private final StudentOutRepository studentOutRepository;

	private final CourseScoreRepository courseScoreRepository;

	private final TeacherProjectRepository teacherProjectRepository;

	private final ReliefRepository reliefRepository;

	private final ScholarshipRepository scholarshipRepository;

	public JPAQueryFactory queryFactory;

	@Autowired
	public StudentBasicService(StudentInfoRepository studentInfoRepository,
			SemesterOrderRepository semesterOrderRepository, StudentRestRepository studentRestRepository,
			StudentOutRepository studentOutRepository, CourseScoreRepository courseScoreRepository,
			TeacherProjectRepository teacherProjectRepository, ReliefRepository reliefRepository,
			ScholarshipRepository scholarshipRepository) {

		this.studentInfoRepository = studentInfoRepository;
		this.semesterOrderRepository = semesterOrderRepository;
		this.studentRestRepository = studentRestRepository;
		this.studentOutRepository = studentOutRepository;
		this.courseScoreRepository = courseScoreRepository;
		this.teacherProjectRepository = teacherProjectRepository;
		this.reliefRepository = reliefRepository;
		this.scholarshipRepository = scholarshipRepository;
	}

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}
	/*
	 * 計算 std_year(入學年) 不重複 SID(學號)
	 */
	
	public Map<String, Object> filter() {
		QSemesterOrder item = QSemesterOrder.semesterOrder;

		StringPath temp = Expressions.stringPath("c");

		List<Map<String, String>> enrollerYear = queryFactory.selectFrom(item).select(item.std_year).distinct()
				.where(item.std_year.notIn(0)).orderBy(item.std_year.asc()).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T.toString());
					map.put("name", T.toString());
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> colleageresult = queryFactory.selectFrom(item).select(item.dpt_name).distinct()
				.where(item.dpt_name.notIn("")).orderBy(item.dpt_name.asc()).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> deptresult = queryFactory.selectFrom(item).select(item.dpt_name, item.unt_name)
				.distinct().where(item.dpt_name.notIn("")).orderBy(item.dpt_name.asc()).fetch().stream().map(tuple -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", tuple.get(item.dpt_name) + "_" + tuple.get(item.unt_name));
					map.put("name", tuple.get(item.unt_name));
					map.put("upLayer", tuple.get(item.dpt_name));
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> degree = queryFactory.selectFrom(item).select(item.dgr_name).distinct()
				.orderBy(item.dgr_name.asc()).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> fromchannel = queryFactory.selectFrom(item).select(item.st2_name).distinct()
				.where(item.st2_name.notIn("")).orderBy(item.st2_name.asc()).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> sex = queryFactory.selectFrom(item).select(item.std_sex).distinct()
				.where(item.std_sex.notIn("")).orderBy(item.std_sex.asc()).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T.replace("F", "女").replace("M", "男"));
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		Map<String, String> obname = new LinkedHashMap<String, String>();
		obname.put("enrollerYear", "入學年");
		obname.put("colleage", "學院");
		obname.put("dept", "系所");
		obname.put("degree", "學制");
		obname.put("fromChannel", "入學管道");
		obname.put("sex", "性別");

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("enrollerYear", enrollerYear);
		result.put("colleage", colleageresult);
		result.put("dept", deptresult);
		result.put("degree", degree);
		result.put("fromChannel", fromchannel);
		result.put("sex", sex);
		result.put("ObjectName", obname);
		return result;
	}

	public Map<String, Object> enrollerStudent(FilterStu filter) {

		Map<String, Object> finresult = new HashMap<String, Object>();

		QStudentInfo item = QStudentInfo.studentInfo;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

		BooleanBuilder builder = builditem(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.std_year, item.dpt_name, item.SID.count().as(count)).where(builder)
				.groupBy(item.std_year, item.dpt_name).orderBy(item.std_year.asc()).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					if (tuple.get(item.std_year) >= 101) {
						map.put("year", tuple.get(item.std_year).toString());
						map.put("dpt_name", tuple.get(item.dpt_name));
						map.put("count", tuple.get(count).intValue());
					}
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> resultAllcount = queryFactory.selectFrom(item)
				.select(item.std_year, item.SID.count().as(count)).where(builder).groupBy(item.std_year)
				.orderBy(item.std_year.asc()).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					if (tuple.get(item.std_year) >= 101) {
						map.put("year", tuple.get(item.std_year).toString());
						map.put("count", tuple.get(count).intValue());
					}
					return map;
				}).collect(Collectors.toList());

		// 前端格式

		Map<String, Map<String, Integer>> resu = new HashMap<String, Map<String, Integer>>();

		for (Map<String, Object> reitem : result) {

			if (resu.get(reitem.get("year")) == null) {
				resu.put((String) reitem.get("year"), new HashMap<String, Integer>());
				if (resu.get((String) reitem.get("year")).get(reitem.get("dpt_name")) == null) {
					resu.get((String) reitem.get("year")).put((String) reitem.get("dpt_name"),
							(Integer) reitem.get("count"));
				}
			} else {
				if (resu.get((String) reitem.get("year")).get(reitem.get("dpt_name")) == null) {
					resu.get((String) reitem.get("year")).put((String) reitem.get("dpt_name"),
							(Integer) reitem.get("count"));
				}
			}
		}

		for (Map<String, Object> reitem : resultAllcount) {

			if (resu.get(reitem.get("year")) == null) {
				resu.put((String) reitem.get("year"), new HashMap<String, Integer>());
				if (resu.get((String) reitem.get("year")).get("allcount") == null) {
					resu.get((String) reitem.get("year")).put("allcount", (Integer) reitem.get("count"));
				}
			} else {
				if (resu.get((String) reitem.get("year")).get("allcount") == null) {
					resu.get((String) reitem.get("year")).put("allcount", (Integer) reitem.get("count"));
				}
			}
		}

		ArrayList<Map<String, Integer>> result_array = new ArrayList<>();

		resu.forEach(new BiConsumer<String, Map<String, Integer>>() {

			@Override
			public void accept(String t, Map<String, Integer> u) {
				if (t != null) {
					u.put("syear", Integer.valueOf(t));
					result_array.add(u);
				}

			}
		});
		finresult.put("lineData", result_array);

		return finresult;
	}

	/*
	 * 計算 yms_year(學年) 不重複 SID(學號)
	 */

	public Map<String, Object> studentDegreeDept(FilterStu filter) {
		QSemesterOrder item = QSemesterOrder.semesterOrder;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem2(item, filter);

		List<Map<String, Object>> resultbydegreeNdept = queryFactory.selectFrom(item)
				.select(item.dgr_name, item.unt_name, item.SID.countDistinct().as(count)).where(builder)
				.groupBy(item.dgr_name, item.unt_name).fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("dpt_name", tuple.get(item.dgr_name));
					map.put("unt_name", tuple.get(item.unt_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		List<Map<String, Object>> resultbydegree = queryFactory.selectFrom(item)
				.select(item.dgr_name, item.SID.countDistinct().as(count)).where(builder).groupBy(item.dgr_name).fetch()
				.stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("dpt_name", tuple.get(item.dgr_name));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Map<String, Object>> maintemp = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> maintemp2 = new LinkedHashMap<String, Map<String, Object>>();
		for (Map<String, Object> reitem : resultbydegree) {
			if (maintemp.get(reitem.get("dpt_name")) == null) {
				maintemp.put((String) reitem.get("dpt_name"), new HashMap<String, Object>());
				maintemp.get((String) reitem.get("dpt_name")).put("name", (String) reitem.get("dpt_name"));
				maintemp.get((String) reitem.get("dpt_name")).put("cnt", reitem.get("count"));
			} else {
				maintemp.get((String) reitem.get("dpt_name")).put("name", (String) reitem.get("dpt_name"));
				maintemp.get((String) reitem.get("dpt_name")).put("cnt", reitem.get("count"));
			}
		}

		for (Map<String, Object> reitem : resultbydegreeNdept) {
			if (maintemp2.get(reitem.get("dpt_name") + "_sub") == null) {
				maintemp2.put((String) reitem.get("dpt_name") + "_sub", new HashMap<String, Object>());
				if (maintemp2.get(reitem.get("dpt_name") + "_sub").get(reitem.get("unt_name")) == null) {
					maintemp2.get(reitem.get("dpt_name") + "_sub").put((String) reitem.get("unt_name"),
							reitem.get("count"));
				}
			} else {
				if (maintemp2.get(reitem.get("dpt_name") + "_sub").get(reitem.get("unt_name")) == null) {
					maintemp2.get(reitem.get("dpt_name") + "_sub").put((String) reitem.get("unt_name"),
							reitem.get("count"));
				}
			}
		}

		Map<String, ArrayList<Object>> codingCourse1Sub = transMap(maintemp2);
		Map<String, Integer> total = calMap(maintemp);

		result.putAll(total);
		result.putAll(maintemp);
		result.putAll(codingCourse1Sub);

		return result;
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

	public List<Map<String, Integer>> studentSex(FilterStu filter) {
		QSemesterOrder item = QSemesterOrder.semesterOrder;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = studentSex(item, filter);

		List<Map<String, Integer>> result = queryFactory.selectFrom(item)
				.select(item.std_sex, item.SID.countDistinct().as(count)).where(builder).groupBy(item.std_sex).fetch()
				.stream().map(tuple -> {
					Map<String, Integer> map = new LinkedHashMap<>();
					map.put(tuple.get(item.std_sex), tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());
		return result;

		// 前端格式

	}

	public Map<String, Map<String, Object>> restStudent(FilterStu filter) {
		QStudentRest item = QStudentRest.studentRest;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem3(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.rest_reason_kind, item.SID.countDistinct().as(count))
				.where(builder).groupBy(item.yms_year, item.yms_sms, item.rest_reason_kind).orderBy(item.yms_year.asc())
				.fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("yms_year", tuple.get(item.yms_year));
					map.put("yms_sms", tuple.get(item.yms_sms));
					map.put("rest_reason_kind", tuple.get(item.rest_reason_kind));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, Map<String, Object>> maintemp = new LinkedHashMap<String, Map<String, Object>>();

		for (Map<String, Object> reitem : result) {
			if (maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms")) == null) {
				maintemp.put((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"),
						new HashMap<String, Object>());
				maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"))
						.put((String) reitem.get("rest_reason_kind"), reitem.get("count"));
			} else {
				maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"))
						.put((String) reitem.get("rest_reason_kind"), reitem.get("count"));
			}
		}
		return maintemp;
	}

	public Map<String, Map<String, Object>> outStudent(FilterStu filter) {
		QStudentOut item = QStudentOut.studentOut;
		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
		BooleanBuilder builder = builditem4(item, filter);

		List<Map<String, Object>> result = queryFactory.selectFrom(item)
				.select(item.yms_year, item.yms_sms, item.out_reason_kind, item.SID.countDistinct().as(count))
				.where(builder).groupBy(item.yms_year, item.yms_sms, item.out_reason_kind).orderBy(item.yms_year.asc())
				.fetch().stream().map(tuple -> {
					Map<String, Object> map = new LinkedHashMap<>();
					map.put("yms_year", tuple.get(item.yms_year));
					map.put("yms_sms", tuple.get(item.yms_sms));
					map.put("out_reason_kind", tuple.get(item.out_reason_kind));
					map.put("count", tuple.get(count).intValue());
					return map;
				}).collect(Collectors.toList());

		// 前端格式
		Map<String, Map<String, Object>> maintemp = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> temp = new LinkedHashMap<>();
		for (Map<String, Object> reitem : result) {
			if (maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms")) == null) {
				maintemp.put((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"),
						new HashMap<String, Object>());
				maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"))
						.put((String) reitem.get("out_reason_kind"), reitem.get("count"));
			} else {
				maintemp.get((String) reitem.get("yms_year") + (String) reitem.get("yms_sms"))
						.put((String) reitem.get("out_reason_kind"), reitem.get("count"));
			}
		}

		return maintemp;
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
		builder.and(item.dpt_name.notIn(""));
		return builder;
	}

	public BooleanBuilder builditem2(QSemesterOrder item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getYms_year()) && !filter.getYms_year().isEmpty()) {
			builder.and(item.yms_year.in(filter.yms_year));
		} else {
			builder.and(item.yms_year.in("108"));
		}

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
		}
		
		if(!StringUtils.isEmpty(filter.getYms_sms()) && !filter.getYms_sms().isEmpty()) {
			builder.and(item.yms_sms.in(filter.yms_sms));
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

		builder.and(item.dpt_name.notIn(""));
		return builder;
	}

	public BooleanBuilder builditem3(QStudentRest item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
		} else {
			builder.and(item.std_year.in(107));
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
		builder.and(item.dpt_name.notIn(""));
		return builder;
	}

	public BooleanBuilder builditem4(QStudentOut item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
		} else {
			builder.and(item.std_year.in(107));
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
		builder.and(item.dpt_name.notIn(""));
		return builder;
	}
	
	public BooleanBuilder studentSex(QSemesterOrder item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getYms_year()) && !filter.getYms_year().isEmpty()) {
			builder.and(item.yms_year.in(filter.yms_year));
		} else {
			builder.and(item.yms_year.in("108"));
		}
		
		if(!StringUtils.isEmpty(filter.getYms_sms()) && !filter.getYms_sms().isEmpty()) {
			builder.and(item.yms_sms.in(filter.yms_sms));
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

		builder.and(item.dpt_name.notIn(""));
		return builder;
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
