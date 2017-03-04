package com.ft.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity 
@Table(name = "user")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length=20)
	private String username;
	@Column(length=20)
	private String password;
	@Column(length=20)
	private String firstname;
	@Column(length=20)
	private String lastname;
	@Column(length=8)
	private Date birthday;
	@Column(length=11)
	private String telephone;
	@Column(length=20)
	private String nativeplace;
	@Column(length=20)
	private String location;
	@Column(length=4)
	private boolean register;
	@Column(length=4)
	private boolean live;
	@ManyToOne
	@JoinColumn(name="familyId")
	private Family family;
	@Column
	private Integer fatherId;
	@Column
	private Integer motherId;
	@Column
	private String img_url;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isRegister() {
		return register;
	}
	public void setRegister(boolean register) {
		this.register = register;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public Family getFamilyId() {
		return family;
	}
	public void setFamilyId(Family family) {
		this.family = family;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public Integer getMotherId() {
		return motherId;
	}
	public void setMotherId(Integer motherId) {
		this.motherId = motherId;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

}