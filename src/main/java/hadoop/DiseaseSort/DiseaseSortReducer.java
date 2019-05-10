package hadoop.DiseaseSort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DiseaseSortReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

	public void reduce(IntWritable _key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		// process values
		for (Text val : values) {
			context.write(_key, val);
		}
	}
}
