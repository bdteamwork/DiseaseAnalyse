package hadoop.AllDisease;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class AllDiseaseAnalyseMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		System.out.println("in map");
		String[] str = ivalue.toString().split("\\|");
		//排除异常数据
		if(str.length!=9){
			return ;
		}
		String diseaseName = str[1];
		System.out.println(diseaseName);
		context.write(new Text(diseaseName), new IntWritable(1));
	}
}
