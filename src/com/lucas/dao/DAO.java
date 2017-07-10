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
 * ��װ�Ļ�����CRUD�������Թ�����ʹ��
 * ��ǰDAOֱ���ڷ����л�ȡ���ݿ�����
 * ����DAO��ȡDBUtil�������
 * @param <T>:��ǰdao�����ʵ��������ʲô
 */
public class DAO<T> {
	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public DAO() {
		System.out.println("getClass..." + this.getClass());
		//�õ������͵ĸ���
		//Type�� Java ����������������͵Ĺ����߼��ӿڡ����ǰ���ԭʼ���͡����������͡��������͡����ͱ����ͻ������͡�
		Type genericSuperclass = getClass().getGenericSuperclass();
		System.out.println("...genericSuperClass:" + genericSuperclass);
		if (genericSuperclass instanceof ParameterizedType){//ParameterizedType�ǲ��������ͣ�������
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			//Type�� Java ����������������͵Ĺ����߼��ӿڡ����ǰ���ԭʼ���͡����������͡��������͡����ͱ����ͻ������͡�
			if(actualTypeArguments != null && actualTypeArguments.length>0) {
				clazz = (Class<T>) actualTypeArguments[0];
				System.out.println("...clazz:" + clazz);
			}
		}
	}
	
	/**
	 * ����ĳһ���ֶ�ֵ�����緵��ĳһ����¼��customerName,�򷵻����ݱ����ж�������¼��
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
	 * ����T��Ӧ��List
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
	 * ���ض�ӦT��ʵ����Ķ���
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
	 * �÷�����װ��insert/delete/update�Ȳ���
	 * @param sql��sql���
	 * @param args�����sql����ռλ��
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
