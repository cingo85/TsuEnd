package com.leadtek.tsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "studentrest")
@ApiModel(description = "休學")
@Entity
@IdClass(StudentRest.class)
public class StudentRest implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty(value = "休學原因", required = true)
	@Column(name = "rest_reason_kind", updatable = false, insertable = false)
	public String rest_reason_kind;
	
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
	
//	@ApiModelProperty(value = "身分別", required = true)
//	@Column(name = "unt_name", updatable = false, insertable = false)
//	public String unt_name;
//	
	@ApiModelProperty(value = "性別", required = true)
	@Column(name = "std_sex", updatable = false, insertable = false)
	public String std_sex;

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getYms_year() {
		return yms_year;
	}

	public void setYms_year(String yms_year) {
		this.yms_year = yms_year;
	}



	public String getRest_reason_kind() {
		return rest_reason_kind;
	}

	public void setRest_reason_kind(String rest_reason_kind) {
		this.rest_reason_kind = rest_reason_kind;
	}



	public String getYms_sms() {
		return yms_sms;
	}

	public void setYms_sms(String yms_sms) {
		this.yms_sms = yms_sms;
	}

	public Integer getStd_year() {
		return std_year;
	}

	public void setStd_year(Integer std_year) {
		this.std_year = std_year;
	}

	public String getDpt_name() {
		return dpt_name;
	}

	public void setDpt_name(String dpt_name) {
		this.dpt_name = dpt_name;
	}

	public String getUnt_name() {
		return unt_name;
	}

	public void setUnt_name(String unt_name) {
		this.unt_name = unt_name;
	}

	public String getDgr_name() {
		return dgr_name;
	}

	public void setDgr_name(String dgr_name) {
		this.dgr_name = dgr_name;
	}

	public String getSt2_name() {
		return st2_name;
	}

	public void setSt2_name(String st2_name) {
		this.st2_name = st2_name;
	}

	public String getStd_sex() {
		return std_sex;
	}

	public void setStd_sex(String std_sex) {
		this.std_sex = std_sex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
