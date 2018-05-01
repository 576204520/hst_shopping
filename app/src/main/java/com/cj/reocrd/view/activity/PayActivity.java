package com.cj.reocrd.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.alipay.OrderInfoUtil2_0;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.alipay.PayResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static android.provider.UserDictionary.Words.APP_ID;

public class PayActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.rb_pay_alipay)
    RadioButton rbPayAlipay;
    @BindView(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @BindView(R.id.rb_pay_other)
    RadioButton rbPayOther;
    @BindView(R.id.tv_order_over_payway)
    TextView tvOrderOverPayway;
    @BindView(R.id.tv_order_over_pirce)
    TextView tvOrderOverPirce;
    @BindView(R.id.tv_order_over_youhui)
    TextView tvOrderOverYouhui;
    @BindView(R.id.rl_order_pay_over)
    RelativeLayout rlOrderPayOver;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private String oid, orderPrice, payWay;
    private final String TYPE_ALIPAY = "1";
    private final String TYPE_WECHAT = "2";
    private final String TYPE_YUER = "3";  // 余e
    public static final String BUNDLE_KEY_OID = "oid";
    public static final String BUNDLE_KEY_PRICE = "price";

    private CountDownTimer countDownTimer;
    private long countTime = 30 * 60 * 1000;
    private int time_M = 29;
    private int time_S = 59;
    public static final String APPID = "2018042902608871";
    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJmhD7W6lp8OrP9bsFGxDOICdhdgEKCCYxZg2Sk/x0rXvMdJTwI1EFHU0YENw5aw7mJaajtDLtKetvDe1qvqKrkqSmxGq+jwE1Py7hqMa+qwOG46fZCxbG0g+E4RXt75hHFtlQr6uQDAFD0LgP+ob2QzM2c+XgOZ5fMpoJtrLCaJAgMBAAECgYBytFKqeRokwCxirO7Ez9CynuvVICDJ9xBMkRsTNU9zjgihMxcOqtf4gVG7ba9vP8N9I8zVWqAHGgG1bmwSB9DFLzb35VIm4gacB1fmHbZdE3CTPmkToeqPJXtz/MUWREp/Mj1MkIvF7JevlV1JIgqcCVP1XeAgMDdCdBPhJPXVQQJBANG+KMmx6jT1Bbw6TsGu5IqLr7YZCQz2psaqDjiwkvdLqtfi4u18q5pxxSGA9IuJLbhhuFy6tFCAs2ZmsPuvu/UCQQC7gsnRLROoMUvmyFFcTdhe0Vo4J2mN/nGJEFxOU6rHpKLbxyGuJEDt+zgKE6OR0dxjrICvl/o3dsWde46v55fFAkBF9ChibOZyVy5cgw31Z0FuO/yUvXDZVAJ3zAwGOE4sEJ6gdlm9X5FUZ5GaBQqK52vKTT1SICs3llU/WAy7RPdpAkEApgYQgWgDDpnPVaCqAStf19xPoIas/C99AVn+ENAd1zTbifMPzCuHXe9gV/O5kUQaehpjupW6018Ta/PZi8Bn7QJBAJZ0E6CTkmfdHC6inbqk+7gm1FWIy9qRHjjTG4jzyajmDdTmWc2g3MxDERuYXONLukgdrgmH9cIETH3wPZNaAcw=";

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initData() {
        super.initData();
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID);
        orderPrice = getIntent().getStringExtra(BUNDLE_KEY_PRICE);

    }

    @Override
    public void initView() {
        //
        titleCenter.setText("支付");
        tvOrderPrice.setText(ConstantsUtils.RMB + orderPrice);
        countDownTimer = new CountDownTimer(countTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_S = time_S - 1;
                if (time_S == 0) {
                    time_S = 59;
                    time_M = time_M - 1;
                }
                if (time_M == 0) {
                    countDownTimer.cancel();
                    tvTime.setText("00:00");
                } else if (time_S < 10) {
                    tvTime.setText(time_M + ":" + time_S + "0");
                } else {
                    tvTime.setText(time_M + ":" + time_S);
                }
            }

            @Override
            public void onFinish() {
                tvTime.setText("订单超时");
            }
        };

        countDownTimer.start();
    }

    @Override
    public void initPresenter() {

    }


    @OnClick({R.id.title_left, R.id.rb_pay_alipay, R.id.rb_wechat_pay, R.id.rb_pay_other
            , R.id.tv_btn_order_pay, R.id.tv_check_order_detail, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
            case R.id.title_left:
                finish();
                break;
            case R.id.rb_pay_alipay:
                payWay = TYPE_ALIPAY;
                rbPayAlipay.setChecked(true);
                rbPayOther.setChecked(false);
                rbWechatPay.setChecked(false);
                break;
            case R.id.rb_wechat_pay:
                payWay = TYPE_WECHAT;
                rbPayAlipay.setChecked(false);
                rbPayOther.setChecked(false);
                rbWechatPay.setChecked(true);
                break;
            case R.id.rb_pay_other:
                payWay = TYPE_YUER;
                rbPayAlipay.setChecked(false);
                rbPayOther.setChecked(true);
                rbWechatPay.setChecked(false);
                break;
            case R.id.tv_btn_order_pay:
                //todo  发起第三方支付未申请，暂时直接调用支付成功接口
                if (TextUtils.isEmpty(payWay)) {
                    ToastUtil.showShort("请先选择支付方式");
                } else {
                    if (payWay.equals(TYPE_ALIPAY)) {
                        payByAlipay();
                    } else if (payWay.equals(TYPE_WECHAT)) {
                        ToastUtil.showShort("未开通");
                    } else if (payWay.equals(TYPE_YUER)) {
                        inputPwdDialog();
                    }
                }
                break;
            case R.id.tv_check_order_detail:
                //todo  支付成功后，跳转到订单详情
                if (!TextUtils.isEmpty(oid)) {
                    Bundle b = new Bundle();
                    b.putString(OrderDetailActivity.BUNDLE_KEY_OID, oid);
                    startActivity(OrderDetailActivity.class, b);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 支付宝支付
     */
    private void payByAlipay() {
        //秘钥验证的类型 true:RSA2 false:RSA
        boolean rsa = false;
        //构造支付订单参数列表
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa, this);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(PayActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                //app_id=2018042902608871
                // &timestamp=2016-07-29+16%3A55%3A53
                // &biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%220501181451-1042%22%7D&method=alipay.trade.app.pay&charset=utf-8&version=15.5.3
                // &sign_type=RSA&sign=bLMlXcpdfEImFe0ZygvetFyt149OyghHLVv4Kqt3FjGNx0igi%2BtXnrzZGv%2BB2kzrJIfKcSq7s9kg%2F4%2FN4mihhsAVUC39WCbuZGbYxBh9tgaUJHe5vgfB53bWimZrW0XsKCsGIt7BrFysua3nm%2BEuqf5GmrHa7NWQyKm4U1akpvg%3D
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private  void defatureAlipay(){
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
//                APP_ID, "请复制第1步中生成的密钥中的商户应用私钥", "json", "utf-8", ,"沙箱环境RSA2支付宝公钥", "RSA2");
//        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//        request.setBizModel(model);
//        model.setOutTradeNo(System.currentTimeMills());
//        model.setTotalAmount("88.88");
//        model.setSubject("Iphone6 16G");
//        AlipayTradePrecreateResponse response = alipayClient.execute(request);
//        System.out.print(response.getBody());
//        System.out.print(response.getQrCode());
        //实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
////实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
//        AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
////SDK已经封装掉了公共参数，这里只需要传入业务参数
////此次只是参数展示，未进行字符串转义，实际情况下请转义
//        request.setBizContent("  {" +
//                "    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
//                "    \"primary_industry_code\":\"10001/20102\"," +
//                "    \"secondary_industry_code\":\"10001/20102\"," +
//                "    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
//                " }");
//        AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request);
////调用成功，则处理业务逻辑
//        if(response.isSuccess()){
//            //.....
//        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /**
     * 輸入登陸密碼
     */
    private void inputPwdDialog() {
        final EditText inputServer = new EditText(this);
        inputServer.setLines(1);
        inputServer.setMaxEms(18);
        inputServer.setPadding(50, 50, 50, 50);
        inputServer.setFocusable(true);
        inputServer.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入登陆密码").setView(inputServer).setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        if (TextUtils.isEmpty(inputName)) {
                            ToastUtil.showToastS(PayActivity.this, "请输入登陆密码");
                            return;
                        }
                        doLogin(inputName);
                    }
                });
        builder.show();
    }

    private void doLogin(String password) {
        HashMap<String, Object> map = new HashMap<>();
        String phone = (String) SPUtils.get(this, SPUtils.SpKey.USER_PHONE, "");
        map.put("phone", phone);
        map.put("password", password);
        ApiModel.getInstance().getData(UrlConstants.UrLType.LOGIN_PWD, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse) {
                    if ("1".equals(apiResponse.getStatusCode())) {
                        UserBean userBean = (UserBean) apiResponse.getResults();
                        sendPaySuccess();
                    } else {
                        ToastUtil.showShort(apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.showShort(t.toString());
            }

        });
    }

    /**
     * orderid 订单id(如果有多个订单 用逗号分割)
     * uid     用户ID
     * payType  1支付宝 2微信 3余额
     * todo : 余额支付时提示属于支付密码（登陆密码），需先判断余额是否充足（需在本地保存余额）
     */
    private void sendPaySuccess() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderid", oid);
        map.put("uid", uid);
        map.put("payType", payWay);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_PAY_SUCCESS, map, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //payType	1支付宝 2微信 3余额
                // sumAmount	金额
                // 展示支付成功view，
                ToastUtil.showShort(apiResponse.getMessage());
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    showPayOverView();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                rlOrderPayOver.setVisibility(View.GONE);
            }
        });
    }

    private void showPayOverView() {
        titleLeft.setVisibility(View.GONE);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("完成");
        titleCenter.setText("支付成功");
        if (TYPE_ALIPAY.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:支付宝");
        } else if (TYPE_WECHAT.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:微信");
        } else {
            tvOrderOverPayway.setText("支付方式:余额");
        }
        tvOrderOverPirce.setText("支付金额:" + orderPrice);
        rlOrderPayOver.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }


}
