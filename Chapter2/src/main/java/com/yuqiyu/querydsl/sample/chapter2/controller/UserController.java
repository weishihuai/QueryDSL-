package com.yuqiyu.querydsl.sample.chapter2.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter2.bean.QUserBean;
import com.yuqiyu.querydsl.sample.chapter2.bean.UserBean;
import com.yuqiyu.querydsl.sample.chapter2.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/2
 * Time：18:38
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
@RequestMapping(value = "/user")
public class UserController
{
    @Autowired
    private UserJPA userJPA;

    //实体管理者
    @Autowired
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory()
    {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }

    /**
     * 查询全部数据并根据id倒序
     * @return
     */
    @RequestMapping(value = "/queryAll")
    public List<UserBean> queryAll()
    {
        //使用querydsl查询
        QUserBean _Q_user = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(_Q_user)//查询源
                .orderBy(_Q_user.id.desc())//根据id倒序
                .fetch();//执行查询并获取结果集
    }

    /**
     * 查询详情
     * @param id 主键编号
     * @return
     */
    @RequestMapping(value = "/detail/{id}")
    public UserBean detail(@PathVariable("id") Long id)
    {
        //使用querydsl查询
        QUserBean _Q_user = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(_Q_user)//查询源
                .where(_Q_user.id.eq(id))//指定查询具体id的数据
                .fetchOne();//执行查询并返回单个结果
    }

    /**
     * SpringDataJPA & QueryDSL实现单数据查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail_2/{id}")
    public UserBean detail_2(@PathVariable("id") Long id)
    {
        //使用querydsl查询
        QUserBean _Q_user = QUserBean.userBean;
        //查询并返回指定id的单条数据
        return userJPA.findOne(_Q_user.id.eq(id));
    }


    /**
     * 根据名称模糊查询
     * @param name
     * @return
     */
    @RequestMapping(value = "/likeQueryWithName")
    public List<UserBean> likeQueryWithName(String name)
    {
        //使用querydsl查询
        QUserBean _Q_user = QUserBean.userBean;

        return queryFactory
                .selectFrom(_Q_user)//查询源
                .where(_Q_user.name.like(name))//根据name模糊查询
                .fetch();//执行查询并返回结果集
    }
}
