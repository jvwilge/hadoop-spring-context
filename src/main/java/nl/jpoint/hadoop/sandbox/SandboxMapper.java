package nl.jpoint.hadoop.sandbox;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import javax.inject.Inject;
import java.io.IOException;

public class SandboxMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Inject
	private HelloWorldBean helloWorldBean;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		LazySpringContext.autowireBean(context.getConfiguration(), this);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println("Hello " + helloWorldBean.getName() + ". Read [" + value.toString() + "]");
	}

}
