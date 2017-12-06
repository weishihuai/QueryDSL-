package com.yuqiyu.querydsl.sample.chapter2.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 核心JPA
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/2
 * Time：18:23
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@NoRepositoryBean
public interface BaseJPA<T>
       extends JpaRepository<T, Long>,
        JpaSpecificationExecutor<T>,
        QueryDslPredicateExecutor<T>,
        Serializable
{

}
