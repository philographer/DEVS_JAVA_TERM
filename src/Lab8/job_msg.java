package Lab8;
import GenCol.*;

public class job_msg extends entity
{   
	boolean IsWork;
	double temp;
	
	public job_msg(String name, boolean _work)
	{  
		super(name);
		
		IsWork = _work;
		temp = 0;
	}

	public job_msg(String name, double _temp)
	{  
		super(name);  

		IsWork = true;
		temp = _temp;
	}
}
