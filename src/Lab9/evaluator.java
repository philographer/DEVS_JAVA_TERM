package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class evaluator extends ViewableAtomic
{
  
	protected entity job;

	int arrive;
	int solved; 
	int loss; 

	public evaluator()
	{
		this("evaluation");
	}

	public evaluator(String name)
	{
		super(name);
    
		addOutport("out");
		addInport("arrive");
		addInport("solved");
		addInport("loss");
		
	}
  
	public void initialize()
	{
		arrive = 0;
		solved = 0;
		loss = 0;
		holdIn("active",10);
	}

	public void deltext(double e, message x)
	{


		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "arrive", i))
				{
					arrive++;
				}
				else if (messageOnPort(x, "solved", i))
				{
					solved++;
				}
				else if (messageOnPort(x, "loss", i))
				{
					loss++;
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
		return m;
	}

	public String getTooltipText()
	{
		return
	 super.getTooltipText()
	 + "\n" + " tatal: " + arrive
	 + "\n" + " solved: " + solved
	 + "\n" + " loss: " + loss;
	}

}

