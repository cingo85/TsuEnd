package com.leadtek.tsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "teacherproject")
@ApiModel(description = "教師相關")
@Entity
@IdClass(TeacherProject.class)
public class TeacherProject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "教師代碼", required = true)
	@Column(name = "emp_id", updatable = false, insertable = false)
	public String emp_id;

	@Id
	@ApiModelProperty(value = "學年度", required = true)
	@Column(name = "學年度", updatable = false, insertable = false)
	public String syear;

	@ApiModelProperty(value = "專兼任", required = true)
	@Column(name = "專兼任", updatable = false, insertable = false)
	public String teacherstatus;

	/*
	 * 期刊論文發表數
	 */

	@ApiModelProperty(value = "本學期教師計畫案次數", required = true)
	@Column(name = "project_c_times", updatable = false, insertable = false)
	public Integer project_c_times;

	@ApiModelProperty(value = "本學期教師期刊論文次數", required = true)
	@Column(name = "project_d_times", updatable = false, insertable = false)
	public Integer project_d_times;

	@ApiModelProperty(value = "本學期教師研討會論文次數", required = true)
	@Column(name = "project_e_times", updatable = false, insertable = false)
	public Integer project_e_times;

	/*
	 * 專業技術服務/學術活動數
	 */
	@ApiModelProperty(value = "本學期教師專業技術服務次數", required = true)
	@Column(name = "project_a_times", updatable = false, insertable = false)
	public Integer project_a_times;

	@ApiModelProperty(value = "本學期教師學術活動次數", required = true)
	@Column(name = "project_b_times", updatable = false, insertable = false)
	public Integer project_b_times;

	/*
	 * 各學年教師著作/專利/獲獎數
	 */

	@ApiModelProperty(value = "本學期教師學術活動次數", required = true)
	@Column(name = "project_f_times", updatable = false, insertable = false)
	public Integer project_f_times;

	@ApiModelProperty(value = "本學期教師學術活動次數", required = true)
	@Column(name = "project_g_times", updatable = false, insertable = false)
	public Integer project_g_times;

	@ApiModelProperty(value = "本學期教師學術活動次數", required = true)
	@Column(name = "project_h_times", updatable = false, insertable = false)
	public Integer project_h_times;

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getSyear() {
		return syear;
	}

	public void setSyear(String syear) {
		this.syear = syear;
	}

	public String getTeacherstatus() {
		return teacherstatus;
	}

	public void setTeacherstatus(String teacherstatus) {
		this.teacherstatus = teacherstatus;
	}

	public Integer getProject_c_times() {
		return project_c_times;
	}

	public void setProject_c_times(Integer project_c_times) {
		this.project_c_times = project_c_times;
	}

	public Integer getProject_d_times() {
		return project_d_times;
	}

	public void setProject_d_times(Integer project_d_times) {
		this.project_d_times = project_d_times;
	}

	public Integer getProject_e_times() {
		return project_e_times;
	}

	public void setProject_e_times(Integer project_e_times) {
		this.project_e_times = project_e_times;
	}

	public Integer getProject_a_times() {
		return project_a_times;
	}

	public void setProject_a_times(Integer project_a_times) {
		this.project_a_times = project_a_times;
	}

	public Integer getProject_b_times() {
		return project_b_times;
	}

	public void setProject_b_times(Integer project_b_times) {
		this.project_b_times = project_b_times;
	}

	public Integer getProject_f_times() {
		return project_f_times;
	}

	public void setProject_f_times(Integer project_f_times) {
		this.project_f_times = project_f_times;
	}

	public Integer getProject_g_times() {
		return project_g_times;
	}

	public void setProject_g_times(Integer project_g_times) {
		this.project_g_times = project_g_times;
	}

	public Integer getProject_h_times() {
		return project_h_times;
	}

	public void setProject_h_times(Integer project_h_times) {
		this.project_h_times = project_h_times;
	}

}
