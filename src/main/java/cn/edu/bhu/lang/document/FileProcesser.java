package cn.edu.bhu.lang.document;

import java.util.HashSet;
import java.util.List;

import org.bhu.io.FileReader;
import org.bhu.io.FileWriter;
import org.bhu.nlp.utils.FileUtil;

public class FileProcesser {

	private final static String TAB = "\t";

	public void SogouLexDemo2(HashSet<String> set, String dir, String name, String outPath) {

		List<String> fileList = FileUtil.getAllFiles(dir);
		FileWriter writer = new FileWriter(outPath + name + ".txt");
		for (String file : fileList) {
			FileReader reader = new FileReader(file);
			System.out.println(reader.getFileName() + TAB + reader.getEncoding());
			String line;

			String[] strs = file.split("\\\\");
			String subClass = strs[5];
			for (; (line = reader.readLine()) != null;) {
				String[] items = line.trim().split("\t");
				if (items.length != 2) {
					continue;
				}
				if (set.contains(items[0])) {
					continue;
				}
				String sline = 0 + TAB + items[0] + TAB + items[0].length() + TAB + items[1] + TAB + subClass + TAB
						+ name;
				writer.writeLine(sline);
			}
		}
		writer.close();
	}

	
	/***
	 * 查找某个词所在的文件
	 * @param dir
	 * @param word
	 */
	public void findWord(String dir, String word) {
		List<String> paths = FileUtil.getAllFiles(dir);
		for (String path : paths) {

			FileReader reader = new FileReader(path);
//			System.out.println(path);
			String line;
			String[] strs = path.split("\\\\");

			for (; (line = reader.readLine()) != null;) {
				if (line.contains(word)) {
					System.out.println(path);
				}
			}

		}
	}

	public void getLexFile(String name, String dir, HashSet<String> set) {

		List<String> paths = FileUtil.getAllFiles(dir);
		FileWriter writer = new FileWriter("E:\\data\\lex\\0115\\" + name + ".txt", "utf-8");
		for (String path : paths) {
//			if(!path.endsWith(".txt")) {
//				continue;
//			}
			FileReader reader = new FileReader(path);
			System.out.println(path);
			String line;
			String[] strs = path.split("\\\\");
			String subclass = strs[5];
			for (; (line = reader.readLine()) != null;) {
				String[] items = line.split("\t");
				if (items.length == 1 || line.trim().length() == 0) {
					continue;
				}
				String word = items[0];
				String nature = items[1];
				if (set.contains(word.trim())) {
					continue;
				}
				int len = word.length();
				writer.writeLine(word + "\t" + nature + "\t" + len + "\t" + subclass + "\t" + name);
			}
		}

		writer.close();
	}

	public void wordFilter(HashSet<String> set) {
		String path = "E:\\data\\lex\\annotation.txt";
		FileReader reader = new FileReader(path, "utf-8");
		FileWriter writer = new FileWriter("E:\\data\\lex\\wordset.txt", "utf-8");
		String line;

		for (; (line = reader.readLine()) != null;) {
			if (line.startsWith("�ƻ�")) {
				System.out.println(line);
			}
			line = line.replace("\"", "");
			String[] items = line.split("\t");
			String word = items[2];
			if (set.contains(word)) {

				System.out.println(line);
				continue;
			}
			writer.writeLine(line.trim());
		}
		writer.close();
	}

	/**
	 * 载入词典
	 * 
	 * @return
	 */
	public HashSet<String> loadDic() {
		HashSet<String> set = new HashSet<String>();
		System.out.println("loading dictionary");
		String path = "E:\\data\\core.ini";
		FileReader reader = new FileReader(path, "utf-8");
		String line;

		for (; (line = reader.readLine()) != null;) {
//			if(line.startsWith("�ƻ�")) {
//				System.out.println(line);
//			}
			String[] items = line.split("\t");
			String word = items[0];
			set.add(word);
		}
		return set;
	}
}
