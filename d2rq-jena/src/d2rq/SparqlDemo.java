package d2rq;
import java.io.File;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import util.Utils;
/**
 *  model.write(OutputStream) : 也可以用model.write(OutputStream, null) 代替。默认的输出格式。
	model.write(OutputStream, "RDF/XML-ABBREV"): 使用XML 缩略语法输出RDF。
	model.write(OutputStream, "N-TRIPLE"): 输出n 元组的格式。
 */

  
public class SparqlDemo { 
	private  static Logger log = LogManager.getLogger(SparqlDemo.class.getName());
	public static String inputFileName = "doc/data/data.rdf"; // 文本路径
	public static String SPARQL = "doc/data/q1.rq";//sparql语句
  
    public static void main(String args[]) throws Exception {  
    	 Model model = ModelFactory.createDefaultModel();  
    	 String sparql = Utils.readToString(new File(SPARQL));
         // 使用 FileManager 查找文件  
         InputStream in = FileManager.get().open( inputFileName );  
         if (in == null) {  
             throw new IllegalArgumentException(  
                                      "File: " + inputFileName + " not found");  
         }  
   
         // 读取RDF/XML 文件   Model 的read 方法可以读取RDF 输入到model 中
         model.read(in, null);  
         log.debug("--------------------读取的rdf文本内容-----------------");
         
         model.write(System.out,"N-TRIPLE");
    	// String sparql =Utils.getProperty("sql5");
    	log.debug("sparql="+sparql);
    	// String queryString1 = "SELECT ?x  WHERE { ?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  \"John Smith\" } ";
         Query query = QueryFactory.create(sparql) ;
    	 QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    	 try {
    		 ResultSet results = qexec.execSelect() ;
    		 log.debug("----------------sparql查询的内容------------------");
    		 ResultSetFormatter.out(System.out, results, query) ;//格式化输出到控制台
    		 
    	    log.debug("-----------results结果集="+(results.hasNext()==true?"不为空":"空"));
    	 /*   while(results.hasNext())
    	    {
    	      QuerySolution soln = results.nextSolution() ;
    	      RDFNode name = soln.get("x") ;       // Get a result variable by name.
    	      Resource x = soln.getResource("x") ; // Get a result variable - must be a resource
    	      //Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
    	     if(name.isLiteral()) ((Literal)name).getLexicalForm();
    	     if(name.isResource()){
    	    	 Resource r = (Resource) name;
    	    	 if(! r.isAnon()){
    	    		 log.debug(r.getURI());
    	    	 }
    	     }
    	     
    	      log.debug("name="+name+" "+x.toString());
    	     }*/
    	 } finally { qexec.close() ; }
    }  
  
} 
