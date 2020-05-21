package com.leadtek.tsu.controller;

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

import com.leadtek.tsu.entity.FilterStu;
import com.leadtek.tsu.service.FilterService;
import com.leadtek.tsu.service.StudentBasicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "篩選列表")
@RestController
@RequestMapping(value = "/api/filter")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class FilterController {

	@Autowired
	StudentBasicService studentBasicService;
	
	@Autowired
	FilterService FilterService;

	@ApiOperation(value = "篩選列表", notes = "篩選列表")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/optionfilter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> enrollerStudent(@ApiParam(required = true, value = "過濾選項") @RequestBody FilterStu Filter) {

		Map<String, Object> result = FilterService.filter(Filter);
		return result;
	}
}
