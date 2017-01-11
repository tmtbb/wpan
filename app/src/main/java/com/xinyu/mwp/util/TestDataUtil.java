package com.xinyu.mwp.util;

import com.xinyu.mwp.entity.UnitEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 17/1/11.
 */

public class TestDataUtil {

    public static List<UnitEntity> getUnitEntities() {
        List<UnitEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setId(String.valueOf(i));
            unitEntity.setName("Name - " + i);
            unitEntity.setIcon(ImageUtil.getRandomUrl());
            list.add(unitEntity);
        }
        return list;
    }
}
