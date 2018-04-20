package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.GoodsDetailsBean;
import com.cj.reocrd.model.entity.OrderBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Lyndon.Li on 2018/4/3.
 */

public class GoodsDetailPresenter extends GoodsDetailContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getGoodsDetail(String goodsID) {
        baseMap.clear();
        baseMap.put("uid", BaseActivity.uid);
        baseMap.put("id",goodsID);
        baseMap.put("pagesize","20");
        baseMap.put("pageno","0");
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_GOODS_DETAIL, baseMap, GoodsDetailsBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        GoodsDetailsBean goodsDetailsBean = (GoodsDetailsBean) apiResponse.getResults();
                        mView.onSuccess(goodsDetailsBean);
                    }else{
                        mView.onFailureMessage(apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }

    @Override
    public void addToCart(String uid, String sid, int num, String goodsID) {
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("mid",goodsID);
        baseMap.put("sid",sid);
        baseMap.put("num",num);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ADD_TO_CART, baseMap,null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    mView.onFailureMessage(apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }

    /**
     * @param uid  用户id
     * @param mid  商品id
     * @param sid  规格id
     * @param num  数量
     */
    @Override
    public void orderByDetail(String uid, String mid, String sid, int num) {
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("mid",mid);
        baseMap.put("sid",sid);
        baseMap.put("num",num);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ORDER_FROM_DETAIL, baseMap, OrderBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    mView.acticonToSubmitOrder(apiResponse); //{"consignee":"新增3","phone":"18825142536","fuladdress":"湖北省荆州市荆州区湖北人","oid":"9fd17ffc-f146-44ad-8d26-19fa0abfad8d","message":"操作成功","aid":"湖北省荆州市荆州区湖北人","statusCode":"1"}
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }

    @Override
    public void collectGoods(String uid, String mid) {
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("mid",mid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_COLLECT_GOODS, baseMap,null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.setCollectImg(true);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }


}
