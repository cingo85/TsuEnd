package com.leadtek.nuu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadtek.nuu.entity.*;
import com.leadtek.nuu.service.StudentBasicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "學生相關基本概況")
@RestController
@RequestMapping(value = "/api/studentBasic")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class StudnetBasicController {

	@Autowired
	StudentBasicService studentBasicService;

	@ApiOperation(value = "各學年新生人數", notes = "各學年新生人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/enrollerStudent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> enrollerStudent(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {

		Map<String, Object> result = studentBasicService.enrollerStudent(Filter);
		return result;
	}

	@ApiOperation(value = "各學年學制系所人數比例", notes = "各學年學制系所人數比例")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/studentDegreeDept", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> studentDegreeDept(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {

		Map<String, Object> result = studentBasicService.studentDegreeDept(filter);

		return result;
	}

	@ApiOperation(value = "學生性別比例", notes = "學生性別比例")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/studentSex", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Integer>> studentSex(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return studentBasicService.studentSex(filter);
	}

	@ApiOperation(value = "各學年休學人數與原因比例", notes = "各學年新生人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/restStudent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ArrayList<Map<String, Object>> restStudent(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {

		Map<String, Map<String, Object>> result = studentBasicService.restStudent(filter);
		ArrayList<Map<String, Object>> result_array = new ArrayList<>();

		result.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				u.put("type", t);
				result_array.add(u);
			}
		});
		return result_array;
	}

	@ApiOperation(value = "各學年退學人數與原因比例", notes = "各學年新生人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/outStudent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ArrayList<Map<String, Object>> outStudent(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {

		Map<String, Map<String, Object>> result = studentBasicService.outStudent(filter);
		ArrayList<Map<String, Object>> result_array = new ArrayList<>();

		result.forEach(new BiConsumer<String, Map<String, Object>>() {

			@Override
			public void accept(String t, Map<String, Object> u) {
				u.put("type", t);
				result_array.add(u);
			}
		});

		return result_array;
	}

}
