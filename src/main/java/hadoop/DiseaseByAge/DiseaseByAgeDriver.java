package hadoop.DiseaseByAge;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DiseaseByAgeDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "DiseaseByAge");
		job.setJarByClass(hadoop.DiseaseByAge.DiseaseByAgeDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.DiseaseByAge.DiseaseByAgeMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.DiseaseByAge.DiseaseByAgeReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.64.129:9000/disease/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.64.129:9000/disease/out/Disease-70-999"));

		if (!job.waitForCompletion(true))
			return;
	}
}
