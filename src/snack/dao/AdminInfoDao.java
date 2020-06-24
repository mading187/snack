package snack.dao;

import java.util.Map;

public class AdminInfoDao {
	
	public Map<String, String > login(String account ,String pwd){
		DBHelper db = new DBHelper();
		String sql = "select aid,aname,tel from adminInfo where (aname=? or tel=?) and pwd=md5(?)";
		return db.find(sql, account,account,pwd);
	}
	
	public static void main(String[] args) {
		AdminInfoDao  adminInfoDao = new AdminInfoDao();
		System.out.println( adminInfoDao.login("17373436415", "123321") );
	}

}
