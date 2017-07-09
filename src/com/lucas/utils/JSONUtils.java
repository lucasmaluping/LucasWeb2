package com.lucas.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lucas.domain.Customer;

public class JSONUtils {
	public String parseToJson(List<Customer> customers) {
		JSONArray jsonArray = (JSONArray) JSON.toJSON(customers);
		System.out.println(jsonArray);
		return jsonArray.toJSONString();
		
	}
}
