package com.ira.utils;

import com.ira.beans.Category;

public class CategoryUtil {

	public static String CategoryConvert(Category category) {

		switch (category) {
		case FOOD:
			return "FOOD";
		case ELECTRICITY:
			return "ELECTRICITY";
		case RESTAURANT:
			return "RESTAURANT";
		case VACATION:
			return "VACATION";

		}
		return null;
	}
	
	public static int CategoryConvertString(String category) {

		switch (category) {
		case "FOOD":
			return 1;
		case "ELECTRICITY":
			return 2;
		case "RESTAURANT":
			return 3;
		case "VACATION":
			return 4;

		}
		return 0;
	}
	public static int CategoryConvertInt(Category category) {

		switch (category) {
		case FOOD:
			return 1;
		case ELECTRICITY:
			return 2;
		case RESTAURANT:
			return 3;
		case VACATION:
			return 4;

		}
		return 0;
	}
	
	
}