package snack.dao;

import java.util.List;
import java.util.Map;

public class GoodsTypeDao {

	/**添加商品类型信息
	 * @param tname
	 * @return
	 */
	public int add(String tname){
		DBHelper db = new DBHelper();
		String sql = "insert into goodstype values(0,?,1)";
		return db.update(sql,tname);
	}
	
	/**查询所有类型信息
	 * @return
	 */
	public List<Map<String , String >> finds(){
		DBHelper db = new DBHelper();
		String sql = "select tid,tname,status from goodstype";
		return db.finds(sql);
	}
	
	/**查询所有可用的类型
	 * @return
	 */
	public List<Map<String , String >> find(){
		DBHelper db = new DBHelper();
		String sql = "select tid,tname from goodstype where status !=0";
		return db.finds(sql);
	}
	public int update(String tid , int status){
		DBHelper db = new DBHelper();
		String sql = "update goodstype set status = ? where tid = ?";
		return db.update(sql, status ,tid);
	}
}
