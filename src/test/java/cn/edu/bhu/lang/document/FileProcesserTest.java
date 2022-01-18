package cn.edu.bhu.lang.document;

import java.util.HashSet;

import org.bhu.io.FileReader;
import org.junit.Test;

public class FileProcesserTest {

	
	@Test
	public void findWordTester() {
		FileProcesser sl = new FileProcesser();
		String word= "红军树";
		String dir= "E:\\data\\lex\\ycy\\城市信息大全词性标记\\四川";
		sl.findWord(dir, word);
	}
	
	@Test
	public void demo1115() {
		FileProcesser sl = new FileProcesser();
		HashSet<String> set = sl.loadDic();
		String name = "lch";
		String dir =  "E:\\data\\lex\\0115\\"+name+"\\";
		

//		String word = "����";
//		if(set.contains(word)) {
//			System.out.println(word);
//		}
		
		sl.SogouLexDemo2(set,dir,name,"E:\\data\\lex\\0115\\");
	}
	
	@Test
	public void demo2() {
//		FileReader reader = new FileReader("E:\\data\\lex\\0115\\bxy\\工程与应用科学\\包装\\特种纸名称.txt","GB18030");
		FileReader reader = new FileReader("E:\\data\\lex\\0115\\bxy\\农林渔畜\\农业\\农业和农村常用词汇.txt");
		String line = reader.read2End();
		System.out.println(line);
	}
//	
//	
//	@Test
//	public void demo3() {
//		String path =  "E:\\data\\lex\\hsj\\��\\����\\������.txt";
//		FileReader reader = new FileReader(path);
//		
//		System.out.println(reader.getEncoding());
//	}
}
