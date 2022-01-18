package cn.edu.bhu.lang.html;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class HtmlHelperTest {

	
	@Test
	public void containsTagTester() {
		String tag = "dd[class=lemmaWgt-lemmaTitle-title J-lemma-title]";
		String url = "https://baike.baidu.com/item/在家人";
//		String url = "https://baike.baidu.com/item/员相";
		HtmlHelper hl = new HtmlHelper();
		Document doc = hl.getDocument(url);
		boolean bool= hl.containsTag(doc, tag);
		System.out.println(bool);
		String para = hl.getBaikeParaphrase(doc);
		System.out.println(para);
	}
}
