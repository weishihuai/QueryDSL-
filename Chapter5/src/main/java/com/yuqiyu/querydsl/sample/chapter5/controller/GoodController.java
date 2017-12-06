package com.yuqiyu.querydsl.sample.chapter5.controller;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter5.bean.QGoodInfoBean;
import com.yuqiyu.querydsl.sample.chapter5.bean.QGoodTypeBean;
import com.yuqiyu.querydsl.sample.chapter5.dto.GoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多表查询返回商品dto控制器
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/10
 * Time：23:04
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class GoodController
{

    //实体管理
    @Autowired
    private EntityManager entityManager;

    //查询工厂
    private JPAQueryFactory queryFactory;

    //初始化查询工厂
    @PostConstruct
    public void init()
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }
    /**
     * 根据QueryDSL查询
     * @return
     */
    @RequestMapping(value = "/selectWithQueryDSL")
    public List<GoodDTO> selectWithQueryDSL()
    {
        //商品基本信息
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;

        return queryFactory
                .select(
                        Projections.bean(
                                GoodDTO.class,//返回自定义实体的类型
                                _Q_good.id,
                                _Q_good.price,
                                _Q_good.title,
                                _Q_good.unit,
                                _Q_good_type.name.as("typeName"),//使用别名对应dto内的typeName
                                _Q_good_type.id.as("typeId")//使用别名对应dto内的typeId
                         )
                )
                .from(_Q_good,_Q_good_type)//构建两表笛卡尔集
                .where(_Q_good.typeId.eq(_Q_good_type.id))//关联两表
                .orderBy(_Q_good.order.desc())//倒序
                .fetch();
    }

    /**
     * 使用java8新特性Collection内stream方法转换dto
     * @return
     */
    @RequestMapping(value = "/selectWithStream")
    public List<GoodDTO> selectWithStream()
    {
        //商品基本信息
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;
        return queryFactory
                .select(
                        _Q_good.id,
                        _Q_good.price,
                        _Q_good.title,
                        _Q_good.unit,
                        _Q_good_type.name,
                        _Q_good_type.id
                )
                .from(_Q_good,_Q_good_type)//构建两表笛卡尔集
                .where(_Q_good.typeId.eq(_Q_good_type.id))//关联两表
                .orderBy(_Q_good.order.desc())//倒序
                .fetch()
                .stream()
                //转换集合内的数据
                .map(tuple -> {
                    //创建商品dto
                    GoodDTO dto = new GoodDTO();
                    //设置商品编号
                    dto.setId(tuple.get(_Q_good.id));
                    //设置商品价格
                    dto.setPrice(tuple.get(_Q_good.price));
                    //设置商品标题
                    dto.setTitle(tuple.get(_Q_good.title));
                    //设置单位
                    dto.setUnit(tuple.get(_Q_good.unit));
                    //设置类型编号
                    dto.setTypeId(tuple.get(_Q_good_type.id));
                    //设置类型名称
                    dto.setTypeName(tuple.get(_Q_good_type.name));
                    //返回本次构建的dto
                    return dto;
                })
                //返回集合并且转换为List<GoodDTO>
                .collect(Collectors.toList());
    }
}
