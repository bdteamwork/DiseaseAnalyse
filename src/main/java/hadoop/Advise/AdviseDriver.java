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
/***
 * 医生对患者病情分析分词driver
 * @author wby
 *
 */
public class AdviseDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Advise");
		job.setJarByClass(hadoop.Advise.AdviseDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(hadoop.Advise.AdviseMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(hadoop.Advise.AdviseReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.16.130:9000/desease/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.16.130:9000/desease/out/advise"));

		if (!job.waitForCompletion(true))
			return;
	}

}
