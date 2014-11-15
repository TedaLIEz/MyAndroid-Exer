/*
 * Attack on spiders!
 */
package com.hustascii.torrentdemo.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import android.util.Log;

import com.hustascii.torrentdemo.beans.Result;

public class Spider {

	private String abs = "http://btkitty.org";
	public ArrayList<Result> list;
	public List<Element> filedetail;
	public String download_url;
	private Map<String, Map<String, List<Element>>> map;

	public Spider() {
		list = new ArrayList<Result>();
		filedetail=new ArrayList<Element>();
		download_url = new String();
		map = new HashMap<String, Map<String, List<Element>>>();
	}

	public ArrayList<Result> collect(String key) throws IOException {

		Document doc = Jsoup.connect(abs).data("keyword", key).timeout(60000)
				.post();
		Elements tmp0 = doc.getElementsByClass("list");
		Elements urls = tmp0.select("a[href^=/torrent/]");
		Elements names = tmp0
				.select("a[href^=/torrent/][target*=_blank]:matches((.*?)torrent)");
		Elements tmp1 = doc.getElementsByClass("option");
		Elements sizes = tmp1.select("span:matches(Size)");

		for (int i = 0; i < names.size(); i++) {
			if (urls.get(i).select("[style]").hasText()) {
				urls.remove(i);
			}
		}

		for (int i = 0; i < names.size(); i++) {

			Result rs = new Result();
			rs.setUrl(urls.get(i).attr("abs:href"));
			rs.setName(names.get(i).select("a[target]").text());
			rs.setSize(sizes.get(i).text().replaceAll("Size", "内部文件大小"));
			list.add(rs);

		}
		return list;
	}
	/*
	 * Map including Map<url,Map<download_url,filedetail>>
	 */
	public Map<String, Map<String, List<Element>>> map(String url)
			throws IOException {
		Document doc1 = Jsoup.connect(url).timeout(60000).post();
		Elements download = doc1.select("a[href$=torrent]");
		Elements filedetails = doc1.select("dd.filelist>p");
		if(filedetails.is("span")){
			filedetails.select("span").remove();
			//Log.i("test", "我进行了移除工作");
		}
		Map<String, List<Element>> map_tmp = new HashMap<String, List<Element>>();
		for (Element filename:filedetails) {
			filedetail.add(filename);
			if(filename.text().isEmpty()){
			Log.i("test","来了个空文本");}
		}
		map_tmp.put(download.attr("href"), filedetail);
		map.put(url, map_tmp);
		return map;
	}

}
