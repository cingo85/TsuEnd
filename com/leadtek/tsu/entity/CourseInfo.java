package com.leadtek.tsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "courseinfo")
@ApiModel(description = "成績")
@Entity
@IdClass(CourseInfo.class)
public class CourseInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "學年", required = true)
	@Column(name = "yms_year", updatable = false, insertable = false)
	public String yms_year;

	@Id
	@ApiModelProperty(value = "學期", required = true)
	@Column(name = "yms_sms", updatable = false, insertable = false)
	public String yms_sms;
	
	@Id
	@ApiModelProperty(value = "課號", required = true)
	@Column(name = "CID", updatable = false, insertable = false)
	public String CID;

	@Id
	@ApiModelProperty(value = "課程種類", required = true)
	@Column(name = "crt_name", updatable = false, insertable = false)
	public String crt_name;
	
	@ApiModelProperty(value = "課程名稱", required = true)
	@Column(name = "sub_name", updatable = false, insertable = false)
	public String sub_name;

	@ApiModelProperty(value = "課程平均成績", required = true)
	@Column(name = "score_avg", updatable = false, insertable = false)
	public Integer score_avg;
	
	
	@ApiModelProperty(value = "開課學院", required = true)
	@Column(name = "course_dpt_name", updatable = false, insertable = false)
	public String dpt_name;
	
	@ApiModelProperty(value = "開課系所", required = true)
	@Column(name = "course_unt_name", updatable = false, insertable = false)
	public String unt_name;
	
	@ApiModelProperty(value = "開課學制", required = true)
	@Column(name = "course_dgr_name", updatable = false, insertable = false)
	public String dgr_name;
	
	@ApiModelProperty(value = "選課人數", required = true)
	@Column(name="scr_acptcnt", updatable = false, insertable = false)
	public String acptcnt;

	@ApiModelProperty(value = "教師職級", required = true)
	@Column(name="tit_name_list", updatable = false, insertable = false)
	public String tit_name_list;
	
	@ApiModelProperty(value = "專兼任", required = true)
	@Column(name="emp_poaddt_list", updatable = false, insertable = false)
	public String emp_poaddt_list;
	
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

	public String getCrt_name() {
		return crt_name;
	}

	public void setCrt_name(String crt_name) {
		this.crt_name = crt_name;
	}

	public Integer getScore_avg() {
		return score_avg;
	}

	public void setScore_avg(Integer score_avg) {
		this.score_avg = score_avg;
	}

	public String getTit_name_list() {
		return tit_name_list;
	}

	public void setTit_name_list(String tit_name_list) {
		this.tit_name_list = tit_name_list;
	}

	public String getEmp_poaddt_list() {
		return emp_poaddt_list;
	}

	public void setEmp_poaddt_list(String emp_poaddt_list) {
		this.emp_poaddt_list = emp_poaddt_list;
	}
	
	

}
