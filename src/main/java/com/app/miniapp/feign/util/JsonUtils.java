package com.app.miniapp.feign.util;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: jsckson工具类
 * <p>DESCRIPTION:
 * <p>CALLED BY:	ZhangShihua
 * <p>CREATE DATE: 2023年5月25日
 * <p>UPDATE DATE: 2023年5月25日
 * <p>UPDATE USER:
 * <p>HISTORY:		1.0
 * @version 1.0
 * @author ZhangShihua(张士华)
 * @since java 1.8.0
 * @see
 */
@Component
public class JsonUtils {
	private static final ObjectMapper mapper;

	public static ObjectMapper getObjectMapper(){
		return mapper;
	}

	static {
		mapper = new ObjectMapper();
	}


	/**
	 * json字符串转为对象
	 *
	 * @param jsonStr
	 * @param beanType
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> beanType) throws Exception {
		return mapper.readValue(jsonStr, beanType);
	}

	/**
	 * 对象转Json字符串
	 */
	public static <T> String objectToJson(T t) throws Exception {
		return mapper.writeValueAsString(t);
	}

	/**
	 * json字符串转map
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map<String, Object> jsonToMapObj(String json)
			throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(json,Map.class);
	}

	/**
	 * json转list
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> json2List(String json, Class<T> beanClass) {
		try {

			return (List<T>)mapper.readValue(json, getCollectionType(List.class, beanClass));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * map转json
	 * @param map
	 * @return
	 */
	public static String map2Json(Map map) {
		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * list转json
	 * @param list
	 * @return
	 */
	public static String list2Json(List list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);
			if (value == null) {
				value = "";
			}
			map.put(fieldName, value);
		}
		return map;
	}
}
