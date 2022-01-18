package cn.edu.bhu.lang.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlHelper {

	private static final String BAIDU_BAIKE= "https://baike.baidu.com/item/";
	private static final String BAIDU_BAIKE_PARA_TAG = "div[class=para]";
	
	/***
	 * 解析百度百科词条的释义
	 * @param document
	 * @return
	 */
	public String getBaikeParaphrase(Document document) {
		String rs = "";
		Elements els = document.select(BAIDU_BAIKE_PARA_TAG);
		for(Element el : els) {
			rs+= el.text();
		}
		return rs;
	}
	/***
	 * 获取document
	 * @param url
	 * @return
	 */
	public Document getDocument(String url) {
		Document document = null;
		
		try {
			document=Jsoup.connect(url).timeout(3000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
		return document;
	}
	
	/**
	 * 检查document是否存在指定tag
	 * @param tag
	 * @param document
	 * @return
	 */
	public boolean containsTag( Document document,String tag) {

			Elements els = document.select(tag);
			if(els.size()>0) {
				return true;
			}
		return false;
	}
	
	public String getBaiduBaikeUrl(String word) {
		return BAIDU_BAIKE+word;
	}
}
