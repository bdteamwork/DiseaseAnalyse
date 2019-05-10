package hadoop.DiseaseByGender;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DiseaseByGenderDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "DiseaseByGender");
		job.setJarByClass(hadoop.DiseaseByGender.DiseaseByGenderDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.DiseaseByGender.DiseaseByGenderMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.DiseaseByGender.DiseaseByGenderReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.64.129:9000/disease/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.64.129:9000/disease/out/Disease-Woman"));

		if (!job.waitForCompletion(true))
			return;
	}
}
