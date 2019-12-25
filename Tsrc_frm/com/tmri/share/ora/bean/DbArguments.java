package com.tmri.share.ora.bean;

public class DbArguments {
	private String object_name;
	private String package_name;
	private String argument_name;
	private String position;
	private String data_type;
	private String in_out;

	public String getIn_out() {
		return in_out;
	}

	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}

	public String getObject_name() {
		return object_name;
	}

	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getArgument_name() {
		return argument_name;
	}

	public void setArgument_name(String argument_name) {
		this.argument_name = argument_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

}
