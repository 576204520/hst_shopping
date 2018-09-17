package com.cj.reocrd.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.entity.AppInfo;
import com.cj.reocrd.model.entity.BannerData;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.HomePresenter;
import com.cj.reocrd.utils.GlideImageLoader;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.UpdateUtil;
import com.cj.reocrd.view.activity.CollectActivity;
import com.cj.reocrd.view.activity.GoodDetailActivity;
import com.cj.reocrd.view.activity.SearchActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.adapter.OnRecyclerItemClickListener;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/3/16.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
        , BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate, OnBannerListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.fl_home_banner)
    FrameLayout flHomeBannerLyout;
    List<String> images;
    private List<GoodsBean> goodsBeanList = new ArrayList<>();

    private HomeAdapter mHomeTabAdapter;
    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private final static String TAG = "HomeFragment";
    private Bundle goodBundle = new Bundle();
    private List<BannerData> bannerData;
    private boolean isCancle = false;
    private AppInfo appInfo;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        super.initData();
        // todo 检查更新
//        isCancle = (boolean) SPUtils.get(mActivity,SPUtils.SpKey.UPDATE_IS_CANCLE,false);
//        mPresenter.checkUpdate(String.valueOf(UpdateUtil.getVerCode(mActivity)));
        images = new ArrayList<>();
        images.add("http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        images.add("http://pic28.photophoto.cn/20130827/0005018362405048_b.jpg");
        images.add("http://pic28.photophoto.cn/20130827/0005018371946994_b.jpg");
    }

    @Override
    public void initView() {
        LogUtil.e(TAG, "initview");
        titleLeft.setVisibility(View.GONE);
        titleCenter.setText(getString(R.string.home));
        titleRight.setBackgroundResource(R.mipmap.gouwuche);
        titleRight.setVisibility(View.VISIBLE);

        initRecycleView();
    }

    @Override
    public void getArgumentData(Bundle arguments) {
        super.getArgumentData(arguments);
//        ToastUtil.showShort(arguments.getString("key"));
    }

    @Override
    public void putArgumentData(BaseFragment baseFragment, int position) {
        super.putArgumentData(this, position);
        Bundle b = new Bundle();
        b.putCharSequence("key", position + "; This is Home Fragment");
        baseFragment.setArguments(b);
    }

    private void setBannerView(List<String> images) {
        //轮播图
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(this);
        banner.start();
    }

    private void initRecycleView() {
        mHomeTabAdapter = new HomeAdapter(R.layout.item_good, null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mHomeTabAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mHomeTabAdapter);
        //设置适配器可以上拉加载
        mHomeTabAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mActivity, true));
        recyclerViewContent.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerViewContent) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                goodBundle.clear();
                goodBundle.putString("goodsID", goodsBeanList.get(viewHolder.getAdapterPosition()).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });
//        //条目的点击事件
//        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });

        mPresenter.getHomeData(size, pageno);

    }

    @OnClick({R.id.home_search, R.id.title_right, R.id.home_index1, R.id.home_index2, R.id.home_index3, R.id.home_index4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_search:
                startActivity(SearchActivity.class);
                break;
            case R.id.title_right:
                getMainActivity().getViewPager().setCurrentItem(3);
                break;
            case R.id.home_index1:
                getMainActivity().getViewPager().setCurrentItem(1);
                break;
            case R.id.home_index2:
                getMainActivity().getViewPager().setCurrentItem(4);
                break;
            case R.id.home_index3:

                break;
            case R.id.home_index4:
                Bundle bundleCollect = new Bundle();
                bundleCollect.putString("from", "collect");
                startActivity(CollectActivity.class, bundleCollect);
                break;
        }
    }

    private void update(AppInfo appInfo) {
        if (mActivity == null || isCancle) {
            return;
        }
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(mActivity)
                .title("版本更新")
                .content("更新内容：" + "\n" + appInfo.getDetailDesc() + "\n" + "版本大小：" + appInfo.getApkSize())
                .positiveText("确定")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateUtil.downloadFile(appInfo.getApkUrl(), mActivity);
                    }
                });
        if ("2".equals(appInfo.getIsupdate())) {
            materialDialog.negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SPUtils.put(mActivity, SPUtils.SpKey.UPDATE_IS_CANCLE, true);
                            SPUtils.put(mActivity, SPUtils.SpKey.CANCLE_UPDATE_VERSION, appInfo.getVersionCode());
                        }
                    });
//            materialDialog.onNegative(new MaterialDialog.SingleButtonCallback(){
//                @Override
//                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                    mActivity.finish();
//                }
//            });
        }

        materialDialog.show();
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void onLoadMoreRequested() {
        //  加载更多 BaseQuickAdapter的上拉加载更多方法，和onRefreshLayoutBeginLoadingMore使用其中一个就可以
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        // 开始刷新
        System.out.println("onRefreshLayoutBeginRefreshing===");
//        mPresenter.getListDataTest(size,1);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        if (goodsBeanList.size() <= size * pageno) {
            pageno++;
            mPresenter.getHomeData(size, pageno);
        }
        return false;
    }

    @Override
    public void OnBannerClick(int position) {
        Bundle b = new Bundle();
        b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_HOME_BANNER);
        b.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, bannerData.get(position).getUrl());
        startActivity(WebViewActivity.class, b);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e(TAG, "onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onStart");
    }

    @Override
    public void onRefreshHomeData(HomeBean homeBean) {
        if (null != homeBean.getMlist() && homeBean.getMlist().size() > 0) {
            goodsBeanList.clear();
            goodsBeanList.addAll(homeBean.getMlist());
            if (pageno > 0) {
                mHomeTabAdapter.addData(homeBean.getMlist());
            } else {
                mHomeTabAdapter.setNewData(homeBean.getMlist());
            }
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        } else {
            ToastUtil.showShort("暂时没有商品信息");
            mHomeTabAdapter.loadComplete();
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        }
        if (null != homeBean.getBlist() && homeBean.getBlist().size() > 0) {
            images.clear();
            bannerData = homeBean.getBlist();
            for (BannerData uri : homeBean.getBlist()) {
                images.add(UrlConstants.BASE_URL + uri.getImgurl());
            }
            setBannerView(images);
        }
        mHomeTabAdapter.loadComplete();
    }

    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = mActivity.getPackageManager().canRequestPackageInstalls();
            ToastUtil.showShort("checkIsAndroidO b= " + b);
            if (b) {
//                installApk();// todo 安装应用的逻辑(写自己的就可以)
                update(appInfo);
            } else {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1);
            }
        } else {
//            installApk();
            update(appInfo);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    installApk();
                    update(appInfo);
                } else {
                    ToastUtil.showShort("onRequestPermissionsResult  Settings ");
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//                    startActivityForResult(intent, 2);
                }
                break;
            default:
                break;

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 2:
//                checkIsAndroidO();
//                break;
//
//            default:
//                break;
//        }
//    }


}
