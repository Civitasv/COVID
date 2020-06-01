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

    // 国家
    private String country;

    // 省份
    private String province;

    // 市级
    private String city;

    // 区县
    private String district;

    // 位置字符串，Postgis字段
    private String location;

    // 描述
    private String description;

    public Virus(int publicTime, double lat, double lng, int newDiagnosis, int newRecovery, int newDeath, String country, String province, String city, String district, String description) {
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
        this.description = description;
    }

    public Virus() {
    }
}
