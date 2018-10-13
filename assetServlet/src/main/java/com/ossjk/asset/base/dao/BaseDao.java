package com.ossjk.asset.base.dao;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.ossjk.asset.base.util.CommonUtil;
import com.ossjk.asset.base.util.ReflectUtil;

public class BaseDao<T extends Serializable> {

	private Logger logger = Logger.getLogger(BaseDao.class);
	private Connection con = null;
	private PreparedStatement ps = null;
	private static final String table_prefix = "";
	private DruidPool druidPool;
	private DruidDataSource druidDataSource;

	/// C3p0连接
	public BaseDao() {
		druidDataSource = druidPool.getInstance().getDataSource();
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		logger.debug("获取数据库连接。。。");
		if (con == null || con.isClosed()) {
			con = druidDataSource.getConnection();
		}

		return con;
	}

	/**
	 * 回收连接
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		logger.debug("回收数据库连接。。。");
		con.close();
	}

	/**
	 * 基础查找
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public ResultSet select(String sql, Object... obj) {
		ResultSet rs = null;
		try {
			getConnection();
			ps = con.prepareStatement(sql);
			/// 填充占位符
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {// 循环填充占位符
					ps.setObject(i + 1, obj[i]);
				}
			}
			logger.debug("执行：" + sql);
			// 执行sql
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 基础增删改
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public Integer merger(String sql, Object... obj) throws SQLException {
		int result = 0;
		try {
			getConnection();
			ps = con.prepareStatement(sql);
			/// 填充占位符
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {// 循环填充占位符
					ps.setObject(i + 1, obj[i]);
				}
			}
			logger.debug("执行：" + sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return result;

	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	public String getTableName() {
		Class entityClazz = ReflectUtil.getClassGenricType(this.getClass());
		return table_prefix + entityClazz.getSimpleName();
	}

	/**
	 * 自动封装
	 * 
	 * @return
	 */
	public <Model> Model autoSetter(ResultSet rs, Class<Model> class1) {
		Model model = null;
		try {
			if (rs != null) {
				model = class1.newInstance();
				//// 通过反射获取到实体类里面的所有属性
				Field[] modelFields = class1.getDeclaredFields();
				for (int i = 0; i < modelFields.length; i++) {
					String type = modelFields[i].getType().getName();
					// 动态封装
					if (type.lastIndexOf("Integer") > -1 || type.lastIndexOf("int") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getInt(modelFields[i].getName()));
					} else if (type.lastIndexOf("Long") > -1 || type.lastIndexOf("long") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getLong(modelFields[i].getName()));
					} else if (type.lastIndexOf("Short") > -1 || type.lastIndexOf("short") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getShort(modelFields[i].getName()));
					} else if (type.lastIndexOf("Float") > -1 || type.lastIndexOf("float") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getFloat(modelFields[i].getName()));
					} else if (type.lastIndexOf("Double") > -1 || type.lastIndexOf("double") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getDouble(modelFields[i].getName()));
					} else if (type.lastIndexOf("String") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getString(modelFields[i].getName()));
					} else if (type.lastIndexOf("Date") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getDate(modelFields[i].getName()));
					} else if (type.lastIndexOf("BigDecimal") > -1) {
						ReflectUtil.invokeSetter(model, modelFields[i].getName(), rs.getBigDecimal(modelFields[i].getName()));
					}
				}
			}
		} catch (Exception e) {
			logger.error("自动封装会报错", e);
		}
		return model;

	}

	/**
	 * 查找单个
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public T selectOne(String sql, Object... obj) throws SQLException {
		T object = null;
		try {
			ResultSet rs = select(sql, obj);
			if (rs != null && rs.next()) {
				object = (T) autoSetter(rs, ReflectUtil.getClassGenricType(this.getClass()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return object;
	}

	/**
	 * 查找单个
	 * 
	 * @param class1
	 * @param sql
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public <R> R selectOne(Class<R> class1, String sql, Object... obj) throws SQLException {
		R object = null;
		try {
			ResultSet rs = select(sql, obj);
			if (rs != null && rs.next()) {
				object = (R) autoSetter(rs, class1.getClass());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return object;
	}

	/**
	 * 查找多个
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public List<T> selectSome(String sql, Object... obj) throws SQLException {
		List<T> list = new ArrayList();
		try {
			ResultSet rs = select(sql, obj);
			if (rs != null) {
				while (rs.next()) {
					// 处理结果集返回一个对象
					T object1 = autoSetter(rs, ReflectUtil.getClassGenricType(this.getClass()));
					/// 把对象放到集合里面
					list.add(object1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return list;
	}

	/**
	 * 查找多个
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public <R> List<R> selectSome(Class<R> class1, String sql, Object... obj) throws SQLException {
		List<R> list = new ArrayList();
		try {
			ResultSet rs = select(sql, obj);
			if (rs != null) {
				while (rs.next()) {
					// 处理结果集返回一个对象
					R object1 = (R) autoSetter(rs, class1.getClass());
					/// 把对象放到集合里面
					list.add(object1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return list;
	}

	/**
	 * 查找全部
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<T> selectAll() throws SQLException {
		List list = new ArrayList<>();
		String sql = "select * from " + getTableName();
		list = selectSome(sql);
		return list;
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public T selectById(Serializable id) throws SQLException {
		String sql = "select * from " + getTableName() + " where id  = ?";
		return selectOne(sql, id);
	}

	/**
	 * 获取插入sql
	 * 
	 * @return
	 */
	public String getInsertSql() {
		StringBuffer sql = new StringBuffer("insert into " + getTableName() + "( ");
		Class entityClass = ReflectUtil.getClassGenricType(this.getClass());
		Field[] fields = entityClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			sql.append(fields[i].getName());
			if (i < fields.length - 1) {
				sql.append(",");
			}
		}
		sql.append(" ) values( ");
		for (int i = 0; i < fields.length; i++) {
			sql.append("?");
			if (i < fields.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
	}

	/**
	 * 插入
	 * 
	 * @param t
	 * @return
	 */
	public Integer insert(T t) throws SQLException {
		Field[] fields = t.getClass().getDeclaredFields();
		List params = new ArrayList();
		for (int i = 0; i < fields.length; i++) {
			params.add(ReflectUtil.getFieldValue(t, fields[i].getName()));
		}
		return merger(getInsertSql(), params.toArray());
	}

	/**
	 * 获取更新sql
	 * 
	 * @return
	 */
	public String getUpdateSql() {
		StringBuffer sql = new StringBuffer("update " + getTableName() + " set ");
		Class entityClass = ReflectUtil.getClassGenricType(this.getClass());
		Field[] fields = entityClass.getDeclaredFields();
		for (int i = 1; i < fields.length; i++) {
			sql.append(fields[i].getName() + " = ?");
			if (i < fields.length - 1) {
				sql.append(",");
			}
		}
		sql.append(" where id = ?");
		return sql.toString();
	}

	/**
	 * 更新
	 * 
	 * @param t
	 * @return
	 */
	public Integer update(T t) throws SQLException {
		Field[] fields = t.getClass().getDeclaredFields();
		List params = new ArrayList();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].getName().equals("id")) {
				params.add(ReflectUtil.getFieldValue(t, fields[i].getName()));
			}
		}
		Object id = ReflectUtil.getFieldValue(t, "id");
		if (id != null && "".equals(id.toString())) {
			params.add(id);
			return merger(getUpdateSql(), params.toArray());
		}
		return 0;
	}

	/**
	 * 选择更新
	 * 
	 * @param t
	 * @return
	 */
	public Integer updateBySelected(T t) throws SQLException {
		Field[] fields = t.getClass().getDeclaredFields();
		List params = new ArrayList();
		if (fields != null && fields.length > 0) {
			StringBuffer sql = new StringBuffer("update " + getTableName() + " set ");
			boolean flag = false;
			for (int i = 1; i < fields.length; i++) {
				if (!fields[i].getName().equals("id")) {
					Object value = ReflectUtil.getFieldValue(t, fields[i].getName());
					if (value != null) {
						flag = true;
						params.add(value);
						sql.append(fields[i].getName() + " = ?");
						sql.append(",");
					}
				}
			}
			sql.append(" where id = ?");
			int lastIndexOf = sql.toString().lastIndexOf(",");
			sql = sql.replace(lastIndexOf, lastIndexOf + ",".length(), "");
			Object id = ReflectUtil.getFieldValue(t, "id");
			if (id != null && !"".equals(id.toString())) {
				params.add(id);
				if (flag) {
					return merger(sql.toString(), params.toArray());
				}
			}
		}
		return 0;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Serializable id) throws SQLException {
		String sql = "delete from " + getTableName() + " where id = ?";
		return merger(sql, id);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer batchDelete(Serializable[] id) throws SQLException {
		if (!CommonUtil.isBlank(id)) {
			StringBuilder sql = new StringBuilder();
			sql.append("delete from " + getTableName() + " where id in (");
			for (Serializable serializable : id) {
				sql.append(" ? ,");
			}
			sql.append(" )");
			int lastIndexOf = sql.toString().lastIndexOf(",");
			sql = sql.replace(lastIndexOf, lastIndexOf + ",".length(), "");
			return merger(sql.toString(), id);
		}

		return 0;
	}

	public void autoGenEntityTitle() throws Exception {
		Class class1 = ReflectUtil.getClassGenricType(this.getClass());
		StringBuilder toCopy = new StringBuilder();
		StringBuilder title = new StringBuilder("import java.io.Serializable;\n\n");
		title.append("public class ");
		title.append(getTableName());
		title.append(" implements Serializable {\n\n");
		getConnection();
		ResultSet rs = con.prepareStatement("show full fields from " + getTableName()).executeQuery();
		String tpyeName = null;
		String comment = null;
		String field = null;
		while (rs.next()) {
			comment = rs.getString("comment");
			field = rs.getString("field");
			tpyeName = rs.getString("type").substring(0, 3);
			title.append("\t/**\n");
			title.append("\t*" + comment + " \n");
			title.append("\t*/\n");
			title.append("\tprivate ");
			if ("dec".equals(tpyeName) || "num".equals(tpyeName) || "int".equals(tpyeName) || "sma".equals(tpyeName) || "tin".equals(tpyeName) || "big".equals(tpyeName) || "dou".equals(tpyeName) || "flo".equals(tpyeName)) {
				title.append(" BigDecimal ");
			} else if ("var".equals(tpyeName) || "cha".equals(tpyeName) || "tex".equals(tpyeName)) {
				title.append(" String ");
			} else if ("dat".equals(tpyeName) || "tim".equals(tpyeName) || "yea".equals(tpyeName)) {
				title.append(" Date ");
			} else {
				title.append(" Object ");
			}
			title.append(field + ";");
			title.append("\n");

		}
		title.append("\n");
		title.append("}");
		System.out.println(title.toString());
		closeConnection();
	}

}
