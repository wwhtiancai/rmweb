package com.tmri.share.frm.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

import com.tmri.share.frm.dao.CommDao;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbArguments;
import com.tmri.share.ora.bean.DbParaInfo;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.share.ora.bean.lob.Blob;
import com.tmri.share.ora.bean.lob.Clob;



@Repository
public class CommDaoJdbc extends FrmDaoJdbc implements CommDao {
	@Autowired
	private DefaultLobHandler lobHandler;


	public DbResult operaPackage(String procname,
			Map<String, Object> hashmap) {
		DbResult result = new DbResult();
		Map<String, Object> ht = commPackage(procname, hashmap);
		if (ht.get("key1") != null&&!(ht.get("key1") instanceof String)) {
			result.setCode(Integer.valueOf((String) ht.get("key1")).intValue());
		} else {
			result.setObj(ht.get("key1"));
			result.setCode(1);
		}
		result.setMsg((String) ht.get("key2"));
		result.setMsg1((String) ht.get("key3"));
		return result;
	}

	// srmprojectȡֵ��ƥ��
	public Map<String, Object> commPackage(String procname,
			Map<String, Object> hashmap) {
		String callString = getCallString(procname);
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbParaInfo para = (DbParaInfo) getParameterObject();
				para.setCstmt(cstmt);
				Hashtable<String, Object> ht = new Hashtable<String, Object>();
				ht = getCallback(para);
				return ht;
			}
		};
		DbParaInfo para = new DbParaInfo();
		para.setHashmap(hashmap);
		para.setProcname(procname);
		para.setLobHandler(this.lobHandler);
		callBack.setParameterObject(para);

		Hashtable<String, Object> ht = (Hashtable<String, Object>) jdbcTemplate
				.execute(callString, callBack);
		return ht;
	}

	// ��ȡ�ֶ�����
	public int getClassname(String type) {
		int result = Types.VARCHAR;
		if (type.equals("CHAR")) {
			result = Types.VARCHAR;
		} else if (type.equals("VARCHAR2")) {
			result = Types.VARCHAR;
		} else if (type.equals("DATE")) {
			result = Types.DATE;
		} else if (type.equals("TIMESTAMP")) {
			result = Types.TIMESTAMP;
		} else if (type.equals("NUMBER")) {
			result = Types.NUMERIC;
		} else if (type.equals("BLOB")) {
			result = Types.BLOB;
		}
		return result;
	}

	public Hashtable<String, Object> getCallback(DbParaInfo para)
			throws SQLException, DataAccessException {
		String procname = para.getProcname();
		Map<?, ?> hashmap = para.getHashmap();
		CallableStatement cstmt = para.getCstmt();

		List<DbArguments> argulist = getDbArguList(procname);
		for (int i = 0; i < argulist.size(); i++) {
			DbArguments obj = (DbArguments) argulist.get(i);
			int pos = Integer.valueOf(obj.getPosition()).intValue()+1;
			if (obj.getIn_out().equals("OUT")) {
				cstmt.registerOutParameter(pos,
						getClassname(obj.getData_type()));
			} else if (obj.getIn_out().equals("IN")) {
				if (obj.getData_type().equals("BLOB")) {
					if (!obj.getArgument_name().equals("")) {
						//
						String field = obj.getArgument_name().substring(3);
						Object objblob = hashmap.get(field);
						if (objblob != null && objblob.toString().equals("")) {
							try {
								this.lobHandler.getLobCreator().setBlobAsBytes(
										cstmt, pos, null);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						} else {
							byte[] val = (byte[]) hashmap.get(field);
							try {
								this.lobHandler.getLobCreator().setBlobAsBytes(
										cstmt, pos, val);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				} else {
					if (!obj.getArgument_name().equals("")) {
						String field = obj.getArgument_name().substring(3);
						//

						Object valobj = hashmap.get(field);
						if (valobj == null || valobj.equals("")) {
							cstmt.setNull(pos, Types.VARCHAR);
						} else {
							if (obj.getData_type().equals("VARCHAR2")
									|| obj.getData_type().equals("CHAR")) {
								String val = String.valueOf(valobj);
								cstmt.setString(pos, val);
							} else if (obj.getData_type().equals("NUMBER")) {
								Long val = Long.valueOf(String.valueOf(valobj));
								cstmt.setLong(pos, val);
							}
						}
					}
				}
			}
		}
		cstmt.execute();
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		int j = 0;
		for (int i = 0; i < argulist.size(); i++) {
			DbArguments obj = (DbArguments) argulist.get(i);
			if (obj.getIn_out().equals("OUT")) {
				j = j + 1;
				int pos = Integer.valueOf(obj.getPosition()).intValue()+1;
				String key = "key" + j;
				if (obj.getData_type().equals("VARCHAR2")
						|| obj.getData_type().equals("CHAR")) {
					try {
						String retval = cstmt.getString(pos);
						if (retval == null) {
							retval = "";
						}
						ht.put(key, retval);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (obj.getData_type().equals("NUMBER")) {
					try {
						ht.put(key, String.valueOf(cstmt.getLong(pos)));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return ht;
	}

	// ���ɵ������
	public String getCallString(String procname) {
		String result = "{";
		List<DbArguments> argulist = getDbArguList(procname);
		for (int i = 0; i < argulist.size(); i++) {
			DbArguments obj = (DbArguments) argulist.get(i);
			if (obj.getPosition().equals("0")) {
				result = result + " ? = ";
			} else {
				if (!obj.getArgument_name().equals("")) {
					if (obj.getPosition().equals("1")) {
						result = result + " call " + procname + "(?,";
					} else {
						result = result + "?,";
					}
				}
			}
		}
		if (result.equals("{")) {
			result = result + " call " + procname + "}";
		} else {
			result = result.substring(0, result.length() - 1) + ")}";
		}

		return result;
	}

	// ��ȡ�洢���̵��ֶ��б�
	public List<DbArguments> getDbArguList(String procname) {
		String sql = "select v.object_name,v.package_name,v.argument_name,v.position,v.data_type,v.in_out "
				+ " from user_arguments v"
				+ " where v.object_name = '"
				+ getProcname(procname)
				+ "' "
				+ " and v.package_name  = '"
				+ getPckname(procname) + "' " + " order by v.position ";
		List<DbArguments> list = this.jdbcTemplate.queryForList(sql,
				DbArguments.class);
		return list;
	}

	// ��ʽ��������
	public static String getPckname(String procname) {
		String result = "";
		try {
			int index = procname.indexOf(".");
			result = procname.substring(0, index);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(procname);
		}
		return result.toUpperCase();
	}

	// ��ʽ���洢��������
	public static String getProcname(String procname) {
		int index = procname.indexOf(".");
		String result = procname.substring(index + 1);
		return result.toUpperCase();
	}

	/**
	 * ִ�д洢���̣��洢���̵ķ���ֵ��ֻ��һ��number�͵�code
	 * 
	 * @see CommDaoJdbc#executeProc(String, int, Object...)
	 * 
	 * @param proc
	 *            �洢��������package.proc
	 * @param args
	 *            �������������ޣ�����˳����
	 * @return
	 */
	public long executeProc1(String proc, final Object... args) {
		DbResult result = executeProc(proc, 1, args);
		return result.getCode();
	}

	/**
	 * ִ�д洢���̣�����ֵ������DbResult���������ʺϵ��ô洢���̣����ؼ򵥽��
	 * 
	 * </br>˵�������������bolb����clob�ֶΣ��뽫�������з�װ��<li>new Clob(argn);�ο�{@link Clob} <li>
	 * new Blob(argm); �ο�{@link Blob}
	 * 
	 * @param proc
	 *            �洢���̵����ơ�pkg.proc
	 * @param retArgCount
	 *            ����ֵ������Ҫ��5>retArgCount>0�����򱾷�������</br> <li>
	 *            ��һ������ֵ���ͣ�����Ϊnumber����ӦDbResult��code�ֶΣ� <li>����ķ���ֵ���ͣ�
	 *            һ��Ϊvarchar����Ӧ�ֶ�[msg~msg3]
	 * @param args
	 *            �洢���̵�������������Դ�0~N��ֵ
	 * @return {@link DbResult}
	 * 
	 * <pre>
	 * �洢���̣� <code> Procedure Changenet_Sys_User_Passwd ( In_Yhlx Varchar2, In_Yhdh
	 *         Varchar2, In_Newpasswd Varchar2, Rtn Out Number, Rtncolname Out
	 *         Varchar2, Rtnmsg Out Varchar2 ) Is </code>
	 *         ............................
	 * 
	 *         java���� <code> DbResult info =
	 *         executeProc("net_sys_pkg.Changenet_Sys_User_Passwd"
	 *         ,3,"yhlx","yhdh","passwd"); </code> ���õķ���ֵ�� <code>
	 *         info.getCode(); info.getMsg(); info.getMsg1(); </code>
	 * 
	 *         </pre>
	 * 
	 */
	public DbResult executeProc(String proc, int retArgCount,
			Object... args) {
		if (retArgCount <= 0 || retArgCount > 5) {
			throw new IllegalArgumentException("����[retArgCount]ȡֵ������������:[1,5]");
		}

		final int argsCount;
		if (args == null) {
			argsCount = 0;
		} else {
			argsCount = args.length;
		}
		String storedProc = buildCallProcStr(proc, argsCount, retArgCount);
		DbResult result = this.jdbcTemplate.execute(storedProc,
				buildCallBack(argsCount, retArgCount, args));
		return result;
	}

	private CallableStatementCallback<DbResult> buildCallBack(
			final int argsCount, final int retArgsCount, final Object... args) {
		return new CallableStatementCallback<DbResult>() {
			public DbResult doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {

				int index = setInArgs(args.length, args, cs);
				int[] outIndexs = setOutArgs(index, retArgsCount, cs);

				cs.execute();

				return getData(retArgsCount, outIndexs, cs);
			}
		};
	}

	private int setInArgs(int argsCount, Object[] args, CallableStatement cs)
			throws SQLException {
		int index = 0;
		if (argsCount > 0) {
			for (Object object : args) {
				// �����clob
				if (object instanceof Clob) {
					String str = ((Clob) object).toString();
					if (str == null)
						cs.setNull(index + 1, Types.CLOB);
					else
						CommDaoJdbc.this.lobHandler.getLobCreator()
								.setClobAsString(cs, index + 1, str);
				}
				// �����blob
				else if (object instanceof Blob) {
					if (object != null) {
						CommDaoJdbc.this.lobHandler.getLobCreator()
								.setBlobAsBytes(cs, index + 1,
										((Blob) object).toByte());
					} else {
						cs.setNull(index + 1, Types.BLOB);
					}

				}
				// �������ͨ���ֶ�
				else {
					cs.setObject(index + 1, object);
				}
				index++;
			}
		}
		return index;
	}

	private int[] setOutArgs(int index, int retArgsCount, CallableStatement cs)
			throws SQLException {
		int[] outIndexs = new int[retArgsCount];
		for (int i = 0; i < retArgsCount; i++) {
			if (i == 0) {
				cs.registerOutParameter(index + 1, OracleTypes.NUMBER);
			} else {
				cs.registerOutParameter(index + 1, OracleTypes.VARCHAR);
			}
			outIndexs[i] = index + 1;
			index++;
		}
		return outIndexs;
	}

	private DbResult getData(int retArgsCount, int[] outIndexs,
			CallableStatement cs) throws SQLException {
		DbResult returnInfo = new DbResult();
		if (retArgsCount > 0) {
			returnInfo.setCode(cs.getLong(outIndexs[0]));
		}
		if (retArgsCount > 1) {
			returnInfo.setMsg(cs.getString(outIndexs[1]));
		}
		if (retArgsCount > 2) {
			returnInfo.setMsg1(cs.getString(outIndexs[2]));
		}
		if (retArgsCount > 3) {
			returnInfo.setMsg2(cs.getString(outIndexs[3]));
		}
		if (retArgsCount > 4) {
			returnInfo.setMsg3(cs.getString(outIndexs[4]));
		}
		return returnInfo;
	}

	/**
	 * ����������������ͷ��ز����������������ô洢���̵�call string
	 * 
	 * @param proc
	 * @param argsCount
	 * @param retArgCount
	 * @return
	 */
	private static String buildCallProcStr(String proc, int argsCount,
			int retArgCount) {
		if (argsCount < 0) {
			argsCount = 0;
		}

		StringBuilder callStoredProc = new StringBuilder("{call ").append(proc)
				.append("(");
		int i = 0;
		int totalArgCount = argsCount + retArgCount;
		while (i < totalArgCount) {
			if (i > 0) {
				callStoredProc.append(",");
			}
			i++;
			callStoredProc.append("?");
		}
		callStoredProc.append(")}");

		return callStoredProc.toString();
	}

}
