package hadoop.DeseaseDescription;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * 患者病情自述分词排序mapper
 * @author wby
 *
 */
public class DeseaseDescriptionSortMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] results = ivalue.toString().split("\t");
		context.write(new IntWritable(Integer.parseInt(results[1])), new Text(results[0]));

	}

}
