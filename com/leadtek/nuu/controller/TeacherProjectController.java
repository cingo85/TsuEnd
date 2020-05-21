package com.leadtek.nuu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadtek.nuu.entity.FilterStu;
import com.leadtek.nuu.service.TeacherProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "教師相關")
@RestController
@RequestMapping(value = "/api/teacherProject")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class TeacherProjectController {

	@Autowired
	TeacherProjectService TeacherProjectService;

	@ApiOperation(value = "教師相關", notes = "教師雜項")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/teachercontribution/{teachparam}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Object>> scoreByDept(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter, @PathVariable String teachparam) {

		if ("Paper".equals(teachparam)) {
			return TeacherProjectService.stuTeacherPaper(Filter);
		} else if ("Service".equals(teachparam)) {
			return TeacherProjectService.stuTeacherService(Filter);
		} else if ("Patent".equals(teachparam)) {
			return TeacherProjectService.stuTeacherPatent(Filter);
		}
		return null;
	}

	@ApiOperation(value = "教師專兼比例", notes = "教師專兼比例")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/teacherstatus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> teacherstatus(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return TeacherProjectService.teacherStatus(filter);
	}
	
	@ApiOperation(value = "生師比", notes = "生師比")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/studentTeacher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> studentTeacher(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return TeacherProjectService.studentTeacher(filter);
	}
}
