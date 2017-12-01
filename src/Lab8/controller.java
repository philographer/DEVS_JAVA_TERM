package Lab8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class controller extends ViewableAtomic
{
  
	protected job_msg userJob, sensorJob;
	protected double processing_time;
	
	
	int count;
	double current_temp;
	double user_temp;
	boolean IsWork;
	
	public controller()
	{
		this("controller", 20);
	}

	public controller(String name, double Processing_time)
	{
		super(name);
    
		addInport("in1");
		addInport("in2");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		
		userJob = new job_msg("off", false);
		sensorJob = new job_msg("off", false);
		
		user_temp = 0;
		current_temp = 0;
		IsWork = false;
		
		holdIn("off", INFINITY); 
	}

	public void deltext(double e, message x)
	{
		
		Continue(e);

		if (phaseIs("off"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in1", i)) 
				{
					userJob = (job_msg) x.getValOnPort("in1", i);
					IsWork = userJob.IsWork; 
					holdIn("wait", processing_time); 
				}
			}
		}
		else 
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in1", i)) 
				{
					userJob = (job_msg) x.getValOnPort("in1", i);
					IsWork = userJob.IsWork; 
					user_temp = userJob.temp;
				}
				if (messageOnPort(x, "in2", i)) 
		
				{
					sensorJob = (job_msg) x.getValOnPort("in2", i);
					current_temp = sensorJob.temp;
				}
			}
		}
	}
  
	public void deltint()
	{
		
		if (phaseIs("wait"))
		{
			holdIn("ready", processing_time);
		}
		else 
		{
			if(current_temp != 0.0 && user_temp != 0.0) 
			{
				if(current_temp > user_temp) 
				{
					holdIn("cooling", processing_time); 
				}
				else if (current_temp < user_temp)
				{
					holdIn("heating", processing_time);
				}
			}			
		}
	}

	public void deltcon(double e, message x)
	{

		deltext(0, x);
		deltint();
	}
	
	public message out()
	{
		
		System.out.println("out" + count);
		count++;
		
		
		message m = new message();

		if (phaseIs("wait")) 
		{
			m.add(makeContent("out", userJob)); 
		
		}
		else
		{
			if(IsWork == false)
			{

				m.add(makeContent("out", userJob));
				holdIn("off", INFINITY);
			}
		}
		
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + userJob.getName()
		+ "\n" + "user temp: " + user_temp
		+ "\n" + "current temp: " + current_temp;
	}
}

