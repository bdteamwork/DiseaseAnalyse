package hadoop.DiseaseByGender;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DiseaseByGenderMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	private static String gender = "女";
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] str = ivalue.toString().split("\\|");
		//排除异常数据
		if(str.length!=9){
			return ;
		}
		String diseaseName = str[1];
		String genderNow = str[3];
		
		if(genderNow.equals(gender)) {
			System.out.println(genderNow + "---" + diseaseName);
			context.write(new Text(diseaseName), new IntWritable(1));
		}
	}
}
