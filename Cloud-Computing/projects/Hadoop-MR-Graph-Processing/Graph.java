import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

class Vertex implements Writable{
	int tag;
	long group;
	long VID;
	String adjacent = "";
	
	Vertex(){}
	
	Vertex(int t, long g)
	{
		this.tag = t;
		this.group = g;
	}
	
	Vertex(int t, long g, long v, String a)
	{
		this.tag = t;
		this.group = g;
		this.VID = v;
		this.adjacent = a;
	}

	public void readFields(DataInput in) throws IOException {
		
		tag = in.readInt();
		group = in.readLong();
		VID = in.readLong();
		adjacent = in.readLine();
		
	}

	public void write(DataOutput out) throws IOException {
		
		out.writeInt(tag);
		out.writeLong(group);
		out.writeLong(VID);
		out.writeBytes(adjacent);
	}
	
}

public class Graph extends Configured implements Tool {
	
	//First mapper and reducer job
	public static class MapperInit extends Mapper<LongWritable,Text,LongWritable,Vertex>{
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			Scanner sc = new Scanner(value.toString()).useDelimiter(",");
			int vid = sc.nextInt();
			String adj =Integer.toString(sc.nextInt());
			while(sc.hasNext())
			{
				adj = adj + "," + sc.nextInt();
			}
			
			Vertex v = new Vertex(0,vid,vid,adj);
			context.write(new LongWritable(v.VID), v);
		}
	}
	
	public static class ReducerInit extends Reducer<LongWritable,Vertex,Text,Text>
	{
		@Override
		public void reduce(LongWritable key, Iterable<Vertex> value,Context context) throws IOException,InterruptedException{
			for(Vertex v: value)
			{
				Text values = new Text();
				Text keys = new Text();
				keys.set(key.toString());
				values.set("," + v.tag + "," + v.group + "," + v.VID + "," + v.adjacent);
				context.write(keys, values);
			}
		}
	}
	
	//Iterative mapper and reducer jobs
	public static class MapperIter extends Mapper<LongWritable,Text,LongWritable,Vertex>{
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			Scanner sc = new Scanner(value.toString().replaceAll("\\s+", "")).useDelimiter(",");
			int keyz = sc.nextInt();
			int tag = sc.nextInt();
			int group = sc.nextInt();
			int vid = sc.nextInt();
			String adjacent = "";
			while(sc.hasNext())
			{
				if(adjacent.isEmpty())
				{
					adjacent = Integer.toString(sc.nextInt());
				}
				else
				{
					adjacent = adjacent + "," + Integer.toString(sc.nextInt());
				}
			}
			Vertex v = new Vertex(tag,group,vid,adjacent);
			context.write(new LongWritable(v.VID), v);
			
			if(!adjacent.isEmpty())
			{
				Scanner sc1 = new Scanner(adjacent).useDelimiter(",");
				while(sc1.hasNext())
				{
					Vertex v2 = new Vertex(1,v.group);
					context.write(new LongWritable(sc1.nextInt()), v2);
				}
			}
		}
	}
	
	public static class ReducerIter extends Reducer<LongWritable,Vertex,Text,Text>
	{
		@Override
		public void reduce(LongWritable key, Iterable<Vertex> value,Context context) throws IOException,InterruptedException{
			long m = Long.MAX_VALUE;
			Text a = new Text();
			Text b = new Text();
			String adjacent = "";
			for(Vertex v:value)
			{
				if(v.tag == 0)
				{
					adjacent = v.adjacent;
				}
				m = Math.min(m, v.group);
			}
			a.set(Long.toString(m));
			Vertex v1 = new Vertex(0,m,key.get(),adjacent);
			b.set("," + 0 + "," + v1.group + "," + v1.VID + "," + v1.adjacent);
			context.write(a,b);
		}
	}
	
	// Final Mapper and Reducer
	public static class MapperFinal extends Mapper<LongWritable,Text,LongWritable,IntWritable>{
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			Scanner sc = new Scanner(value.toString().replaceAll("\\s+", "")).useDelimiter(",");
			int group = sc.nextInt();
			context.write(new LongWritable(group), new IntWritable(1));
		}
	}
	
	public static class ReducerFinal extends Reducer<LongWritable,IntWritable,Text,Text>
	{
		@Override
		public void reduce(LongWritable key, Iterable<IntWritable> value,Context context) throws IOException,InterruptedException{
			int m = 0;
			for(IntWritable v:value)
			{
				m = m + v.get();
			}
			Text a = new Text();
			Text b = new Text();
			a.set(Long.toString(key.get()));
			b.set(Integer.toString(m));
			context.write(a,b);
		}
	}
	
    public static void main ( String[] args ) throws Exception {
    	int foo = ToolRunner.run(new Configuration(), new Graph(),args);
    	System.exit(foo);
    }
    
    public int run(String[] args)throws Exception
    {
    	/* ... First Map-Reduce job to read the graph */
    	Configuration config = new Configuration();
    	Job job1 = Job.getInstance(config, "Project 3");
    	job1.setJarByClass(Graph.class);
    	job1.setMapperClass(MapperInit.class);
    	job1.setReducerClass(ReducerInit.class);
    	job1.setMapOutputKeyClass(LongWritable.class);
    	job1.setMapOutputValueClass(Vertex.class);
    	job1.setOutputKeyClass(Text.class);
    	job1.setOutputValueClass(Text.class);
    	FileInputFormat.addInputPath(job1, new Path(args[0]));
    	FileOutputFormat.setOutputPath(job1, new Path(args[1] + "/f0"));
    	job1.waitForCompletion(true);
		
		/* ... Second Map-Reduce job to propagate the group number */
		for(short i = 0; i< 5; i++)
		{
			Configuration config_iter = new Configuration();
	    	Job job_iter = Job.getInstance(config_iter, "Project3-iter");
	    	job_iter.setJarByClass(Graph.class);
	    	job_iter.setMapperClass(MapperIter.class);
	    	job_iter.setReducerClass(ReducerIter.class);
	    	job_iter.setMapOutputKeyClass(LongWritable.class);
	    	job_iter.setMapOutputValueClass(Vertex.class);
	    	job_iter.setOutputKeyClass(Text.class);
	    	job_iter.setOutputValueClass(Text.class);
	    	FileInputFormat.addInputPath(job_iter, new Path(args[1] + "/f" + i));
	    	FileOutputFormat.setOutputPath(job_iter, new Path(args[1] + "/f" + (i + 1)));
	    	job_iter.waitForCompletion(true);
		}
		
		/* ... Final Map-Reduce job to calculate the connected component sizes */
		Configuration config_final = new Configuration();
    	Job jobFinal = Job.getInstance(config_final, "Project3-final");
    	jobFinal.setJarByClass(Graph.class);
    	jobFinal.setMapperClass(MapperFinal.class);
    	jobFinal.setReducerClass(ReducerFinal.class);
    	jobFinal.setMapOutputKeyClass(LongWritable.class);
    	jobFinal.setMapOutputValueClass(IntWritable.class);
    	jobFinal.setOutputKeyClass(Text.class);
    	jobFinal.setOutputValueClass(Text.class);
    	FileInputFormat.addInputPath(jobFinal, new Path(args[1] + "/f5"));
    	FileOutputFormat.setOutputPath(jobFinal, new Path(args[2]));
    	jobFinal.waitForCompletion(true);
    	
		return 0;
    }
}