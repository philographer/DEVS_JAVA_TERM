package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class processor extends ViewableAtomic
{
	
	protected Queue q; 
	protected entity job;
	protected double processing_time;
	
	int size; 
	int loss; 
	int who; 
	double temp_time; 
	
	int num;
	
	public processor()
	{
		this("processor", 20, 3);
	}

	public processor(String name, double Processing_time, int _size)
	{
		super(name);
    
		addInport("in");
		addOutport("out1"); 
		addOutport("out2"); 
		
		who = Integer.parseInt(name.substring(9));
		size = _size; 
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{

		num = 1;
		q = new Queue();
		loss = 0; 
		holdIn("passive", INFINITY);
		job = new entity("");

	}

	public void deltext(double e, message x)
	{
		
		Continue(e);

		if (phaseIs("passive")) 
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i)) 
				{
					job = x.getValOnPort("in", i);
					holdIn("busy", processing_time); 
				
				}
			}
		}
		else if (phaseIs("busy")) 
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i)) 
				{
					if(q.size() < size)
					{
						entity job = x.getValOnPort("in", i);
						q.add(job);
					}
					else
					{
						loss++;
						temp_time = sigma; 
						holdIn("loss", 0); 

						
					}
				}
			}
		}
	}
  
/*	public void deltcon(double e, message x) // ���� �߻��� ���� �Լ�
	{
		deltint();
		deltext(0, x);
	}	*/
	
	public void deltint()
	{  

		if(phaseIs("loss"))
		{
			holdIn("busy", temp_time); 
		}
		else 
		{
			
			if (!q.isEmpty()) 
			{
				job = (entity) q.removeFirst(); 
				holdIn("busy", processing_time); 

			}
			else 
			{	
				holdIn("passive", INFINITY);
	
			}
		}
	}

	public message out()
	{
		message m = new message();
		
		if (phaseIs("busy"))
		{
			m.add(makeContent("out1", job)); 
			System.out.println("job : " + job + "phase :" + phase);

			
		}
		
		if (phaseIs("loss"))
		{
			m.add(makeContent("out2", new loss_msg(who + ": " + loss, who, loss)));

		}
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + "queue length: " + q.size()
        + "\n" + "queue itself: " + q.toString();
	}

}



