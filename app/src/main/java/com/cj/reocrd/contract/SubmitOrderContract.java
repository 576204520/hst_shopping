package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.OrderDetail;


/**
 * Created by Lyndon.Li on 2018/4/09
 * 全部商品
 */

public interface SubmitOrderContract {

    interface View extends BaseView {
        //返回获取的数据
        void updateOrderInfo(OrderDetail orderDetail);

    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getOrderDetail(String oid);
        public abstract void updateOrderAddress(String oid ,String aid);
    }
}
