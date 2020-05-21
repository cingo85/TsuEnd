package com.leadtek.nuu.entity;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class FilterStu {

	@ApiModelProperty(value = "入學年", required = true)
	public List<Integer> enrolleryear = new ArrayList<Integer>();

	@ApiModelProperty(value = "學院", required = true)
	public List<String> colleage = new ArrayList<String>();

	@ApiModelProperty(value = "學系", required = true)
	public List<String> dept = new ArrayList<String>();

	@ApiModelProperty(value = "學制", required = true)
	public List<String> degree = new ArrayList<String>();

	@ApiModelProperty(value = "入學管道", required = true)
	public List<String> fromChannel = new ArrayList<String>();

	@ApiModelProperty(value = "身分別", required = true)
	public List<String> status = new ArrayList<String>();

	@ApiModelProperty(value = "性別", required = true)
	public List<String> sex = new ArrayList<String>();
	
	@ApiModelProperty(value = "選修別", required = true)
	public String crt_name;
	
	@ApiModelProperty(value = "學年期", required = true)
	public String yms_year;
	
	@ApiModelProperty(value = "學年", required = true)
	public String yms_sms;
	
	


	public List<Integer> getEnrolleryear() {
		return enrolleryear;
	}

	public void setEnrolleryear(List<Integer> enrolleryear) {
		this.enrolleryear = enrolleryear;
	}

	public List<String> getColleage() {
		return colleage;
	}

	public void setColleage(List<String> colleage) {
		this.colleage = colleage;
	}

	public List<String> getDept() {
		return dept;
	}

	public void setDept(List<String> dept) {
		this.dept = dept;
	}

	public List<String> getDegree() {
		return degree;
	}

	public void setDegree(List<String> degree) {
		this.degree = degree;
	}

	public List<String> getFromChannel() {
		return fromChannel;
	}

	public void setFromChannel(List<String> fromChannel) {
		this.fromChannel = fromChannel;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getSex() {
		return sex;
	}

	public void setSex(List<String> sex) {
		this.sex = sex;
	}

	public String getCrt_name() {
		return crt_name;
	}

	public void setCrt_name(String crt_name) {
		this.crt_name = crt_name;
	}

	public String getYms_year() {
		return yms_year;
	}

	public void setYms_year(String yms_year) {
		this.yms_year = yms_year;
	}

	public String getYms_sms() {
		return yms_sms;
	}

	public void setYms_sms(String yms_sms) {
		this.yms_sms = yms_sms;
	}


	
	
}
