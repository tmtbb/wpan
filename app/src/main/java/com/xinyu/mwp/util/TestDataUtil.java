package com.xinyu.mwp.util;

import com.xinyu.mwp.constant.ActionConstant;
import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.IndexBannerEntity;
import com.xinyu.mwp.entity.MyPushOrderEntity;
import com.xinyu.mwp.entity.MyPushOrderItemEntity;
import com.xinyu.mwp.entity.MyShareOrderEntity;
import com.xinyu.mwp.entity.MyShareOrderItemEntity;
import com.xinyu.mwp.entity.RechargeRecordEntity;
import com.xinyu.mwp.entity.RechargeRecordItemEntity;
import com.xinyu.mwp.entity.UnitEntity;
import com.xinyu.mwp.entity.UserAssetsEntity;
import com.xinyu.mwp.entity.UserAssetsItemEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;

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

    public static List<RechargeRecordEntity> getRechargeRecordEntities() {
        List<RechargeRecordEntity> rrEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RechargeRecordEntity entity = new RechargeRecordEntity();
            entity.setTime("2017年" + (i + 1) + "月");
            List<RechargeRecordItemEntity> itemEntities = new ArrayList<>();
//            for (int j = 0; j < 30; j++) {
//                RechargeRecordItemEntity itemEntity = new RechargeRecordItemEntity();
//                itemEntity.setMoney(String.valueOf(1999 + i));
////                itemEntity.setStatus(String.valueOf(new Random().nextInt(2)));
//                itemEntity.setIcon(ImageUtil.getRandomUrl());
//                itemEntity.setInfo("招商银行  尾号8898");
//                itemEntity.setTime("11:23:12");
//                itemEntity.setTimeDate("11-11");
//                itemEntity.setTimeWeek("周五");
//                itemEntities.add(itemEntity);
//            }
            entity.setInfo(itemEntities);
            rrEntities.add(entity);
        }
        return rrEntities;
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

    //模拟的 我的/好友 推单详情数据
    public static List<MyPushOrderItemEntity> getMyPushOrderInfoEntity() {
        MyPushOrderItemEntity itemEntity;
        List<MyPushOrderItemEntity> orderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemEntity = new MyPushOrderItemEntity();
            itemEntity.setOrderNname("上海-法兰克福" + new Random().nextInt(10));
            itemEntity.setTime("17-01-25 17:" + new Random().nextInt(60));
            itemEntity.setOrderPrice("" + new Random().nextInt(10000));
            orderList.add(itemEntity);
        }
        return orderList;
    }

    public static List<IndexBannerEntity> getIndexBannersInfo() {
        List<IndexBannerEntity> banners = new ArrayList<>();
        IndexBannerEntity banner1 = new IndexBannerEntity();
        banner1.setImg(ImageUtil.FLIGHT_INFO1);
        banner1.setJumpCategory(ActionConstant.JumpCategory.INDEX_POSITION_INFO);
        banner1.setJumpType("1");
        banner1.setTitle("交易规则");
        banner1.setJumpUrl(ActionConstant.fileName1);
        banners.add(banner1);

        IndexBannerEntity banner2 = new IndexBannerEntity();
        banner2.setImg(ImageUtil.FLIGHT_INFO2);
        banner2.setJumpCategory(ActionConstant.JumpCategory.INDEX_POSITION_INFO);
        banner2.setJumpType("2");
        banner2.setTitle("航班信息");
        banner2.setJumpUrl(ActionConstant.fileName2);
        banners.add(banner2);
        return banners;
    }

    /**
     * 请求余额
     */
    public static void requestBalance() {
        NetworkAPIFactoryImpl.getUserAPI().balance(new OnAPIListener<BalanceInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(BalanceInfoEntity balanceInfoEntity) {
                UserManager.getInstance().getUserEntity().setBalance(balanceInfoEntity.getBalance());
            }
        });
    }
}
