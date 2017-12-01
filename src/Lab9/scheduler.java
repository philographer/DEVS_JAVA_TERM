package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class scheduler extends ViewableAtomic
{
  
	protected entity job;
	protected double scheduling_time;
	int node;
	int way;
	
	public scheduler()
	{
		this("scheduler", 0, 5);
	}

	public scheduler(String name, double Scheduling_time, int _node)
	{
		super(name);
    
		node = _node;
		
		addInport("in");
		
		for(int i = 1; i <= node; i++)
		{
			addOutport("out" + i);
		}
		scheduling_time = Scheduling_time;
	}
  
	public void initialize()
	{
		way = 1; 
		holdIn("passive", INFINITY);
		job = new entity("");
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = x.getValOnPort("in", i);
					holdIn("busy", scheduling_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		way++;
		
		if(way > node)
		{
			way = 1;
		}
			
		holdIn("passive", INFINITY);
		job = new entity("");
	}


	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out" + way, job));
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

