import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Parser {
	LexScanner ls;
	int token;
	public Parser(File filename)
	{
		try {
			ls = new LexScanner(filename);
		} catch (FileNotFoundException e) 
		{
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	
	public boolean Program()
	{
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("procedure"))
		{
			System.out.println("You are missing procedure");
			return false;
		}
		token=ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("variable"))
		{
			System.out.println("You are missing variable");
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("begin"))
		{
			System.out.println("You are missing begin");
			return false;
		}
		token = ls.lex();
		if (!StatementList())
		{
			return false;
		}
		if (token != Arrays.asList(ls.keywords).indexOf("end"))
		{
			System.out.println("You are missing end");
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf(";"))
		{
			System.out.println("You are missing ;");
			return false;
		}
		return true;
	}
	
	public boolean StatementList()
	{
		if (!Statement())
		{
			return false;
		}
		if (token != Arrays.asList(ls.keywords).indexOf(";"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("end") 
				&& token != Arrays.asList(ls.keywords).indexOf("endif")
				&& token != Arrays.asList(ls.keywords).indexOf("else"))
		{
			if (!StatementList())
			{
				return false;
			}
		}
		return true;
	}
	public boolean Statement()
	{
		if (!If() && !Assign())
		{
			return false;
		}
		return true;
	}
	
	public boolean Assign()
	{
		if (!Var())
		{	
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf(":="))
		{
			return false;
		}
		token = ls.lex();
		if (!Expr())
		{
			System.out.println("You are missing expr");
			return false;
		}
		return true;
	}
	
	public boolean If()
	{
		if (token != Arrays.asList(ls.keywords).indexOf("if"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("("))
		{
			return false;
		}
		token = ls.lex();
		if (!Bool())
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf(")"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("then"))
		{
			return false;
		}
		token = ls.lex();
		if (!StatementList())
		{
			return false;
		}
		// there can be more else if's here
		while (token == Arrays.asList(ls.keywords).indexOf("elseif"))
		{
			token = ls.lex();
			if (!StatementList());
			{
				return false;
			}
		}
		if (token == Arrays.asList(ls.keywords).indexOf("else"))
		{
			token = ls.lex();
			if (!StatementList())
			{
				return false;
			}
		}
		if (token != Arrays.asList(ls.keywords).indexOf("endif"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf(";"))
		{
			return false;
		}
		return true;
	}
	public boolean Expr()
	{
		if (!Term())
		{
			System.out.println("You are missing term");
			return false;
		}
		token = ls.lex();
		while (token == Arrays.asList(ls.keywords).indexOf("+")
				|| token == Arrays.asList(ls.keywords).indexOf("-")
				|| token == Arrays.asList(ls.keywords).indexOf("*")
				|| token == Arrays.asList(ls.keywords).indexOf("/"))
		{
			token = ls.lex();
			if (!Term())
			{
				return false;
			}
			token = ls.lex();
		}
		return true;
	}
	public boolean Term()
	{
		if (!(Var() || Int()))
		{
			System.out.println("You are missing term "+Int());
			return false;
		}
		return true;
	}
	public boolean Bool()
	{
		if (!Var())
		{
			return false;
		}
		token = ls.lex();
		if (!(token == Arrays.asList(ls.keywords).indexOf("=") || token == Arrays.asList(ls.keywords).indexOf("!=")))
		{
			return false;
		}
		token = ls.lex();
		return true;
	}
	public boolean Var()
	{
		//System.out.println("You are missing var");
		return (token == Arrays.asList(ls.keywords).indexOf("variable"));
	}
	public boolean Int()
	{
		return (token == Arrays.asList(ls.keywords).indexOf("integer"));
	}
}
