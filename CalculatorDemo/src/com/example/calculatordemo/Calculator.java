/*This is a class including a method changing the string to RPN and return the result
 * @aLIEz.TED
 */
package com.example.calculatordemo;
import java.util.*;
public class Calculator {
	private static HashMap<String,Integer> opLs;
	private String src;
	
	//Make HashMap
	public Calculator(String src){
		this.src=src;
		if(opLs==null)
		{
			opLs=new HashMap<String,Integer>(7);
			opLs.put("+", 0);
			opLs.put("-", 0);
			opLs.put("*", 1);
			opLs.put("/", 1);
			opLs.put("%", 1);
			opLs.put(")",2);
			opLs.put("^", 3);
		}
	}
	//Change to RPN
	private String toRpn(){
		String[] tmp=split(src);
		Stack<String> rpn=new Stack<String>();
		Stack<String> tmpSta=new Stack<String>();
		for(String str:tmp){
			if(isNum(str)){
				rpn.push('('+str+')');
			}else{
				if(tmpSta.isEmpty())
				{
					tmpSta.push(str);
				}else{
					if(isHigh(tmpSta.peek(),str))
					{
						if(!str.equals(")"))
						{
							do{
								rpn.push(tmpSta.peek());
								tmpSta.pop();
							}while(!tmpSta.isEmpty()&&(isHigh(tmpSta.peek(),str)));
							
							tmpSta.push(str);
						}else{
							while(!tmpSta.isEmpty()&&!tmpSta.peek().equals("("))
							{
								rpn.push(tmpSta.pop());
							}
							if(!tmpSta.empty()&&(tmpSta.peek().equals("(")))
							{
								tmpSta.pop();
							}
						}
					}else if(!isHigh(tmpSta.peek(),str)){
						tmpSta.push(str);
					}
				}
			}
		}
		while(!tmpSta.empty())
		{
			rpn.push(tmpSta.pop());
		}
		StringBuilder st=new StringBuilder();
		for(String str:rpn){
			st.append(str);
		}
		rpn.clear();
		return st.toString();
	}
	
	private boolean isHigh(String pop, String str) {
		// TODO Auto-generated method stub
		if(str.equals(")"))
		return true;
		if(opLs.get(pop)==null||opLs.get(str)==null)
		return false;
		return opLs.get(pop)>=opLs.get(str);
	}
	private boolean isNum(String str) {
		// TODO Auto-generated method stub
		for(char ch:str.toCharArray())
		{
			if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'||ch=='%'||ch=='^')
				return false;
		}
		return true;
	}
	//Divide the string
	private String[] split(String src) {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder(src.length());
		for(char ch:src.toCharArray())
		{
			if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'||ch=='%'||ch=='^')
			{
				sb.append(",");
				sb.append(ch);
				sb.append(",");
			}else{
				sb.append(ch);
			}
		}
		String string=sb.toString().replaceAll(",{2,}",",");
		return string.split(",");
	}
	
	//Get result
	private double getRes(){
		String rpn=toRpn();
		Stack<Double> res=new Stack<Double>();
		StringBuilder sb=new StringBuilder();
		for(char ch:rpn.toCharArray())
		{
			if(ch=='(')
			{
				continue;
			}else if(ch>='0'&&ch<='9'||ch=='.'){
				sb.append(ch);
			}else if(ch==')'){
				res.push(Double.valueOf(sb.toString()));
				sb=new StringBuilder();
			}
			else
			{
				if(!res.empty())
				{
					Double x=res.pop();
					Double y=res.pop();
					switch(ch){
					case '+':
						res.push(y+x);
						break;
					case '-':
						res.push(y-x);
						break;
					case '*':
						res.push(y*x);
						break;
					case '%':
					case '/':
						if(x!=0)
						{
							double rsd=ch=='%'?y%x:y/x;
							res.push(rsd);
						}else{
							
							res.clear();
							return 0;
						}
						break;
					case '^':
						res.push(Math.pow(y,x));
						break;
					}
				}
			}
			
				
			
		}
		return res.pop();
	}
	//Give the output dealing with Exception
	public String output() throws EmptyStackException,NumberFormatException{
		try{
			return String.valueOf(this.getRes());
		}
		catch(EmptyStackException ex)
		{
			return src;
		}
		catch(NumberFormatException ex)
		{
			return src;
		}
	}
}
