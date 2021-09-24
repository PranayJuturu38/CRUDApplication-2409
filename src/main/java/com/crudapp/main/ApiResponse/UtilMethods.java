package com.crudapp.main.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UtilMethods {

    public ResponseEntity<Object> modifyResponseObject(Object obj, String message) {
		if (obj instanceof ArrayList<?>) {
			if (((ArrayList<?>) obj).size() == 1) {
				obj = ((ArrayList<?>) obj).get(0);
			} else if (!checkIfListEmptyOrNot((List<?>) obj)) {
				obj = new HashMap<>();
			}
		} else if (obj == null) {
			obj = new HashMap<>();
		}
		Response response = new Response();
		response.setResults(obj);
		response.setMessage(message != null && !message.isEmpty() ? message : null);
		return new ResponseEntity<Object>(response,HttpStatus.OK) ;
	}
    public boolean checkIfListEmptyOrNot(List<?> checkList) {
		return checkList != null && !checkList.isEmpty();
	}
}
