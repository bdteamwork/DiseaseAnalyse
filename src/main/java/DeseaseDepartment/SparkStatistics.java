package DeseaseDepartment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkStatistics {
	public static void main(String[] args) {
		// 初始化核心操作对象
		SparkConf conf = new SparkConf();
		conf = conf.setAppName("deseasestatisticcs");
		conf = conf.setMaster("local");
		Map<String,Set> map = new HashMap<String,Set>();
		
		JavaSparkContext ctx = new JavaSparkContext(conf);
		JavaRDD<String> lines = ctx.textFile("hdfs://192.168.16.130:9000/desease/in");

		
		JavaPairRDD<String, String> rddPair = lines.mapToPair(new PairFunction<String, String, String>() {
			@Override
			public Tuple2<String, String> call(String str) throws Exception {
            	String[] res = str.split("\\|");
            	if(res.length !=9){
    				return new Tuple2<String, String>("1", "0");
    			}
				return new Tuple2<String, String>(res[0], res[1]);
			}
		});
		rddPair = rddPair.reduceByKey(new Function2<String, String, String>(){
			@Override
			public String call(String arg0, String arg1) throws Exception {

				if(arg0.contains(arg1)){
					return arg0;
				}else{
					return arg0 + "/" + arg1;
				}
				
			}
		});
//		
		rddPair.saveAsTextFile("hdfs://192.168.16.130:9000/desease/statistic");
		
		

		}
}
