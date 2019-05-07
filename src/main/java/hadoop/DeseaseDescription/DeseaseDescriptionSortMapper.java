package hadoop.DeseaseDescription;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DeseaseDescriptionSortMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] results = ivalue.toString().split("\t");
		context.write(new IntWritable(Integer.parseInt(results[1])), new Text(results[0]));

	}

}
