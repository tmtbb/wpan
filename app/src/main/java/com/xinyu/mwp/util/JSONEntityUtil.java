package com.xinyu.mwp.util;


import com.xinyu.mwp.annotation.FieldJsonKey;
import com.xinyu.mwp.entity.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONEntityUtil {

    public static <entity extends BaseEntity> entity JSONToEntity(Class<? extends BaseEntity> classType, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                BaseEntity baseEntity = classType.newInstance();
                List<Field> fields = getFields(classType);
                return JSONToEntity(baseEntity,fields,jsonObject);
            } catch (InstantiationException e) {
                e.printStackTrace();
                LogUtil.showException(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LogUtil.showException(e);
            }
        }
        return null;
    }


    public static <entity extends BaseEntity> entity JSONToEntity(BaseEntity baseEntity, List<Field> fields, JSONObject jsonObject) {
        if (jsonObject != null && fields != null) {
            for (Field field : fields) {
                setFieldValue(field, jsonObject, baseEntity);
            }
            return (entity) baseEntity;
        }
        return null;
    }

    public static <entity extends BaseEntity> entity JSONToEntity(Class<? extends BaseEntity> classType, String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return JSONToEntity(classType, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void setFieldValue(Field field, JSONObject jsonObject, Object object) {
        Object value = null;
        try {
            String keyName = field.getName();
            FieldJsonKey jsonKey = field.getAnnotation(FieldJsonKey.class);
            if (jsonKey != null)
                keyName = jsonKey.value();
            if (jsonObject.has(keyName) && !jsonObject.isNull(keyName)) {
                value = jsonObject.opt(keyName);
                if (value != null) {
                    try {
                        Class fieldClazz = field.getType();
                        field.setAccessible(true);
                        if (fieldClazz.isAssignableFrom(Long.class)) {
                            field.setLong(value, 0);
                        } else if (fieldClazz.isAssignableFrom(String.class)
                                && !value.getClass().isAssignableFrom(String.class)) {
                            field.set(object, value.toString());
                        } else {
                            if (BaseEntity.class.isAssignableFrom(fieldClazz)) {
                                try {
                                    if(value instanceof JSONObject){
                                        value = JSONToEntity(fieldClazz, (JSONObject) value);
                                    }else if(value instanceof String) {
                                        JSONObject jsonObject1 = new JSONObject(String.valueOf(value));
                                        value = JSONToEntity(fieldClazz, jsonObject1);
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            } else if (fieldClazz.isAssignableFrom(List.class)) {
                                Type fc = field.getGenericType();
                                if (fc instanceof ParameterizedType) {
                                    ParameterizedType pt = (ParameterizedType) fc;
                                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                                    if(value instanceof JSONArray) {
                                        value = JSONToEntitys(genericClazz, (JSONArray) value);
                                    }else if(value instanceof String){
                                        try {
                                            JSONArray jsonArray = new JSONArray((String) value);
                                            value = JSONToEntitys(genericClazz, jsonArray);
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                            field.set(object, value);
                        }

                    } finally {
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LogUtil.e(field.getName() + " " + value + " " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            LogUtil.e(field.getName() + " " + value + " " + e.getLocalizedMessage());
        }
    }

    public static List<Field> getFields(Class<? extends BaseEntity> classType) {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> superclass = classType;
        Field[] fields = null;
        while (superclass != null && !superclass.isInterface()) {
            if (superclass.isAssignableFrom(BaseEntity.class))
                break;
            fields = superclass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            superclass = superclass.getSuperclass();
        }
        return fieldList;
    }

    public static List<? extends BaseEntity> JSONToEntitys(Class<? extends BaseEntity> classType, JSONArray jsonArray) {
        if (jsonArray != null) {
            List<BaseEntity> entities = new ArrayList<BaseEntity>();
            List<Field> fields = getFields(classType);
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    BaseEntity baseEntity = JSONEntityUtil.JSONToEntity(classType.newInstance(),fields, jsonArray.getJSONObject(i));
                    entities.add(baseEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.showException(e);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            return entities;
        }
        return null;
    }

    public static JSONObject EntityToJSON(BaseEntity entity) {
        JSONObject json = new JSONObject();
        List<Field> fields = getFields(entity.getClass());
        if (fields != null && fields.size() > 0) {
            Object obj = null;
            for (Field field : fields) {
                try {
//                    String name = field.getName();
                    String keyName = field.getName();
                    FieldJsonKey jsonKey = field.getAnnotation(FieldJsonKey.class);
                    if (jsonKey != null)
                        keyName = jsonKey.value();
                    field.setAccessible(true);
                    obj = field.get(entity);
                    json.put(keyName, obj);

                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    LogUtil.showException(e);
                } catch (IllegalArgumentException e) {
                    LogUtil.showException(e);
                } catch (JSONException e) {
                    LogUtil.showException(e);
                }
            }
        }
        return json;
    }

}
