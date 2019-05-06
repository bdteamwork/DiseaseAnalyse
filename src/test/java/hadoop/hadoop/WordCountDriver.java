package hadoop.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "WordCount");
		job.setJarByClass(hadoop.hadoop.WordCountDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.hadoop.WordCountMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.hadoop.WordCountReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.16.130:9000/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.16.130:9000/out"));

		if (!job.waitForCompletion(true))
			return;
	}

}
