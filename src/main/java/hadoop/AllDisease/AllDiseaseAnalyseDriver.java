package hadoop.AllDisease;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AllDiseaseAnalyseDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "AllDisease");
		job.setJarByClass(hadoop.AllDisease.AllDiseaseAnalyseDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.AllDisease.AllDiseaseAnalyseMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.AllDisease.AllDiseaseAnalyseReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.64.129:9000/disease/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.64.129:9000/disease/out/AllDiseaseNumber"));

		if (!job.waitForCompletion(true))
			return;
	}
}
