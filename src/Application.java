import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String filename="";
		
		while (filename.compareToIgnoreCase("end")!=0)
		{
			System.out.println("Enter your filename to run the program, or type end to exit the program");
			filename = s.nextLine();
			if(filename.compareToIgnoreCase("end")!=0)
			{
				File f = new File(filename);
				Parser p = new Parser(f);
				System.out.println(p.Program());
			}
		}
	}

}
