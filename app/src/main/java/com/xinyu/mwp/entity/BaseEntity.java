package com.xinyu.mwp.entity;



import com.xinyu.mwp.util.JSONEntityUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseEntity implements Serializable {


    @Override
    public String toString() {

        return JSONEntityUtil.EntityToJSON(this).toString();
    }

    public <entity extends BaseEntity> entity copy() {
        try {
            Class<? extends BaseEntity> classType = this.getClass();
            BaseEntity baseEntity = classType.newInstance();
            baseEntity.update(this);
            return (entity) baseEntity;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(BaseEntity entity) {
        if (entity != null && (entity.getClass().equals(this.getClass()))) {
            Class<? extends BaseEntity> classType = this.getClass();
            List<Field> fields = JSONEntityUtil.getFields(classType);
            for (Field field : fields) {
                updateField(field, entity);
            }
        }
    }

    private void updateField(Field field, BaseEntity entity) {

        if (field != null && entity != null) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }

    }
}
