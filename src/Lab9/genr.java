package Lab9;
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
		holdIn("active", int_arr_time);
		count = 0;
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
			holdIn("active", int_arr_time);
		}
		if (count == 6)
			holdIn("passive",INFINITY);
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", new entity("job" + count)));
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
