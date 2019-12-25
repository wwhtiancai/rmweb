package com.tmri.framework.conn;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c)公安部交通管理科学研究所 2003</p>
 * <p>Company: 公安部交通管理科学研究所</p>
 * @author 是建荣
 * @version 1.0
 */

import java.util.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;

//WebSphere 3.0 使用以下包其它使用javax.sql.*;
//import com.ibm.db2.jdbc.app.stdext.javax.sql.*;
//import com.ibm.ejs.dbm.jdbcext.*;


//import oracle.jdbc.driver.OracleDriver;
public class DBconBean {

  private static DataSource ds = null;
  private static String user = "trff_app";
  private static String password = "trff_admin123!";
  private static String owner = "police";
  private static String source = "jdbc/trffsysphoto";
  private static String ip = "rs6000";
  private static String dbname = "ora816";
  private static String port = "1521";
  private static String ServerType = "4";
  private static String connURL = "";
  private static final String CONFIG_BUNDLE_NAME = "ConnPoolStrings";
  private static DBconBean DBconn = null;

  public static void main(String[] args) {

  }

  public static DBconBean newInstance() {
    if (DBconn == null) {
      DBconn = new DBconBean();
      DBconn.initConn();
    }
    return DBconn;
  }

  public void initConn() {
    try {
      PropertyResourceBundle configBundle = (PropertyResourceBundle)
                                            PropertyResourceBundle.getBundle(
                                              CONFIG_BUNDLE_NAME);
      ServerType = configBundle.getString("poolServlet.ServerType");
      source = configBundle.getString("poolServlet.source");
      user = configBundle.getString("poolServlet.user");
      password = configBundle.getString("poolServlet.password");
      owner = configBundle.getString("poolServlet.owner");

//      InputStream is = DBconn.getClass().getResourceAsStream(CONFIG_BUNDLE_NAME +
//          ".properties");
//      Properties dbProps = new Properties();
//      try
//      {
//        dbProps.load(is);
//      }
//      catch (Exception e)
//      {
//        System.err.println(
//            "不能读取属性文件. " + "请确保db.properties在CLASSPATH指定的路径中");
//      }
//
//      ServerType = dbProps.getProperty("poolServlet.ServerType");
//      source = dbProps.getProperty("poolServlet.source");
//      user = dbProps.getProperty("poolServlet.user");
//      password = dbProps.getProperty("poolServlet.password");
//      owner = dbProps.getProperty("poolServlet.owner");


      //key Key1 = new key();
      //password = Key1.base64Decode(password);
      //System.out.print(password);
    } catch (Exception e) {
      System.out.println("Properties file exception: " + e.getMessage());
    }

    try {

      Context ctx = null;
      Hashtable parms = null;
      switch (Integer.parseInt(ServerType)) {
      case 0:

        //WebSphere 3.0
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup(source);
        break;
      case 1:

        //WebSphere 3.5
        parms = new Hashtable();
        parms.put(Context.INITIAL_CONTEXT_FACTORY,
                  "com.ibm.ejs.ns.jndi.CNInitialContextFactory");
        ctx = new InitialContext(parms);
        ds = (DataSource) ctx.lookup(source);
        break;
      case 2:

        //WebSphere 4.0
        parms = new Hashtable();
        parms.put(Context.INITIAL_CONTEXT_FACTORY,
                  "com.ibm.websphere.naming.WsnInitialContextFactory");
        ctx = new InitialContext(parms);
        ds = (DataSource) ctx.lookup(source);
        break;
      case 3:

        // 使用tomcat数据源
        InitialContext iCtx = new InitialContext();

        //ctx = (Context) iCtx.lookup("java:comp/env");

        ds = (DataSource) iCtx.lookup("java:comp/env/" + source);

        //ds = (DataSource) iCtx.lookup(source);
        break;
      case 4:

        // WebSphere 5.0
        InitialContext context = new InitialContext();
        ds = (DataSource) context.lookup(source);
        break;

      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Naming service exception: " + e.getMessage());
    }

  }

  public Connection getConnection() {
    Connection conn=null;
    try {
      if (ServerType.equals("99")) {
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        String url = "jdbc:oracle:thin:@localhost:1521:oracle";
        conn = DriverManager.getConnection(url, user, password);
        return conn;
      }

      if (ServerType.equals("3") || ServerType.equals("4")) {
        conn = ds.getConnection();
      } else {
        conn = ds.getConnection(user, password);

      }

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("DataBase Connection Errors: " + e.getMessage());
    }
    return conn;
  }

  public void DisConnect(Connection conn) {
    try {
      conn.setAutoCommit(true);
      //conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("DataBase DisConnect Errors: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("DataBase DisConnect Errors: " + e.getMessage());
      }

    }

  }

  public String getDBUrl() {
    return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbname;
  }

  public String getDBUser() {
    return user;
  }

  public String getDBPassword() {
    return password;
  }

	public static String getServerType(){
		return ServerType;
	}

	public static void setServerType(String serverType){
		ServerType=serverType;
	}

}
