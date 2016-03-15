package com.example.util;

import com.alibaba.fastjson.JSON;

/**
 * @author ben
 *
 */
public class JSONResponce {

	/**
	 */
	public int code;
	/**
	 */
	public String message;

	/**
	 */
	public Object data;

	public JSONResponce(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public JSONResponce(int code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public JSONResponce() {

	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
