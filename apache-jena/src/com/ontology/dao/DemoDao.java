package com.ontology.dao;

import java.io.File;
import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Util.Utils;

public class DemoDao {
private static Logger log = LogManager.getLogger(DemoDao.class.getName());
public static String PREFIX="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
							"PREFIX map: <file:///C:/Program%20Files/d2rq-0.8.1/ontology.rdf#>"+
							"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
							"PREFIX db: <file:///C:/Program%20Files/d2rq-0.8.1/ontology.rdf>"+
							"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
							"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
							"PREFIX vocab: <file:///C:/Program%20Files/d2rq-0.8.1/vocab/>";


	//读取sparql查询rdf文本
	public static void sparqlRDF(String rdfPath,String sparqlPath) throws Exception{
		//读取sparl和RDF文本
		long start = System.currentTimeMillis();
		String sparql = Utils.readTxt(new File(sparqlPath));
		
		InputStream in = FileManager.get().open(rdfPath);
		if(sparql == null || in == null){
			 throw new IllegalArgumentException("读取的rdfPath或sparqlPath内容为空");  
		}
		
		Model model = ModelFactory.createDefaultModel();//创建默认模型
		// 读取RDF/XML 文件   Model 的read 方法可以读取RDF 输入到model 中
		model.read(in, "RDF/XML-ABBREV");
		
		//执行sparql查询
		Query query = QueryFactory.create(sparql) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		 try {
    		 ResultSet results = qexec.execSelect() ;
    		 ResultSetFormatter.out(System.out, results, query) ;//格式化输出到控制台
    		 
		} finally {
			//in.close();
			qexec.close();
		}
		long end = System.currentTimeMillis();
		log.debug("====方法[sparqlRDF]耗时："+(end-start)+"毫秒");

	}
}
