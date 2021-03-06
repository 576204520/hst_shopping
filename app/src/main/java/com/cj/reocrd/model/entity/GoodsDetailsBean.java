package com.cj.reocrd.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.cj.reocrd.utils.Utils;

import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Lyndon.Li on 2018/4/3.
 * 商品详情实体类
 *
 * "id": "abcd",   //商品ID
 "name": "商品",  //商品名称
 "price": "100",   //商品价格
 "blocknum": "100"  //售出数量
 "place": "北京",  //产地
 "brand": "星飞",   //品牌
 "imgurl": "http://www.baidu.com",   //商品图片地址
 "detail": "商品详情"， //商品详情
 "unit": "KG"， //单位
 slist:
 clist :
 "invoice":"0",
 "merAtt":"红五谷代餐粉大礼包@#@红五谷代餐粉大礼包@#@红五谷代餐粉大礼包红五谷代餐粉大礼包@#@红五谷代餐粉大礼包红五谷代餐粉大礼包",
 "imgUrlL":"static/upload/attimg/fbf34d9a8d6f4a8fbe8dc5c03b731520.jpg@#@static/upload/attimg/4e6fcdff4ad34e23b4704ae80346d2f6.jpg@#@static/upload/attimg/516c8c96a68e4b8aa75a691c2b54fa6d.jpg@#@static/upload/attimg/7f901dd875db444aa172d837defb8b16.jpg",
 "detail":"养生功效：补血养颜,降脂降压,养心安神。产品配方：红香米、红小豆、红芸豆、红花生、红枣、红枸杞、 玫瑰花、酸枣仁、菊粉多糖。适用人群：失眠多梦者、面色无华者、四肢冰凉者、血色素低下者、高血压高血脂者、大病初愈者、长期熬夜者。",
 */

public class GoodsDetailsBean  implements Parcelable{

    private String iscollect; // 1,一收藏，2.未收藏
    private String oldprice;
    private String id;
    private String name;
    private String price;
    private String blocknum;
    private String place;
    private String brand;
    private String imgurl;
    private String unit;
    private List<SkuBean> slist;
    private List<CommentBean> clist;
    private String introduct;
    private String invoice;
    private String merAtt;
    private String imgUrlL;
    private String detail;
    public GoodsDetailsBean(){

    }

    protected GoodsDetailsBean(Parcel in) {
        oldprice = in.readString();
        iscollect = in.readString();
        id = in.readString();
        name = in.readString();
        price = in.readString();
        blocknum = in.readString();
        place = in.readString();
        brand = in.readString();
        imgurl = in.readString();
        unit = in.readString();
        introduct = in.readString();
        invoice = in.readString();
        merAtt = in.readString();
        imgUrlL = in.readString();
        detail = in.readString();
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getMerAtt() {
        return merAtt;
    }

    public void setMerAtt(String merAtt) {
        this.merAtt = merAtt;
    }

    public String getImgUrlL() {
        return imgUrlL;
    }

    public void setImgUrlL(String imgUrlL) {
        this.imgUrlL = imgUrlL;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        oldprice = Utils.strDivide(oldprice);
        this.oldprice = oldprice;
    }

    public static final Creator<GoodsDetailsBean> CREATOR = new Creator<GoodsDetailsBean>() {
        @Override
        public GoodsDetailsBean createFromParcel(Parcel in) {
            return new GoodsDetailsBean(in);
        }

        @Override
        public GoodsDetailsBean[] newArray(int size) {
            return new GoodsDetailsBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        price = Utils.strDivide(price);
        this.price = price;
    }

    public String getBlocknum() {
        return blocknum;
    }

    public void setBlocknum(String blocknum) {
        this.blocknum = blocknum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SkuBean> getSlist() {
        return slist;
    }

    public void setSlist(List<SkuBean> slist) {
        this.slist = slist;
    }

    public List<CommentBean> getClist() {
        return clist;
    }

    public void setClist(List<CommentBean> clist) {
        this.clist = clist;
    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(oldprice);
        dest.writeString(iscollect);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(blocknum);
        dest.writeString(place);
        dest.writeString(brand);
        dest.writeString(imgurl);
        dest.writeString(unit);
        dest.writeList(slist);
        dest.writeList(clist);
        dest.writeString(introduct);
        dest.writeString(invoice);
        dest.writeString(merAtt);
        dest.writeString(imgUrlL);
        dest.writeString(detail);
    }
}
