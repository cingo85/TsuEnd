package com.leadtek.nuu.controller;

import java.util.HashMap;
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
import com.leadtek.nuu.service.StudentComeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "學生生源")
@RestController
@RequestMapping(value = "/api/studentCome")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class StudnetComeController {
	
	@Autowired
	StudentComeService StudentComeService;

	@ApiOperation(value = "來源學校人數", notes = "來源學校人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/stuComeSchool", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Object>> stuComeSchool(@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {
		return StudentComeService.stuComeSchool(Filter);
	}

	@ApiOperation(value = "來源地區人數", notes = "來源地區人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/stuComeDis", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Map<String, Object>> stuComeDis(@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {	
		return 	StudentComeService.stuComeDis(Filter);
	}

	@ApiOperation(value = "來源管道人數", notes = "來源管道人數")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/stuComeChannel/{channel}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> stuComeChannel(@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter, @PathVariable String channel) {
		return StudentComeService.stuComeChannel(Filter,channel);
	}

}
