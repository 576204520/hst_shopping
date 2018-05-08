package com.cj.reocrd.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.YongJINBean;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class YongJinActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.tv_xs)
    TextView tvXs;
    @BindView(R.id.tv_gl)
    TextView tvGl;
    @BindView(R.id.tv_dl)
    TextView tvDl;
    @BindView(R.id.tv_jq)
    TextView tvJq;
    @BindView(R.id.tv_cj)
    TextView tvCj;
    @BindView(R.id.tv_leader)
    TextView tvLeader;
    @BindView(R.id.ll_leader)
    LinearLayout llLeader;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yongjin;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.myYongJin(uid);
    }

    @Override
    public void initView() {
        titleCenter.setText("我的收益");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            YongJINBean yj = (YongJINBean) response.getResults();
            if (yj != null) {
                tvXs.setText(yj.getXs());
                tvGl.setText(yj.getGl());
                tvDl.setText(yj.getDl());
                tvJq.setText(yj.getJq());
                tvCj.setText(yj.getCj());
                tvLeader.setText(yj.getLeader());
//                if("1".equals(yj.getIsleader())){
//                    tvLeader.setText(yj.getLeader());
//                    llLeader.setVisibility(View.VISIBLE);
//                }
            }

        } else {
            ToastUtil.showToastS(this, response.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(this, msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void acticonToSubmitOrder(ApiResponse apiResponse) {

    }

    @Override
    public void setCollectImg(boolean stuats) {

    }

    @Override
    public void showComment(List<GoodsCommentBean> goodsCommentBeanList) {

    }

    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            default:
                break;

        }
    }

}