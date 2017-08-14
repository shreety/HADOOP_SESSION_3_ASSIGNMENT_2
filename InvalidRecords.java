import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
;
public class InvalidRecords {
	 public static class TokenizerMapper

     extends Mapper<Object, Text, Text, IntWritable>{



  private final static IntWritable one = new IntWritable();

  private Text word = new Text();



  public void map(Object key, Text value, Context context

                  ) throws IOException, InterruptedException {

    String line = value.toString();

    if (line.contains("NA")) 
    {
    	System.out.println("Invalid data");
    }
    else
    {
    	word.set(value);
    	context.write(word,one);
    }

    }

}
	 public static void main(String[] args) throws Exception
		{
			Configuration conf = new Configuration();

		    Job job = Job.getInstance(conf, "Invalid Records");

		    job.setJarByClass(InvalidRecords.class);

		    job.setMapperClass(TokenizerMapper.class);
		    job.setNumReduceTasks(0);

		    job.setOutputKeyClass(Text.class);

		    job.setOutputValueClass(IntWritable.class);
		    Path outputPath= new Path(args[1]);

		    FileInputFormat.addInputPath(job, new Path(args[0]));

		    FileOutputFormat.setOutputPath(job, new Path(args[1]));

		    outputPath.getFileSystem(conf).delete(outputPath);
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
}
