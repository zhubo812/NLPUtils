package cn.edu.bhu.lang.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MySQLHelper {

	// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://192.168.1.106:3306/test?characterEncoding=UTF-8";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs= null;
 // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "admin";
    
    
    public MySQLHelper() {
    	 try {
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);
   
			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   
			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public MySQLHelper(String db, String usrname, String psw) {
   	 try {
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);
  
			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(db,usrname,psw);
  
			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    
    
    /***
     * 2022/01/17mysql数据库词条查重
     *
     */
    public List<Integer> getDupID(String sql) {
    	HashSet<String> set = new HashSet<String>();
    	List<Integer> idList = new ArrayList<Integer>();
    	
        try{
           
            
            rs = stmt.executeQuery(sql);
         // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String word = rs.getString("word");
                if(set.contains(word)) {
                	idList.add(id);
                }else {
                	set.add(word);
                }
    
             
            }
//            // 完成后关闭
//            rs.close();
//            stmt.close();
//            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        System.out.println("Goodbye!");
        return idList;
	}
    
    
    public ResultSet getDBResult(String sql) {
        ResultSet rs=null;
            // 注册 JDBC 驱动
            try {
			
				stmt = conn.createStatement();

					rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        return rs;
	}
    
    
    
  public Statement getStmt() {
		return stmt;
	}


	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
	


	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	}


	//修改语句
    public void update(List<Integer> idlist) throws SQLException {

        PreparedStatement stmt = null;
        try {

            
            for(int id : idlist) {
            String sql = "update annotation set status = -1 where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int result =stmt.executeUpdate();// 返回值代表收到影响的行数
            if(result>0) {
                System.out.println(id+"修改成功");
            }else {
                System.out.println(id+"修改失败");
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    

    
  //创建数据库的连接
    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return   DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }
    
    //关闭数据库的连接
    public void close() throws SQLException {
        if(rs!=null)
            rs.close();
        if(stmt!=null)
            stmt.close();
        if(conn!=null)
            conn.close();
    }
}
