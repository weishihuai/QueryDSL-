package com.yuqiyu.querydsl.sample.chapter7.controller;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter7.bean.GoodInfoBean;
import com.yuqiyu.querydsl.sample.chapter7.bean.QGoodInfoBean;
import com.yuqiyu.querydsl.sample.chapter7.bean.QGoodTypeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/14
 * Time：9:30
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class GoodController
{
    //实体管理对象
    @Autowired
    private EntityManager entityManager;
    //jpa查询工厂对象
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init()
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }
    /**
     * 子查询 模糊查询
     * @return
     */
    @RequestMapping(value = "/childLikeSelect")
    public List<GoodInfoBean> childLikeSelect()
    {
        //商品基本信息查询实体
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型查询实体
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;

        return queryFactory
                .selectFrom(_Q_good)//查询商品基本信息表
                .where(
                        //查询类型名称包含“蔬菜”
                        _Q_good.typeId.in(
                                JPAExpressions.select(
                                        _Q_good_type.id
                                )
                                .from(_Q_good_type)
                                .where(_Q_good_type.name.like("%蔬菜%"))
                        )
                ).fetch();
    }

    /**
     * 子查询 价格最高的蔬菜列表
     * @return
     */
    @RequestMapping(value = "/childEqSelect")
    public List<GoodInfoBean> childEqSelect()
    {
        //商品基本信息查询实体
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;

        return queryFactory
                .selectFrom(_Q_good)
                //查询价格最大的蔬菜列表
                .where(_Q_good.price.eq(
                        JPAExpressions.select(
                                _Q_good.price.max()
                        )
                        .from(_Q_good)
                ))
                .fetch();
    }

    /**
     * 子查询 价格高于平均价格的商品列表
     * @return
     */
    @RequestMapping(value = "/childGtAvgSelect")
    public List<GoodInfoBean> childGtAvgSelect()
    {
        //商品基本信息查询实体
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        return queryFactory
                .selectFrom(_Q_good)
                //查询价格高于平均价的商品列表
                .where(
                        _Q_good.price.gt(
                                JPAExpressions.select(_Q_good.price.avg())
                                .from(_Q_good)
                        )
                ).fetch();
    }
}
