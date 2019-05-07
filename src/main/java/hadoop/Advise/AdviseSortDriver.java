package hadoop.Advise;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 医生对病情指导建议分词排序
 * @author wby
 *
 */
public class AdviseSortDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "AnalyseSort");
		job.setJarByClass(hadoop.Advise.AdviseSortDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.Advise.AdviseSortMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.Advise.AdviseSortReducer.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.16.130:9000/desease/out/advise"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.16.130:9000/desease/sort/advise"));

		if (!job.waitForCompletion(true))
			return;
	}

}
