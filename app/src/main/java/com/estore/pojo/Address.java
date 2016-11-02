package com.estore.pojo;

import java.io.Serializable;

public class Address implements Serializable {
	public int addressId;
	public int userId;
	public String cantactName;

	public Address() {
	}

	public String cantactPhone;

	public String contactAddress;
	public boolean isDefault;
	public String detailed_address;// 详细地址

	public Address(int addressId, int userId, String cantactName,
			String cantactPhone, String contactAddress, boolean isDefault) {
		super();
		this.addressId = addressId;
		this.userId = userId;
		this.cantactName = cantactName;
		this.cantactPhone = cantactPhone;
		this.contactAddress = contactAddress;
		this.isDefault = isDefault;
	}

	public Address( int userId, String cantactName,
			String cantactPhone, String contactAddress, boolean isDefault,
			String detailedAddress) {
		super();
	
		this.userId = userId;
		this.cantactName = cantactName;
		this.cantactPhone = cantactPhone;
		this.contactAddress = contactAddress;
		this.isDefault = isDefault;
		this.detailed_address = detailedAddress;
	}

	@Override
	public String toString() {
		return "Address{" +
				"addressId=" + addressId +
				", userId=" + userId +
				", cantactName='" + cantactName + '\'' +
				", cantactPhone='" + cantactPhone + '\'' +
				", contactAddress='" + contactAddress + '\'' +
				", isDefault=" + isDefault +
				", detailed_address='" + detailed_address + '\'' +
				'}';
	}

	public String getCantactName() {
		return cantactName;
	}

	public void setCantactName(String cantactName) {
		this.cantactName = cantactName;
	}

	public String getCantactPhone() {
		return cantactPhone;
	}

	public void setCantactPhone(String cantactPhone) {
		this.cantactPhone = cantactPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getDetailed_address() {
		return detailed_address;
	}

	public void setDetailed_address(String detailedAddress) {
		detailed_address = detailedAddress;
	}

}
