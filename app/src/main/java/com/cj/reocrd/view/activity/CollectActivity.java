package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.CollectAdapter;
import com.cj.reocrd.view.adapter.FriendsAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/20.
 */

public class CollectActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View,
        BaseQuickAdapter.OnBaseQuickAdapterItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.recycler_content)
    RecyclerView recyclerContent;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.collect_search)
    TextView coolectSearch;

    List<GoodsBean> mDatas;

    private CollectAdapter collectAdapter;
    private int size = 10;  //pageSize
    private int pageno = 0; // 页码
    private int type;
    private final static String TAG = "CollectActivity";
    private boolean loading = false;
    private String from;

    private int position;//删除的项

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from");
        if (!TextUtils.isEmpty(from)) {
            type = 1;
            if ("collect".equals(from)) {
                mPresenter.collectList(uid, size, pageno);
                coolectSearch.setVisibility(View.VISIBLE);
            }
            if ("history".equals(from)) {
                mPresenter.collectBrowse(uid, size, pageno);
                coolectSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.collect_title));
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
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


    @Override
    public void onAdapterItemClickListener(View view, int position) {
        switch (view.getId()) {
            case R.id.collect_delete:
                type = 2;
                this.position = position;
                mPresenter.collectDelete(uid, mDatas.get(position).getId());
                break;
            case R.id.collect_car:
                break;
        }
    }

    public void initList() {
        if ("collect".equals(from)) {
            collectAdapter = new CollectAdapter(R.layout.item_collect, mDatas, true);
        }
        if ("history".equals(from)) {
            collectAdapter = new CollectAdapter(R.layout.item_collect, mDatas, false);
        }
        //设置适配器可以上拉加载
        collectAdapter.setOnBaseAdapterItemClickListener(this);
        collectAdapter.setOnLoadMoreListener(this);
        recyclerContent.setLayoutManager(new LinearLayoutManager(this));
        recyclerContent.setAdapter(collectAdapter);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    HomeBean homeBean = (HomeBean) response.getResults();
                    if (homeBean.getMlist() != null && homeBean.getMlist().size() > 0) {
                        if (loading) {
                            loading = false;
                            collectAdapter.addData(homeBean.getMlist());
                            refreshLayout.endLoadingMore();
                        } else {
                            mDatas = homeBean.getMlist();
                            initList();
                        }
                    }
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    collectAdapter.remove(position);
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(this, msg);
    }

    @OnClick({R.id.title_left, R.id.collect_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.collect_search:

                break;
        }

    }

    @Override
    public void onLoadMoreRequested() {
        loading = true;
        pageno++;
        type = 1;
        if ("collect".equals(from)) {
            mPresenter.collectList(uid, size, pageno);
        }
        if ("history".equals(from)) {
            mPresenter.collectBrowse(uid, size, pageno);
        }
    }
}
