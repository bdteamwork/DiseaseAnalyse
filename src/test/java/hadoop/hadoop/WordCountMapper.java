package hadoop.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import jeasy.analysis.MMAnalyzer;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	MMAnalyzer mm = new MMAnalyzer();
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException   {
		//实现分词操作
		String [] results = mm.segment(ivalue.toString(), " ").split(" ");
		for(String result: results){
			context.write(new Text(result), new IntWritable(1));
		}
	}

}
