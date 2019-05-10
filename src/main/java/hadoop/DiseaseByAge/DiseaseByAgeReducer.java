package hadoop.DiseaseByAge;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class DiseaseByAgeReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// process values
		System.out.println("in reduce");
		int sum = 0;
		for (IntWritable  val : values) {
			sum+=val.get();
		}
		context.write(_key, new IntWritable(sum));
	}
}
