package hadoop.DeseaseDescription;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DeseaseDescriptionSortReducer extends Reducer<IntWritable, Text, Text, IntWritable> {

	public void reduce(IntWritable _key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		// process values
		for (Text val : values) {
			context.write(val, _key);
		}
	}

}
