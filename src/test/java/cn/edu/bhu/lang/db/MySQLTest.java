package cn.edu.bhu.lang.db;

import java.sql.SQLException;
import java.util.List;

import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;

import cn.edu.bhu.lang.html.HtmlHelper;

public class MySQLTest {

	@Test
	public void deleteDuplicationTester() throws SQLException {
		String sql = "select * from annotation where word in (select   word from   annotation group by   word having count(word) >1) ORDER BY word";
		MySQLHelper mySQLHelper = new MySQLHelper();
		List<Integer> idList = mySQLHelper.getDupID(sql);

		mySQLHelper.update(idList);
		mySQLHelper.close();
	}

	@Test
	public void updateAnnotationByBaidu() throws SQLException, UnsupportedEncodingException {
		String sql = "select id, word from annotation where status>0 and id >758468";
		String tag = "dd[class=lemmaWgt-lemmaTitle-title J-lemma-title]";

//		String url = "https://baike.baidu.com/item/员相";
		MySQLHelper mySQLHelper = new MySQLHelper();

		ResultSet rs = mySQLHelper.getDBResult(sql);
		HtmlHelper hl = new HtmlHelper();
		PreparedStatement stmt = null;
		// 展开结果集数据库
		while (rs.next()) {
			// 通过字段检索
			int id = rs.getInt("id");
			String word = rs.getString("word");
			String url = hl.getBaiduBaikeUrl(word);
			Document doc = hl.getDocument(url);
			
			if(doc == null) {
				String upsql = "update annotation set status = 0 where id = ?";
				stmt = mySQLHelper.getConn().prepareStatement(upsql);
				stmt.setInt(1, id);
				int result = stmt.executeUpdate();// 返回值代表收到影响的行数
				if (result > 0) {
					System.out.println(id + "修改成功");
				} else {
					System.out.println(id + "修改失败");
				}
				continue;
			}
			
			boolean bool = hl.containsTag(doc, tag);
			
			if (bool) {

				String para = hl.getBaikeParaphrase(doc);
//				para = new String(para.getBytes(),"ISO8859_1"); 
				String upsql = "update annotation set paraphrase=? where id = ?";
				

				try {
					stmt = mySQLHelper.getConn().prepareStatement(upsql);
					stmt.setInt(2, id);
					stmt.setString(1, para);
					int result = stmt.executeUpdate();// 返回值代表收到影响的行数
					if (result > 0) {
						System.out.println(id + "修改成功");
					} else {
						System.out.println(id + "修改失败");
					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else {
				String upsql = "update annotation set status = -1 where id = ?";
				stmt = mySQLHelper.getConn().prepareStatement(upsql);
				stmt.setInt(1, id);
				int result = stmt.executeUpdate();// 返回值代表收到影响的行数
				if (result > 0) {
					System.out.println(id + "修改成功");
				} else {
					System.out.println(id + "修改失败");
				}
			}
		}
		mySQLHelper.close();
	}
}
