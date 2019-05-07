package hadoop.DeseaseDescription;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DeseaseDescriptionSortDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "DeseaseDescriptionSort");
		job.setJarByClass(hadoop.DeseaseDescription.DeseaseDescriptionSortDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.DeseaseDescription.DeseaseDescriptionSortMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.DeseaseDescription.DeseaseDescriptionSortReducer.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.16.130:9000/desease/out/deseaseDescription"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.16.130:9000/desease/sort/deseaseDescription"));

		if (!job.waitForCompletion(true))
			return;
	}

}
