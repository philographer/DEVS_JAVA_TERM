package Lab9;
import GenCol.*;

public class loss_msg extends entity
{   
	int loss;
	int who;
	public loss_msg(String name, int _who, int _loss)
	{  
		super(name); 
		loss = _loss;
		who = _who;
	}
	
}
