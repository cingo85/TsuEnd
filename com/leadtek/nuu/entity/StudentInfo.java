package com.leadtek.nuu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "studentinfo")
@ApiModel(description = "學生相關")
@Entity
@IdClass(StudentInfo.class)
public class StudentInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "學號", required = true)
	@Column(name = "SID", updatable = false, insertable = false)
	public String SID;
	
	@ApiModelProperty(value = "畢業學校代碼", required = true)
	@Column(name = "std_xsh_id", updatable = false, insertable = false)
	public String std_xsh_id;

	@ApiModelProperty(value = "畢業學校名稱", required = true)
	@Column(name = "std_xsh_name", updatable = false, insertable = false)
	public String std_xsh_name;

	@ApiModelProperty(value = "畢業學校縣市", required = true)
	@Column(name = "std_xsh_city", updatable = false, insertable = false)
	public String std_xsh_city;
	
	
	
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
	
	
	@ApiModelProperty(value = "入學管道細項", required = true)
	@Column(name = "st3_name", updatable = false, insertable = false)
	public String st3_name;
	
//	@ApiModelProperty(value = "身分別", required = true)
//	@Column(name = "unt_name", updatable = false, insertable = false)
//	public String unt_name;
//	
	@ApiModelProperty(value = "性別", required = true)
	@Column(name = "std_sex", updatable = false, insertable = false)
	public String std_sex;
	
	@ApiModelProperty(value = "lat", required = true)
	@Column(name = "lat", updatable = false, insertable = false)
	public String lat;
	
	@ApiModelProperty(value = "lng", required = true)
	@Column(name = "lng", updatable = false, insertable = false)
	public String lng;

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getStd_xsh_id() {
		return std_xsh_id;
	}

	public void setStd_xsh_id(String std_xsh_id) {
		this.std_xsh_id = std_xsh_id;
	}

	public String getStd_xsh_name() {
		return std_xsh_name;
	}

	public void setStd_xsh_name(String std_xsh_name) {
		this.std_xsh_name = std_xsh_name;
	}

	public String getStd_xsh_city() {
		return std_xsh_city;
	}

	public void setStd_xsh_city(String std_xsh_city) {
		this.std_xsh_city = std_xsh_city;
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

	public String getSt3_name() {
		return st3_name;
	}

	public void setSt3_name(String st3_name) {
		this.st3_name = st3_name;
	}
	
	
}
