package de.anhquan.ordertracker.parser.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Address {

	@XStreamAlias("CustomerNo")
	private String _customerNo;
	
	@XStreamAlias("Title")
	private String _title;
	
	@XStreamAlias("FirstName")
	private String _firstName;
	
	@XStreamAlias("LastName")
	private String _lastName;
	
	@XStreamAlias("Company")
	private String _company;
	
	@XStreamAlias("Street")
	private String _street;
	
	@XStreamAlias("HouseNo")
	private String _houseNo;
	
	@XStreamAlias("AddAddress")
	private String _addAdress;
	
	@XStreamAlias("Zip")
	private String _zip;
	
	@XStreamAlias("DescriptionOfWay")
	private String _descriptionOfWay;
	
	@XStreamAlias("City")
	private String _city;
	
	@XStreamAlias("State")
	private String _state;
	
	@XStreamAlias("Country")
	private String _country;
	
	@XStreamAlias("Email")
	private String _email;
	
	@XStreamAlias("PhoneNo")
	private String _phoneNo;
	
	@XStreamAlias("Mobile")
	private String _mobile;
	
	@XStreamAlias("Fax")
	private String _fax;

	public Address(){
		
	}

	public String getCustomerNo() {
		return _customerNo;
	}

	public Address customerNo(String customerNo) {
		this._customerNo = customerNo;
		return this;
	}

	public String getTitle() {
		return _title;
	}

	public Address title(String title) {
		this._title = title;
		return this;
	}

	public String getFirstName() {
		return _firstName;
	}

	public Address firstName(String firstName) {
		this._firstName = firstName;
		return this;
	}

	public String getLastName() {
		return _lastName;
	}

	public Address lastName(String lastName) {
		this._lastName = lastName;
		return this;
	}

	public String getCompany() {
		return _company;
	}

	public Address company(String company) {
		this._company = company;
		return this;
	}

	public String getStreet() {
		return _street;
	}

	public Address street(String street) {
		this._street = street;
		return this;
	}

	public String getHouseNo() {
		return _houseNo;
	}

	public Address houseNo(String houseNo) {
		this._houseNo = houseNo;
		return this;
	}

	public String getAddAdress() {
		return _addAdress;
	}

	public Address addAdress(String addAdress) {
		this._addAdress = addAdress;
		return this;
	}

	public String getZip() {
		return _zip;
	}

	public Address zip(String zip) {
		this._zip = zip;
		return this;
	}

	public String getDescriptionOfWay() {
		return _descriptionOfWay;
	}

	public Address descriptionOfWay(String descriptionOfWay) {
		this._descriptionOfWay = descriptionOfWay;
		return this;
	}

	public String getCity() {
		return _city;
	}

	public Address city(String city) {
		this._city = city;
		return this;
	}

	public String getState() {
		return _state;
	}

	public Address setState(String state) {
		this._state = state;
		return this;
	}

	public String getCountry() {
		return _country;
	}

	public Address country(String country) {
		this._country = country;
		return this;
	}

	public String getEmail() {
		return _email;
	}

	public Address email(String email) {
		this._email = email;
		return this;
	}

	public String getPhoneNo() {
		return _phoneNo;
	}

	public Address phoneNo(String phoneNo) {
		this._phoneNo = phoneNo;
		return this;
	}

	public String getMobile() {
		return _mobile;
	}

	public Address mobile(String mobile) {
		this._mobile = mobile;
		return this;
	}

	public String getFax() {
		return _fax;
	}

	public Address fax(String fax) {
		this._fax = fax;
		return this;
	}

}
