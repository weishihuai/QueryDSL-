package com.yuqiyu.querydsl.sample.chapter5.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品基本信息实体
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/10
 * Time：22:39
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Entity
@Table(name = "good_infos")
@Data
public class GoodInfoBean
    implements Serializable
{
    //主键
    @Id
    @Column(name = "tg_id")
    @GeneratedValue
    private Long id;
    //标题
    @Column(name = "tg_title")
    private String title;
    //价格
    @Column(name = "tg_price")
    private double price;
    //单位
    @Column(name = "tg_unit")
    private String unit;
    //排序
    @Column(name = "tg_order")
    private int order;
    //类型编号
    @Column(name = "tg_type_id")
    private Long typeId;
}
