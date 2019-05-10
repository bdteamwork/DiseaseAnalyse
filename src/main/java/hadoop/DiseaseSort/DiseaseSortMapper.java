package hadoop.DiseaseSort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DiseaseSortMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] results = ivalue.toString().split("\t");
		context.write(new Text(results[0]), new IntWritable(Integer.parseInt(results[1])));

	}
}
