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
			System.out.println("File not found.\n");
		}
	}
	
	public boolean Program()
	{
		
		try
		{
			token = ls.lex();
		}
		catch (StringIndexOutOfBoundsException e)
		{
			return ls.endOfFile();
		}
		if (token != Arrays.asList(ls.keywords).indexOf("procedure"))
		{
			return false;
		}
		token=ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("variable"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf("begin"))
		{
			return false;
		}
		token = ls.lex();
		if (!StatementList())
		{
			return false;
		}
		if (token != Arrays.asList(ls.keywords).indexOf("end"))
		{
			return false;
		}
		token = ls.lex();
		if (token != Arrays.asList(ls.keywords).indexOf(";"))
		{
			return false;
		}
		if (!ls.endOfFile())
		{
			return Program();
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
		return (token == Arrays.asList(ls.keywords).indexOf("variable"));
	}
	public boolean Int()
	{
		return (token == Arrays.asList(ls.keywords).indexOf("integer"));
	}
}
