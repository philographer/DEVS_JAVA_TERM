package Lab5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class server extends ViewableAtomic
{
	protected packet job;
	protected double processing_time;

	
	public server()
	{
		this("procQ", 20);
	}

	public server(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		job = new packet("");
		
		holdIn("wait", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("wait"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = (packet) x.getValOnPort("in", i);

					holdIn("wait", processing_time);
				}
			}
		}
		else if(phaseIs("SYN-received"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = (packet) x.getValOnPort("in", i);
					
					holdIn("established", INFINITY);
				}
			}
		}
	}
	
	public void deltint()
	{
	}

	public message out()
	{
		message m = new message();
		
		if (phaseIs("wait"))
		{
			m.add(makeContent("out", new packet("SYN-ACK")));
			
			holdIn("SYN-received", processing_time);
		}
		
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}



