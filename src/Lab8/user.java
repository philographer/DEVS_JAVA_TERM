package Lab8;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	double user_temp;
	int count = 0;
	
	public user() 
	{
		this("user");
	}
  
	public user(String name)
	{
		super(name);
   
		addOutport("out");
	}
  
	public void initialize()
	{
		holdIn("READY", 10);
		user_temp = 24; 
	}
  
	public void deltext(double e, message x)
	{
	}

	public void deltint()
	{
		if(phaseIs("READY"))
		{
			holdIn("SWITCH ON", 10);
		}
	}

	public message out()
		{

		message m = new message();

		if(phaseIs("READY")) 
		{
			m.add(makeContent("out", new job_msg("on", true))); 
		}
		else if(phaseIs("SWITCH ON"))
		{
			m.add(makeContent("out", new job_msg("user temp : " + user_temp, user_temp)));
			holdIn("SET TEMP", 60);
		}
		else if(phaseIs("SET TEMP"))
		{
			m.add(makeContent("out", new job_msg("off", false)));
			holdIn("SWITCH OFF", INFINITY);
		}
		
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText();
    }

}
