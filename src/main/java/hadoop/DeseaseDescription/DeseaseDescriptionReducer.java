package hadoop.DeseaseDescription;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * 患者病情自述reducer
 * @author wby
 *
 */
public class DeseaseDescriptionReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// process values
		int sum = 0;
		for (IntWritable  val : values) {
			sum+=val.get();
		}
		context.write(_key, new IntWritable(sum));
	}

}
