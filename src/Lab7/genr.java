package Lab7;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	public genr() 
	{
		this("genr", 30);
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 0;
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			
			if(count == 5)
			{
				holdIn("stop", INFINITY);
			}
		}
	}

	public message out()
	{
		message m = new message();
		
		if(count < 5)
		{
			m.add(makeContent("out", new entity("job" + count)));
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
