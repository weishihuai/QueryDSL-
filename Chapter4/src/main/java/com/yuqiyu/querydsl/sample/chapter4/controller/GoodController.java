package com.yuqiyu.querydsl.sample.chapter4.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter4.bean.GoodInfoBean;
import com.yuqiyu.querydsl.sample.chapter4.bean.QGoodInfoBean;
import com.yuqiyu.querydsl.sample.chapter4.bean.QGoodTypeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/9
 * Time：15:24
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class GoodController
{

    @Autowired
    private EntityManager entityManager;

    //查询工厂实体
    private JPAQueryFactory queryFactory;

    //实例化控制器完成后执行该方法实例化JPAQueryFactory
    @PostConstruct
    public void initFactory()
    {
        System.out.println("开始实例化JPAQueryFactory");
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @RequestMapping(value = "/selectByType")
    public List<GoodInfoBean> selectByType
            (
                    @RequestParam(value = "typeId") Long typeId //类型编号
            )
    {
        //商品查询实体
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型查询实体
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;
        return
                queryFactory
                .select(_Q_good)
                .from(_Q_good,_Q_good_type)
                .where(
                        //为两个实体关联查询
                        _Q_good.typeId.eq(_Q_good_type.id)
                        .and(
                                //查询指定typeid的商品
                                _Q_good_type.id.eq(typeId)
                        )
                )
                 .orderBy(_Q_good.order.desc())
                .fetch();
    }

}
