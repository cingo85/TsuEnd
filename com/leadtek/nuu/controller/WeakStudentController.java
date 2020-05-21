package com.leadtek.nuu.controller;

import java.util.List;
import java.util.Map;

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

import com.leadtek.nuu.entity.FilterStu;
import com.leadtek.nuu.service.TeacherProjectService;
import com.leadtek.nuu.service.WeakStudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "弱勢生相關")
@RestController
@RequestMapping(value = "/api/weakStudent")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class WeakStudentController {

	@Autowired
	WeakStudentService WeakStudentService;
	
	@ApiOperation(value = "各學年期獎助學金總額與類型", notes = "各學年期獎助學金總額與類型")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scholarshipcountType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> scholarshipcountType(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return WeakStudentService.ScholarshipcountType(filter);
	}
	
	@ApiOperation(value = "各學年期獎助學金人數與類型", notes = "各學年期獎助學金人數與類型")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scholarshipcountDept", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> scholarshipcountDept(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return WeakStudentService.ScholarshipcountDept(filter);
	}
	
	@ApiOperation(value = "各學年弱勢生人數與類型", notes = "各學年弱勢生人數與類型")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/weakcountType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> weakcountType(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return WeakStudentService.weakcountType(filter);
	}
	
	@ApiOperation(value = "各學年弱勢生來源管道", notes = "各學年弱勢生來源管道")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/weakcountChannel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> weakcountChannel(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu filter) {
		return WeakStudentService.weakcountChannel(filter);
	}
	
	
}
