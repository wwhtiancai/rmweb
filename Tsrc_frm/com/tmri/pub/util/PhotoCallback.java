package com.tmri.pub.util;

import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import oracle.sql.BLOB;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.JdbcUtils;

import com.tmri.framework.bean.ItemContainer;

/**
 * 图片更新callback调用示例见departmentdaojdbc
 * @author chiva
 * 2010/7/24
 */
public class PhotoCallback implements PreparedStatementCallback {
	private Object parameterObject;

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public ItemContainer doInPreparedStatement(PreparedStatement ps)
			throws SQLException, DataAccessException {
		List list = (List) this.parameterObject;
		ItemContainer hm = new ItemContainer();
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			int x = 0;
			if (rs.next()) {
				for (int i = 0; i < list.size(); i++) {
					try {
						BLOB blob = (oracle.sql.BLOB) rs.getBlob(i + 1);
						OutputStream outStream = null;
						try {
							outStream = blob.setBinaryStream(0);
						} catch (Exception ex) {
							outStream = blob.getBinaryOutputStream();
						}
						byte[] bytes = (byte[]) list.get(i);
						outStream.write(bytes, 0, bytes.length);
						outStream.flush();
						outStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} finally {
			JdbcUtils.closeResultSet(rs);
		}
		return hm;
	}

}
