package com.leadtek.nuu.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leadtek.nuu.entity.FilterStu;
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

@Service
public class FilterService extends StudentBasicService {
	public FilterService(StudentInfoRepository studentInfoRepository, SemesterOrderRepository semesterOrderRepository,
			StudentRestRepository studentRestRepository, StudentOutRepository studentOutRepository,
			CourseScoreRepository courseScoreRepository, TeacherProjectRepository teacherProjectRepository,
			ReliefRepository reliefRepository, ScholarshipRepository scholarshipRepository) {
		super(studentInfoRepository, semesterOrderRepository, studentRestRepository, studentOutRepository,
				courseScoreRepository, teacherProjectRepository, reliefRepository, scholarshipRepository);
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> filter(FilterStu filter) {
		QSemesterOrder item = QSemesterOrder.semesterOrder;

		BooleanBuilder builder = builditem2(item, filter);

		List<Map<String, String>> enrollerYear = queryFactory.selectFrom(item).select(item.std_year).distinct()
				.where(item.std_year.notIn(0)).orderBy(item.std_year.asc()).where(builder).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T.toString());
					map.put("name", T.toString());
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> colleageresult = queryFactory.selectFrom(item).select(item.dpt_name).distinct()
				.where(item.dpt_name.notIn("")).orderBy(item.dpt_name.asc()).where(builder).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> deptresult = queryFactory.selectFrom(item).select(item.dpt_name, item.unt_name)
				.distinct().where(item.dpt_name.notIn("")).orderBy(item.dpt_name.asc()).where(builder).fetch().stream().map(tuple -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", tuple.get(item.dpt_name) + "_" + tuple.get(item.unt_name));
					map.put("name", tuple.get(item.unt_name));
					map.put("upLayer", tuple.get(item.dpt_name));
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> degree = queryFactory.selectFrom(item).select(item.dgr_name).distinct()
				.orderBy(item.dgr_name.asc()).where(builder).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> fromchannel = queryFactory.selectFrom(item).select(item.st2_name).distinct()
				.where(item.st2_name.notIn("")).orderBy(item.st2_name.asc()).where(builder).fetch().stream().map(T -> {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("id", T);
					map.put("name", T);
					map.put("upLayer", "");
					return map;
				}).collect(Collectors.toList());

		List<Map<String, String>> sex = queryFactory.selectFrom(item).select(item.std_sex).distinct()
				.where(item.std_sex.notIn("")).orderBy(item.std_sex.asc()).where(builder).fetch().stream().map(T -> {
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

	public BooleanBuilder builditem2(QSemesterOrder item, FilterStu filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(filter.getYms_year()) && !filter.getYms_year().isEmpty()) {
			builder.and(item.yms_year.in(filter.yms_year));
		}

		if (!StringUtils.isEmpty(filter.getEnrolleryear()) && !filter.getEnrolleryear().isEmpty()) {
			builder.and(item.std_year.in(filter.enrolleryear));
		}

		if (!StringUtils.isEmpty(filter.getYms_sms()) && !filter.getYms_sms().isEmpty()) {
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
}
