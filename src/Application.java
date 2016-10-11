import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String filename="";
		
		while (!filename.equalsIgnoreCase("end"))
		{	
			System.out.println("Enter your filename to run the program, or type end to exit the program");
			filename = s.nextLine();
			if(!filename.equalsIgnoreCase("end"))
			{
				File f = new File(filename);
				Parser p = new Parser(f);
				try 
				{
					System.out.println(p.Program() + "\n");
				}
				catch (NullPointerException e) { }
				
			}
			else break;
			
		}
	}

}
