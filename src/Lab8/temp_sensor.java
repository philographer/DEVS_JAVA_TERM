package Lab8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class temp_sensor extends ViewableAtomic
{
	protected job_msg job;
	protected double processing_time;

	double[] current_temp = {15, 15, 19, 24, 25, 24, 22};
	int count;
	
	public temp_sensor()
	{
		this("temp_sensor");
	}

	public temp_sensor(String name)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = 10;
	}
  
	public void initialize()
	{
		
		job = new job_msg("off", false);
		count = 0;	
		holdIn("off", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		
		if (phaseIs("off"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = (job_msg) x.getValOnPort("in", i); 
					holdIn("on", processing_time);
				}
			}
		}
		else if (phaseIs("on"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = (job_msg) x.getValOnPort("in", i);

					if(job.IsWork == false)
					{
						holdIn("off", INFINITY);
			
					}
				}
			}
		}
	}
  
	public void deltint()
	{
		
		if (phaseIs("on"))
		{	
			holdIn("on", processing_time);
		}
	}

	public message out()
	{

		message m = new message();
		
		if (phaseIs("on")) 
		{
			m.add(makeContent("out", new job_msg("current temp : " + current_temp[count], current_temp[count])));

			count++;
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}
}

