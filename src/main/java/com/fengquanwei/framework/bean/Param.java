package com.fengquanwei.framework.bean;

import com.fengquanwei.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数
 *
 * @author fengquanwei
 * @create 2017/11/17 20:39
 **/
public class Param {
    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if (formParamList != null && formParamList.size() > 0) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if (fileParamList != null && fileParamList.size() > 0) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileList;
                if (fileMap.containsKey(fieldName)) {
                    fileList = fileMap.get(fieldName);
                } else {
                    fileList = new ArrayList<>();
                }
                fileList.add(fileParam);
                fileMap.put(fieldName, fileList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileList = getFileList(fieldName);
        if (fileList != null && fileList.size() == 1) {
            return fileList.get(0);
        }

        return null;
    }

    /**
     * 根据参数名获取 String 类型参数值
     */
    public String getString(String name) {
        return (String) getFieldMap().get(name);
    }

    /**
     * 根据参数名获取 Integer 类型参数值
     */
    public Integer getInteger(String name) {
        return (Integer) getFieldMap().get(name);
    }

    /**
     * 参数是否为空
     */
    public boolean isEmpty() {
        if (formParamList != null && formParamList.size() > 0 && fileParamList != null && fileParamList.size() > 0) {
            return false;
        }

        return true;
    }
}
