package com.leadtek.nuu.controller;

import java.util.HashMap;
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
import com.leadtek.nuu.service.StudentScoreService;
import com.querydsl.core.Tuple;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "學生生源")
@RestController
@RequestMapping(value = "/api/studentScore")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class StudnetScoreController {

	@Autowired
	StudentScoreService StudentScoreService;

	@ApiOperation(value = "各學年期各系學生平均成績", notes = "各學年期各系學生平均成績")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scoreByDept", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> scoreByDept(@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {

		return StudentScoreService.scoreByDept(Filter);
	}

	@ApiOperation(value = "各學年期入學管道學生平均成績", notes = "各學年期入學管道學生平均成績")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scoreByChannel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> scoreByChannel(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {

		return StudentScoreService.scoreByChannel(Filter);
	}

	@ApiOperation(value = "各學年期平均成績倒10課程", notes = "各學年期平均成績倒10課程")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scoreLast10", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Object>> scoreLast10(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {
		return StudentScoreService.scoreLast10(Filter);
	}

	@ApiOperation(value = "各學年期平均成績前10課程", notes = "各學年期平均成績前10課程")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/scoreTop10", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Object>> scoreTop10(
			@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {
		return StudentScoreService.scoreTop10(Filter);
	}

}
