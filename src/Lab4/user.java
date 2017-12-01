package Lab4;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	protected Queue queue; // Queue ����
	// Create Queue 
	
	public user() 
	{
		this("genr", 30);
	}
  
	public user(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
   
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
		queue = new Queue(); // Queue ����
		
		// Queue�� ���� �߰�
		queue.add(2);
		queue.add(6);
		queue.add(4);
		queue.add(4);
		queue.add(7);
		queue.add(1);
		queue.add(8);
		queue.add(5);
		queue.add(2);
		queue.add(1);
		
		holdIn("active", int_arr_time);
		// active : ���꿡 ��� �� ���� ������ �����ϴ� ����
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
					holdIn("finished", INFINITY);
					// accumulator�κ��� ��� ��(������)�� ������ finished ���·� ����
					// finished : User Atomic Model�� Ȱ�� ����
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
			// active ���� ����
			// active : ���꿡 ��� �� ���� ������ �����ϴ� ����
		}
	}

	public message out()
		{
		message m = new message();

		if(queue.size() > 1) // queue size�� 1���� ũ��
		{
			int num = (int)queue.removeFirst(); // queue���� ���� �ϳ��� �����´�
			m.add(makeContent("out", new job(Integer.toString(num), num))); 
			// ������ �ϳ��� ������ out��Ʈ�� ���� accumulator�� ������. 
		}
		else if(queue.size() == 1) // queue size�� 1�̸�
		{
			int num = (int)queue.removeFirst(); //  queue���� ���� �ϳ��� �����´�
			m.add(makeContent("out", new job(Integer.toString(num) + ", last", num, true)));
			// job(String name, int _num, boolean _isLast)
			// Integer.toString : string �� return
			//  flag�� ����Ͽ� ������ �������� �˸���, out��Ʈ�� ���� ������ ������ accumulator�� ������. 
			holdIn("passive", INFINITY);
			// passive ���·� �����Ѵ�
			// passive : ���۵� �����鿡 ���� ���� ����� ��ٸ��� ���� 
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
