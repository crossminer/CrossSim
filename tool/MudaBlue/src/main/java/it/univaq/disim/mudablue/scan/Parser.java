package it.univaq.disim.mudablue.scan;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

public class Parser {
	
	
	public List<String> GetFieldsVariables(CompilationUnit cu){
		
    	List<String> fieldvariables = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<FieldDeclaration> node_list = cu.getChildNodesByType(FieldDeclaration.class);
        for( FieldDeclaration variable : node_list)
        {
        	try
        	{
        		fieldvariables.add(variable.getVariable(0).getName().toString());
        	}
        	catch(Exception exc)
        	{
        		continue;
        	}
        }
        
		return fieldvariables;
	}
	
	
	public ArrayList<List<String>> GetVariables(CompilationUnit cu){
		
		ArrayList<List<String>> couples = new ArrayList<List<String>>();
		
        @SuppressWarnings("deprecation")
		List<VariableDeclarationExpr> node_list = cu.getChildNodesByType(VariableDeclarationExpr.class);
        for( VariableDeclarationExpr variable : node_list)
        {
	    	try
	    	{
	        	List<String> variables = new ArrayList<String>();
	        	variables.add(variable.getElementType().toString());
	        	variables.add(variable.getVariable(0).getName().toString());
	        	couples.add(variables);
	    	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
        return couples;
	}

	
	public List<String> GetPackages(CompilationUnit cu){
		
    	List<String> packages = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<ImportDeclaration> node_list = cu.getChildNodesByType(ImportDeclaration.class);
        for( ImportDeclaration package1 : node_list)
        {
        	try
        	{
        		packages.add(package1.getNameAsString());
        	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
		return packages;
	}
	
	
	public List<String> GetMethods(CompilationUnit cu){
		
    	List<String> methods = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<MethodDeclaration> node_list = cu.getChildNodesByType(MethodDeclaration.class);
        for( MethodDeclaration method : node_list)
        {
        	try
        	{
				for(MethodCallExpr elem : method.getChildNodesByType(MethodCallExpr.class))
				{
						methods.add(elem.getNameAsString());
				}
        	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
		return methods;
	}

}
