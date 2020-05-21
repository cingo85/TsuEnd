package com.leadtek.tsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "semesterorder")
@ApiModel(description = "學生相關")
@Entity
@IdClass(SemesterOrder.class)
public class SemesterOrder  implements java.io.Serializable{
	
	@Id
	@ApiModelProperty(value = "學號", required = true)
	@Column(name = "SID", updatable = false, insertable = false)
	public String SID;
	
	@ApiModelProperty(value = "學年", required = true)
	@Column(name = "yms_year", updatable = false, insertable = false)
	public String yms_year;
	
	@ApiModelProperty(value = "學期", required = true)
	@Column(name = "yms_sms", updatable = false, insertable = false)
	public String yms_sms;
	
	@ApiModelProperty(value = "學年學期", required = true)
	@Column(name = "yms_year_sms", updatable = false, insertable = false)
	public String yms_year_sms;
	
	@ApiModelProperty(value = "學期平均成績", required = true)
	@Column(name = "src_tot_avg", updatable = false, insertable = false)
	public Double src_tot_avg;

	@ApiModelProperty(value = "入學年", required = true)
	@Column(name = "std_year", updatable = false, insertable = false)
	public Integer std_year;
	
	@ApiModelProperty(value = "學院", required = true)
	@Column(name = "dpt_name", updatable = false, insertable = false)
	public String dpt_name;
	
	@ApiModelProperty(value = "系所", required = true)
	@Column(name = "unt_name", updatable = false, insertable = false)
	public String unt_name;
	
	@ApiModelProperty(value = "學制", required = true)
	@Column(name = "dgr_name", updatable = false, insertable = false)
	public String dgr_name;
	
	@ApiModelProperty(value = "入學管道", required = true)
	@Column(name = "st2_name", updatable = false, insertable = false)
	public String st2_name;

	@ApiModelProperty(value = "性別", required = true)
	@Column(name = "std_sex", updatable = false, insertable = false)
	public String std_sex;


	public String getYms_year() {
		return yms_year;
	}

	public void setYms_year(String yms_year) {
		this.yms_year = yms_year;
	}

	public String getDgr_name() {
		return dgr_name;
	}

	public void setDgr_name(String dgr_name) {
		this.dgr_name = dgr_name;
	}

	public String getUnt_name() {
		return unt_name;
	}

	public void setUnt_name(String unt_name) {
		this.unt_name = unt_name;
	}

	

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getDpt_name() {
		return dpt_name;
	}

	public void setDpt_name(String dpt_name) {
		this.dpt_name = dpt_name;
	}

	public String getStd_sex() {
		return std_sex;
	}

	public void setStd_sex(String std_sex) {
		this.std_sex = std_sex;
	}



	public Double getSrc_tot_avg() {
		return src_tot_avg;
	}

	public void setSrc_tot_avg(Double src_tot_avg) {
		this.src_tot_avg = src_tot_avg;
	}

	public String getYms_year_sms() {
		return yms_year_sms;
	}

	public void setYms_year_sms(String yms_year_sms) {
		this.yms_year_sms = yms_year_sms;
	}

	public String getSt2_name() {
		return st2_name;
	}

	public void setSt2_name(String st2_name) {
		this.st2_name = st2_name;
	}

	public Integer getStd_year() {
		return std_year;
	}

	public void setStd_year(Integer std_year) {
		this.std_year = std_year;
	}

	
	
	

}
