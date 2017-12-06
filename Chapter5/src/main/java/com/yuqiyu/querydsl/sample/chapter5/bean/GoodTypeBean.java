package com.yuqiyu.querydsl.sample.chapter5.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品类别实体
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/10
 * Time：22:39
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Entity
@Table(name = "good_types")
@Data
public class GoodTypeBean
    implements Serializable
{
    //类型编号
    @Id
    @GeneratedValue
    @Column(name = "tgt_id")
    private Long id;
    //类型名称
    @Column(name = "tgt_name")
    private String name;
    //是否显示
    @Column(name = "tgt_is_show")
    private int isShow;
    //排序
    @Column(name = "tgt_order")
    private int order;
}
