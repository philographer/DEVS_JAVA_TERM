package Lab4;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class accumulator extends ViewableAtomic
{
	
	protected Queue q;
	protected job job;
	protected double processing_time;
	
	protected int result;
	
	public accumulator()
	{
		this("procQ", 20);
	}

	public accumulator(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		q = new Queue();
		job = new job("", 0, false);
		// job(String name, int _num, boolean _isLast)
		result = 0;
		// �������� �ʱⰪ�� 0
		
		holdIn("passive", INFINITY);
		// passive : ���꿡 ��� �� ���� ������ ���� �޴� ����
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
					job = (job) x.getValOnPort("in", i);
					
					q.add(job);
				
					holdIn("passive", processing_time);
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("passive"))
		{
			//job = (job) q.getLast();
			//System.out.println(job.isLast);
			
			if (job.isLast) // ������ �����̸� (��, job.isLast = true)
			{
				for(int i=0; i<10; i++) // 10���� ����
				{
					job = (job) q.removeFirst(); // queue�� �ִ� ������ �ϳ� ����
					System.out.println("job.num=?"+job.num);
					result = result + job.num; // �������� ���Ѵ�
					
					holdIn("processing", processing_time);
					// processing : ������ �޾� ó���ϴ� ����
				}
			}
		}
	}

	public message out()
	{
		message m = new message();
		
		if (phaseIs("processing"))
		{
			m.add(makeContent("out", new job(Integer.toString(result), result)));

			holdIn("finished", INFINITY);

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



