package Lab5;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic
{
	protected double int_arr_time;
	protected int count;
  
	public client() 
	{
		this("genr", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
   
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;

		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);

		if (phaseIs("sent"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("sent", int_arr_time);
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

		if(phaseIs("active"))
		{
			m.add(makeContent("out", new packet("SYN")));
			holdIn("sent", INFINITY);
		}
		else if(phaseIs("sent"))
		{
			m.add(makeContent("out", new packet("ACK")));
			holdIn("connect", INFINITY);
		}
		
		
		return m;
	}

	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}

}
