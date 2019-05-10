package MyCrawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class MyCrawlerTest {
	//爬去队列
		private static List<String> allWaitUrls = new ArrayList<>();
		//已爬取集合
		private static Set<String> allOverUrls = new HashSet<>();
		//问题URL集合
		private static Set<String> finalUrls = new HashSet<>();
		//URL深度
		private static Map<String, Integer> allDepth = new HashMap<>();
		//最大爬取深度
		private static final Integer MAX_DEPTH = 3;
		
		
		public static void main(String[] args) throws Exception {
			String url="http://club.xywy.com/keshi/1.html";
			allWaitUrls.add(url);
			allDepth.put(url, 1);
			productUrl();
			//saveData("http://club.xywy.com/question/20190215/180473182.htm");
		}
		
		
		private static void productUrl() throws IOException
		{
			//将最终结果
			File file = new File("./out.log");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file.getName(),true);
			//统计最终URL数量
			int count = 0 ;
			while (allWaitUrls.size()>0) {
				String urlStr=allWaitUrls.get(0);
				int depth = allDepth.get(urlStr);
				//如果已遍历过则清除
				if (allOverUrls.contains(urlStr)) {
					allWaitUrls.remove(0);
					System.out.println("已删除"+urlStr);
					continue;
				}
				//如果超过最大爬取深度则清除队列
				if (allDepth.get(urlStr) > MAX_DEPTH) {
					allWaitUrls.remove(0);
					System.err.println("已达到最大深度，正在删除"+urlStr);
					continue;
				}
				//即未爬取过又没超出最大爬取深度
				//将新的url加入待爬取队列
				//新的url在原来基础上+1
				//页面爬取异常则直接做清除队列处理
				allOverUrls.add(urlStr);
				try {
					Document document = Jsoup.connect(urlStr).get();
					Elements links = document.select("a[href]");
					//匹配http://club.xywy开头的链接
					Pattern club = Pattern.compile("^http://club.xywy.com/");
					//匹配number.html,number.htm的url
					Pattern page = Pattern.compile("^\\d{1,2}.\\w{3,4}$");
					//匹配提问页面
					Pattern question = Pattern.compile("^http://club.xywy.com/.*\\d{8}/.*.\\w{3,4}$");
					for(Element link : links) {
						String subUrl = link.attr("href");
						System.out.println(subUrl);
						//如果该链接是number.html,number.htm的url并且正在爬取的链接是http://club.xywy.com.*number.html,number.htm
						//则将后面的number.html,number.htm替换掉生成新的链接并加入待爬取队列
						if (page.matcher(subUrl).find()) {
//							System.out.println("first");
							 if(Pattern.compile(".*\\d{1,2}.\\w{3,4}$").matcher(urlStr).find()) {
								subUrl=Pattern.compile("\\d{1,2}.\\w{3,4}$").matcher(urlStr).replaceAll(subUrl);
								//新发现链接加入待爬取队列且加深度加1否则不做处理
								if(!allDepth.containsKey(subUrl)) {
									allDepth.put(subUrl, 1);
									allWaitUrls.add(subUrl);
									System.out.println("新增页面 :"+subUrl+", 深度+1");
								}
								continue;
							 }
						}
						//如果是以http://club.xywy.com/question开头的则为有效URL加入到finalUrls中
						if (question.matcher(subUrl).find()) {
//							System.out.println("second");
//							finalUrls.add(subUrl);
							String item = saveData(subUrl);
							if (item.length()>0) {
								System.out.println("已爬取到"+count);
								fileWriter.write(item);
								System.out.println(subUrl+"已加入文件");
								count++;
							}
							allOverUrls.add(subUrl);
							continue;
							}
						//如果是以http://club.xywy开头的则加入待爬取队列，否则不加入
						if(club.matcher(subUrl).find()){
//							System.out.println("third");
							if(!allDepth.containsKey(subUrl)) {
								allDepth.put(subUrl, depth+1);
								allWaitUrls.add(subUrl);
								System.out.println("新增普通链接 :"+subUrl+", 深度+1");
							}
							continue;
						}	
						//不是目标url什么都不做
						continue;
						}
						
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}		
				System.out.println("已遍历"+urlStr);
			}
			fileWriter.close();
		}

		
		
			
		private static String saveData(String url) throws IOException  {
			String item = "";
			int flag=0;
			Document document = Jsoup.connect(url).get();
			//定位科室大类
			Elements keshiGroup = document.getElementsByAttributeValueStarting("href", "http://club.xywy.com/big");
			if(keshiGroup.size()==0) {
				return "";
			}
			for(Element keshi:keshiGroup) {
				item=item+keshi.text()+"||";
//				System.out.println(keshi.text());
			}
			//定位科室小类
			Elements keshiDetail = document.getElementsByAttributeValueStarting("href", "http://club.xywy.com/small");
			if(keshiGroup.size()==0) {
				return "";
			}
			for(Element keshi:keshiDetail) {
				item=item+keshi.text()+"||";
//				System.out.println(keshi.text());
			}
			//定位提问时间
			Elements times = document.getElementsByAttributeValue("class", "User_newbg User_time");
			if(times.size()==0) {
				return "";
			}
			for(Element time:times)
			{
				item=item+time.text()+"||";
//				System.out.println(time.text());
			}
			//定位用户信息
			Elements users = document.select("span");
			if(users.size()==0)
			{
				return "";
			}
			for(Element user:users) {
				Elements user_tmp = Jsoup.parse(user.toString()).getElementsByAttributeValueEnding("class", "User_fticon");
				if(user_tmp.size() > 0 ) { 
					if(Pattern.compile("[男女]").matcher(user.text()).find()) {
						item=item+user.text()+"||";
						flag=1;
						continue;
					}
					if(Pattern.compile("[0-9]{1,3}[月岁]").matcher(user.text()).find()) {
						item=item+user.text()+"||";
						flag=2;
						continue;
					}
					
//					System.out.println(user.text());
				}
			}
			if(flag==0) {
				item=item+"未知"+"||";
				item=item+"未知"+"||";
			}
			if (flag==1) {
				item=item+"未知"+"||";
			}
			//定位问题描述
			Elements questions = document.getElementsByAttributeValue("id", "qdetailc");
			if(questions.size()==0) {
				return "";
			}
			for(Element question:questions) {
				item=item+question.text()+"||";
//				System.out.println(question.text());
			}
			//定位医生信息
			Elements docInfos = document.getElementsByAttributeValue("class", "docall clearfix");
			for (Element docInfo:docInfos) {
//				Elements zhicheng=Jsoup.parse(docInfo.toString()).getElementsByAttributeValueStarting("href", "http://club.xywy.com/doc_card/");
//				if (zhicheng.size()>0) {
//					for (Element zc:zhicheng){
//						item=item+zc.text()+"||";
//					}
//				Elements zhichengSec=Jsoup.parse(docInfo.toString()).getElementsByAttributeValue("class", "fl btn-a");
//				if (zhichengSec.size()>0) {
//					for (Element zc:zhichengSec){
//						item=item+zc.text()+"||";
//					}
//				}
				Elements zhuanchangs = Jsoup.parse(docInfo.toString()).getElementsByAttributeValue("class", "fl graydeep");
				if(zhuanchangs.size()>0) {
					for(Element zhuanchang:zhuanchangs) {
						item=item+zhuanchang.text()+"||";
					}
				}
				}
			
			//定位回答
			Elements answers = document.getElementsByAttributeValueEnding("class", "deepblue");
			if(answers.size()==0) {
				return "";
			}
			for(Element answer:answers)
			{
				try{
					item=item+answer.text().substring(0, answer.text().indexOf("指导建议"))+"||";
					item=item+answer.text().substring(answer.text().indexOf("指导建议"))+"||";
				}
				catch (Exception e) {
					// TODO: handle exception
					try {
						item=item+answer.text().substring(0, answer.text().indexOf("指导意见"))+"||";
						item=item+answer.text().substring(answer.text().indexOf("指导意见"))+"||";
					} catch (Exception e2) {
						// TODO: handle exception
						item=item+answer.text()+"||";
					}
					
				}
//				System.out.println(answer.text());
			}
			item+="\r\n";
			System.out.println(item);
			return item;
		}


}
