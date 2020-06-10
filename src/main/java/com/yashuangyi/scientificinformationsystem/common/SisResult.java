package com.yashuangyi.scientificinformationsystem.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;

import java.util.List;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 1:22
 */
public class SisResult {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer code;

    /**
     * 响应消息
     */

    private String msg;

    // 响应中的数据
    private Long count;

    /**
     * 响应中的数据
     */
    private Object data;

    public static SisResult build(Integer code, String msg, Object data) {
        return new SisResult(code, msg, data);
    }

    public static SisResult ok(Object data) {
        return new SisResult(data);
    }

    public static SisResult ok() {
        return new SisResult(null);
    }

    public static SisResult getTable(Object data){
        return new SisResult(data,0);
    }

    public SisResult(Object data, int code){
        if(data instanceof PageInfo){
            this.data = ((PageInfo) data).getRows();
            this.count = ((PageInfo) data).getTotal();
        }else{
            this.data = data;
        }
        this.code = 0;
        this.msg = "OK";
    }

    public SisResult() {

    }

    public static SisResult build(Integer code, String msg) {
        return new SisResult(code, msg, null);
    }

    public SisResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SisResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为SisResult对象
     *
     * @param jsonData json数据
     * @param clazz    EmrResult中的object类型
     * @return
     */
    public static SisResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, SisResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static SisResult format(String json) {
        try {
            return MAPPER.readValue(json, SisResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static SisResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static SisResult build(ResultEnum resultEnum){
        return new SisResult(resultEnum.getCode(),resultEnum.getMessage(),null);
    }
}
