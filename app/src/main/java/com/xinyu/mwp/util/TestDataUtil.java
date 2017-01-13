package com.xinyu.mwp.util;

import com.xinyu.mwp.entity.IndexBannerEntity;
import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.entity.MyShareOrderEntity;
import com.xinyu.mwp.entity.MyShareOrderItemEntity;
import com.xinyu.mwp.entity.UnitEntity;
import com.xinyu.mwp.entity.UserAssetsEntity;
import com.xinyu.mwp.entity.UserAssetsItemEntity;

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

    public static MyShareOrderEntity getMyShareOrderEntity() {
        MyShareOrderEntity entity = new MyShareOrderEntity();
        entity.setDayCount(String.valueOf(new Random().nextInt(99)));
        entity.setWeekCount(String.valueOf(new Random().nextInt(99)));
        entity.setMonthCount(String.valueOf(new Random().nextInt(99)));

        List<MyShareOrderItemEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyShareOrderItemEntity itemEntity = new MyShareOrderItemEntity();
            itemEntity.setName("Name - " + i);
            itemEntity.setStatus(String.valueOf(new Random().nextInt(2)));
            itemEntity.setCreateDepotPrice(String.valueOf(new Random().nextInt(9999) + 1));
            itemEntity.setCreateDepotTime("16-11-16 17:40");
            itemEntity.setFlatDepotPrice(String.valueOf(new Random().nextInt(9999) + 1));
            itemEntity.setFlatDepotTime("15-11-22 08:21");
            if (i % 2 == 0) {
                itemEntity.setProfit(String.valueOf(-(new Random().nextInt(9999) + 1)));
            } else {
                itemEntity.setProfit(String.valueOf(new Random().nextInt(9999) + 1));
            }
            list.add(itemEntity);
        }

        entity.setShareOrders(list);
        return entity;
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

    public static UserAssetsEntity getUserAssetsEntity() {
        UserAssetsEntity entity = new UserAssetsEntity();
        entity.setMoney("188888.00");
        List<UserAssetsItemEntity> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            UserAssetsItemEntity itemEntity = new UserAssetsItemEntity();
            itemEntity.setTime("2017.1.01");
            if (i % 5 == 0) {
                itemEntity.setTimeTag(i + "月");
            }

            if (i == 1) {
                itemEntity.setSilverPrice(String.valueOf(-(new Random().nextInt(99) + 1)));
            } else if (i == 2) {
                itemEntity.setCafePrice(String.valueOf(new Random().nextInt(99) + 1));
            } else {
                itemEntity.setOilPrice(String.valueOf(new Random().nextInt(99) + 1));
            }
            list.add(itemEntity);
        }
        entity.setAssets(list);
        return entity;
    }

    public static List<IndexBannerEntity> getIndexBanners(int size) {
        List<IndexBannerEntity> banners = new ArrayList<>();
        for (int i = 0; i < size; i++) {

            IndexBannerEntity banner = new IndexBannerEntity();
            banner.setJumpType("1");
            banner.setJumpCategory(String.valueOf(i));


            if (i == 0)// no skip
            {
                banner.setJumpType("0");
            }


            if (i == 1) { //game
                banner.setJumpType("1");
                banner.setJumpId("19");
            }

            if (i == 2) { //gift
                banner.setJumpType("1");
                banner.setJumpId("37");
            }
            if (i == 3) { //sociaty
                banner.setJumpType("1");
                banner.setJumpId("20");
            }

            if (i == 4) { //web
                banner.setJumpType("2");
                banner.setJumpUrl("http://baidu.com");
            }
            banner.setImg(ImageUtil.getRandomUrl());
            banner.setLocation(String.valueOf(i));
            banner.setTitle("title" + i);
            banner.setContent("content" + i);
            banner.setName("name" + i);
            banner.setGname("gname" + i);
            banners.add(banner);
        }
        return banners;
    }
}
