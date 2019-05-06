package hadoop.hadoop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class NewsCrawler {
	//准备一个url的等待爬取队列
	private static List<String> allWaitUrls = new ArrayList();
	//准备一个已经爬取完成的url集合，这里使用不允许重复的set集合
	private static Set<String> allOverUrls = new HashSet<>();
	//准备一个限制爬取深度的集合
	private static Map<String,Integer> allDepth = new HashMap<String,Integer>();
	//限制最大深度
	private static final Integer MAX_DEPTH = 3;
	//准备保存hdfs的hadoop的api核心类
	private static Configuration conf = new Configuration();
	private static final String ROOT_PATH = "hdfs://192.168.16.130:9000/";
	private static Path rootPath = new Path(ROOT_PATH);
	private static FileSystem fs;
	private static Integer count =1;
	static{
		try{
			fs = rootPath.getFileSystem(conf);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args){
		//添加爬取的种子地址
		allWaitUrls.add("http://news.163.com/");
		allDepth.put("http://news.163.com/", 1);
		//执行爬取任务
		crawData();
	}
	private static void crawData() {
		// TODO Auto-generated method stub
		if(allWaitUrls.size()>0){
			String urlStr = allWaitUrls.get(0);
			allWaitUrls.remove(0);
			allOverUrls.add(urlStr);
			System.out.println("正在处理：" + urlStr);
			//取得深度
			int depth = allDepth.get(urlStr);
			try{
				//解析这个url的数据，这里直接使用java自带的基础类库来实现url的链接读取
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				//读取里面的数据
				InputStream is = conn.getInputStream();
				//逐行读取内容
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"GBK"));
				String line = null;
				while((line = reader.readLine())!=null){
					//提取其中的有效信息
					// 1、提取新闻内容
					// 单独编写一个提取内容的方法
					if(urlStr.matches(".*/\\d{2}/\\d{4}/\\d{2}/[0-9A-Z]+\\.html")){
						// 需要提取有效信息
						// 这里为了省事儿，我只提取新闻的标题
						if(line.contains("<title>")){
							String title = line.substring(line.indexOf("<title>")+7);
							title = title.substring(0, title.indexOf("<"));
							//将标题保存到HDFS
							// 这里需要用Java HDFS API
							// 在HDFS中建立一个文件
							Path path = new Path(ROOT_PATH+"in/"+System.currentTimeMillis()+".txt");
							FSDataOutputStream os = fs.create(path);
							os.write(title.getBytes());
							os.close();
							System.out.println("已经完成： " + (count++) + " 个新闻的处理");
						}
					}
					//2、提取URL地址，同时添加到队列中
					//取得所有页面上超链接地址：<a href>
					//这里直接使用正则表达式，或者字符串操作来完成。
					if(line.contains("<a")){
						//有超链接，取得其中 href=里面的内容，这里直接使用字符串操作来截取
						String result = line.substring(line.indexOf("<a"));
						if(result.contains("href=")){
							result = result.substring(result.indexOf("href=")+6);
							result = result.substring(0, result.indexOf("\"")).trim();
							//判断地址是否合法
							if(result.startsWith("http:") || result.startsWith("https:")){
								//判断是否在163上存在
								if(result.contains("163.com")){
									// 判断该地址是否爬取过或在队列中已存在
									if(!allWaitUrls.contains(result)||allOverUrls.contains(result)){
										//彻底是新的，没爬过，加入队列
										allWaitUrls.add(result);
										allDepth.put(result, depth+1);
										
									}
								}
							}
						}
					}
					
				}
			}catch(Exception e){
				System.out.println(e);
				
			}
			//继续爬取下一个
			crawData();
			
		}
			
	}
			
	

}
