import java.io.*;
import java.util.Scanner;
import java.util.Vector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


/* single color intensity */
class Color implements WritableComparable<Color> {
    public short type;       /* red=1, green=2, blue=3 */
    public short intensity;  /* between 0 and 255 */
    /* need class constructors, toString, write, readFields, and compareTo methods */

    Color(){
    }
    Color(short type, short intensity){
            this.type = type;
            this.intensity = intensity;
    }

    public void write(DataOutput out) throws IOException {
            out.writeShort(type);
            out.writeShort(intensity);
    }

    public void readFields(DataInput in) throws IOException {
            type = in.readShort();
            intensity = in.readShort();
    }

    public int compareTo(Color c){
            if(this.type < c.type){
                   return -1;
            }
            if( this.type > c.type) {
                    return 1;
            }
            if(this.type == c.type) {
                    if(this.intensity < c.intensity){
                            return -1;
                    }
                    if(this.intensity > c.intensity){
                            return 1;
                    }

            }
	return 0;
    }

    public String toString() {
            return this.type + ", " + this.intensity + " ";
    }
}


public class Histogram {
    public static class HistogramMapper extends Mapper<Object,Text,Color,IntWritable> {
        @Override
        public void map ( Object key, Text value, Context context )
                        throws IOException, InterruptedException {
            /* write your mapper code */
            Scanner s = new Scanner(value.toString()).useDelimiter(",");
            short r_pixel_value = s.nextShort();
            short g_pixel_value = s.nextShort();
            short b_pixel_value = s.nextShort();

            Color red = new Color((short)1, r_pixel_value);
            Color green = new Color((short)2, g_pixel_value);
            Color blue = new Color((short)3, b_pixel_value);

            context.write(red, new IntWritable(1));
            context.write(green, new IntWritable(1));
            context.write(blue, new IntWritable(1));
            s.close();
        }
    }

    public static class HistogramReducer extends Reducer<Color,IntWritable,Color,LongWritable> {
        @Override
        public void reduce ( Color key, Iterable<IntWritable> values, Context context )
                           throws IOException, InterruptedException {
            /* write your reducer code */
            int sum = 0;
            for (IntWritable v:values) {
                    sum += 1;
            }

            context.write(key, new LongWritable(sum));

        }
    }

    public static void main ( String[] args ) throws Exception {
        /* write your main program code */
            Job job = Job.getInstance();
            job.setJobName("Assignment1-Histogram-mapReduce");
            job.setJarByClass(Histogram.class);

            job.setOutputKeyClass(Color.class);
            job.setOutputValueClass(LongWritable.class);

            job.setMapOutputKeyClass(Color.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setMapperClass(HistogramMapper.class);
            job.setReducerClass(HistogramReducer.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            job.waitForCompletion(true);
   }
}