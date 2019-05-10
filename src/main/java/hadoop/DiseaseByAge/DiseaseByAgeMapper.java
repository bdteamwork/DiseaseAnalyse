package hadoop.DiseaseByAge;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DiseaseByAgeMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	private static String gender = "女";
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] str = ivalue.toString().split("\\|");
		//排除异常数据
		if(str.length!=9){
			return ;
		}
		String diseaseName = str[1];
		
		if(str[4].equals("未知")) {
			return;
		}
		String ageString = str[4].substring(0, str[4].length()-1);
//		System.out.println(ageString + "岁---");
		int age = Integer.parseInt(ageString);
		
		if(age>70 && age<=999) {
			System.out.println(age + "岁---" + diseaseName);
			context.write(new Text(diseaseName), new IntWritable(1));
		}
	}
}