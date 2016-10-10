import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LexScanner {

	final public String[] keywords = {"endif","then","else","if","end","begin","procedure", "integer", "variable",")", "(", "!=","=","/","*","-","+",";",":="};
	private String current;
	private Scanner s;
	private String buffer = "";
	private int location = 0;
	public LexScanner(File f) throws FileNotFoundException {
		current = "";
		s = new Scanner(f);
		s.useDelimiter("");
		while(s.hasNext()){
			buffer=buffer+s.next();
		}
	}
	public int lex(){
		boolean variable = false;
		boolean integer = false;
		current = "";
		char c =buffer.charAt(location);
		if(Character.isWhitespace(c)){
			while(Character.isWhitespace(c)){
				location++;
				c=buffer.charAt(location);
			}
		}
		if(c=='('){
			location++;
			current = ""+c;
		}
		else if(c==')'){
			location++;
			current = ""+c;
		}
		else if(c=='='){
			location++;
			current = ""+c;
		}
		else if(c=='!'){
			location++;
			if(buffer.charAt(location)=='='){
				current = "!=";
				location++;
			}
		}
		else if(c=='/'){
			location++;
			current = ""+c;
		}
		else if(c=='+'){
			location++;
			current = ""+c;
		}
		else if(c=='-'){
			location++;
			current = ""+c;
		}
		else if(c=='*'){
			location++;
			current = ""+c;
		}
		else if(c==';'){
			location++;
			current = ""+c;
		}
		else if(c==':'){
			location++;
			if(buffer.charAt(location)=='='){	
				location++;
				current = ":=";
			}
		}
		else if(current.isEmpty() && Character.isDigit(c)){
			integer = true;
			while(Character.isDigit(c)){
				current = current +c;
				location++;
				c = buffer.charAt(location);
			}
		}
		else if(Character.isAlphabetic(c)){
			
			while(Character.isLetterOrDigit(c)){
				current = current +c;
				int next = location +1;
				//System.out.println(current);
				for(int i =0;i<7;i++){
					
					if(keywords[i].compareTo(current)==0 && !Character.isLetterOrDigit(buffer.charAt(next))){
						location++;
						return i;
					}
				}
				location++;
				c = buffer.charAt(location);
			}
			variable = true;
		}
		if(variable){
			return Arrays.asList(keywords).indexOf("variable");
		}
		if(integer){
			return Arrays.asList(keywords).indexOf("integer");
		}
		return Arrays.asList(keywords).indexOf(current);
		
	}
	public String getCurrentToken() {
		return current;
	}

	public boolean endOfFile() {
		return location == buffer.length();
	}
}
