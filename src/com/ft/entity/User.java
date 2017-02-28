package com.ft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity 
@Table(name = "testqqr")
public class User implements java.io.Serializable {

	// Fields
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length=20)
	private String username;
	@Column(length=20)
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*private String address;
	private Integer sex;
	private Integer phone;
	private String realname;
	private String email;
	private Integer familyId;
	private Integer fatherId;
	private Integer motherId;
	private String birthplace;*/

	// Constructors

	/*public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	*//** default constructor *//*
	public User() {
	}

	*//** full constructor *//*
	public User(String username, String passward, Integer sex, Integer phone,
			String realname, String email, Integer familyId, Integer fatherId,
			Integer motherId) {
		this.username = username;
		this.password = passward;
		this.sex = sex;
		this.phone = phone;
		this.realname = realname;
		this.email = email;
		this.familyId = familyId;
		this.fatherId = fatherId;
		this.motherId = motherId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getPhone() {
		return this.phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public Integer getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public Integer getMotherId() {
		return this.motherId;
	}

	public void setMotherId(Integer motherId) {
		this.motherId = motherId;
	}
*/
}