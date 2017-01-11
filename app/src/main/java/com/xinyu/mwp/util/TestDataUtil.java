package com.xinyu.mwp.util;

import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.entity.UnitEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static MyPushOrderEntity getMyPushOrderEntity() {
        MyPushOrderEntity entity = new MyPushOrderEntity();
        entity.setDayCount(String.valueOf(new Random().nextInt(99)));
        entity.setWeekCount(String.valueOf(new Random().nextInt(99)));
        entity.setMonthCount(String.valueOf(new Random().nextInt(99)));

        List<MyPushOrderItemEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MyPushOrderItemEntity itemEntity = new MyPushOrderItemEntity();
            itemEntity.setSucCount(String.valueOf(new Random().nextInt(99)));
            if (i == 1) {
                itemEntity.setProfit(String.valueOf(-(new Random().nextInt(99) + 1)));
            } else {
                itemEntity.setProfit(String.valueOf(new Random().nextInt(99) + 1));
            }
            if (i == 1) {
                itemEntity.setSucOdds(String.valueOf(-(new Random().nextInt(99) + 1)));
            } else {
                itemEntity.setSucOdds(String.valueOf(new Random().nextInt(99) + 1));
            }
            list.add(itemEntity);
        }

        entity.setPushOrders(list);
        return entity;
    }
}
