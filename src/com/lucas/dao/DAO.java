package com.lucas.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lucas.db.JdbcUtills;
import com.mchange.v2.c3p0.impl.DefaultConnectionTester.QuerylessTestRunner;

/**
 * 封装的基本的CRUD方法，以供子类使用
 * 当前DAO直接在方法中获取数据库连接
 * 整个DAO采取DBUtil解决方案
 * @param <T>:当前dao处理的实体类型是什么
 */
public class DAO<T> {
	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public DAO() {
		System.out.println("getClass..." + this.getClass());
		//得到带泛型的父类
		//Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
		Type genericSuperclass = getClass().getGenericSuperclass();
		System.out.println("...genericSuperClass:" + genericSuperclass);
		if (genericSuperclass instanceof ParameterizedType){//ParameterizedType是参数化类型，即泛型
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			//Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
			if(actualTypeArguments != null && actualTypeArguments.length>0) {
				clazz = (Class<T>) actualTypeArguments[0];
				System.out.println("...clazz:" + clazz);
			}
		}
	}
	
	/**
	 * 返回某一个字段值：例如返回某一条记录的customerName,或返回数据表中有多少条记录。
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E getForValue(String sql, Object...args) {
		Connection connection = null;
		try {
			connection = JdbcUtills.getConnection();
			return (E) queryRunner.query(connection, sql, new ScalarHandler(),args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtills.releaseConnection(connection);
		}
		return null;
	}
	/**
	 * 返回T对应的List
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getForList(String sql, Object...args) {
		Connection connection = null;
		try {
			connection = JdbcUtills.getConnection();
			return (List<T>) queryRunner.query(connection, sql, new BeanListHandler(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtills.releaseConnection(connection);
		}
		return null;
	}
	/**
	 * 返回对应T的实体类的对象
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(String sql, Object...args) {
		System.out.println(clazz);
		Connection connection = null;
		try {
			connection = JdbcUtills.getConnection();
			return (T) queryRunner.query(connection, sql, new BeanHandler(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtills.releaseConnection(connection);
		}
		return null;
	}
	/**
	 * 该方法封装了insert/delete/update等操作
	 * @param sql：sql语句
	 * @param args：填充sql语句的占位符
	 */
	public int update(String sql, Object...args) {
		int result = 0;
		Connection connection = null;
		try {
			connection = JdbcUtills.getConnection();
			result = queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtills.releaseConnection(connection);
		}
		return result;
		
	}
}
