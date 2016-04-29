package com.ontology.test;

import com.ontology.service.DemoService;

public class DemoTest {
	public static String RDF = "file/ontology.rdf"; // 文本路径
	public static String SPARQL = "file/ontology.sparql";//sparql语句
		public static void main(String [] args) throws Exception{
			DemoService demo = new DemoService();
			demo.sparqlRDF(RDF, SPARQL);
		}
}
