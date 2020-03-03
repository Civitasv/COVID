package com.gis.application.model;

import lombok.Data;

@Data
public class Virus {
    // 自增ID
    private Long id;

    // 发布时间
    private int publicTime;

    // 纬度
    private double lat;

    // 经度
    private double lng;

    // 新增确诊
    private int newDiagnosis;

    // 新增康复
    private int newRecovery;

    // 新增死亡
    private int newDeath;

    // 地址
    // private String origin;

    // 国家
    private String country;

    // 省份
    private String province;

    // 市级
    private String city;

    // 区县
    private String district;

    //
    private String origin;

    // 位置字符串
    private String location;

    public Virus(int publicTime, double lat, double lng, int newDiagnosis, int newRecovery, int newDeath, String country, String province, String city, String district) {
        this.publicTime = publicTime;
        this.lat = lat;
        this.lng = lng;
        this.newDiagnosis = newDiagnosis;
        this.newRecovery = newRecovery;
        this.newDeath = newDeath;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.origin = this.country+","+this.province+","+this.city+","+this.district;

    }

    public Virus() {
    }
}
