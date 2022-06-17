package com.cj.modular.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: 自定义响应结构, 转换类
 */
@Slf4j
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data Object类型
     * @return java.lang.String
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json结果集转化为对象
     *
     * @param jsonData json字符串
     * @param beanType 数据实体对象
     * @return T
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData json字符串
     * @param beanType 数据实体对象
     * @return java.util.List<T>
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Restful API传的参数中executorParams中的单引号转为双引号，并将该参数转为pojo类
     * @param stringParam 参数字符串
     * @param beanType 数据实体对象
     * @return T
     */
    public static <T> T stringToJson(String stringParam, Class<T> beanType) {
        String[] strings = stringParam.split("");
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if ("'".equals(strings[i])) {
                strings[i] = "\"";
            }
            buffer.append(strings[i]);
        }
        String str = buffer.toString();
        return jsonToPojo(str, beanType);
    }

}
