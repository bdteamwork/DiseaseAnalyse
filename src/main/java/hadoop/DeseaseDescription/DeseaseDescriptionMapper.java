package hadoop.DeseaseDescription;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import jeasy.analysis.MMAnalyzer;
/**
 * 患者病情自述mapper
 * @author wby
 *
 */
public class DeseaseDescriptionMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	//停用词目录
	static String stopwordspath = "/Users/wby/Downloads/stopwords.txt";
	static Set<String> stopwordsList ;
	
	//分词
	MMAnalyzer mm = new MMAnalyzer();
	
	protected void setup(Context context) throws IOException,InterruptedException {
		stopwordsList=new TreeSet<String>();

        try{
    		FileReader reader = new FileReader(stopwordspath);
    		BufferedReader br = new BufferedReader(reader);
    		String line;
    		while((line = br.readLine())!=null ){
    			stopwordsList.add(line);
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    } 
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] str = ivalue.toString().split("\\|");
		//排除异常数据
		if(str.length!=9){
			return ;
		}
		//患者病情自述
		String deseaseDescription = str[5].toString();
		String [] results = mm.segment(deseaseDescription, " ").split(" ");
		for(String result: results){
	        if (Pattern.compile(".*\\d+.*").matcher(result).matches() || Pattern.compile(".*[a-zA-z].*").matcher(result).matches()) {
	        	//有数字或者字母排除
	            return ;
	        }else{
	        	//对比停用词，如果存在，则不保存
	        	if(!stopwordsList.contains(result)){
	    			context.write(new Text(result), new IntWritable(1));
	        	}
	        	
	        }
			
		}
	}

}
