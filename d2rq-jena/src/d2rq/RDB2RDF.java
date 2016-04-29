package d2rq;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC;

import de.fuberlin.wiwiss.d2rq.jena.GraphD2RQ;
import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;
import de.fuberlin.wiwiss.d2rq.parser.MapParser;
import util.Utils;

public class RDB2RDF {
	private static Logger log = LogManager.getLogger(RDB2RDF.class.getName());
	
	private static final String D2RQ_DIR = "C:\\Program Files\\d2rq-0.8.1";//服务目录
	private static final String USER = "root";//用户名
	private static final String PASS = "123456";//密码
	private static final String DRIVER = "com.mysql.jdbc.Driver";//mysql数据库驱动
	private static final String LOCAL_CONNECT = "jdbc:mysql://localhost/ontology";//数据库链接的url
	//private static final String SERVER_CONNECT = "jdbc:mysql://192.168.0.176/ontology";//服务器数据库链接的url
	private static final String OUTPUT = "ontology.ttl";//映射的虚拟rdf文本
	private static final String FORMAT = "RDF/XML-ABBREV";//格式化文本的格式
	private static final String OUT_RDF = "academic-dump.rdf";//输出的RDF文件名称
	private static final String SPARQL_FILE = "doc/sparql/ontology.rq";//查询的sparql内容
	
	public static Process process = null;
	private static final String PREFIX ="PREFIX db: <>"+
										"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
										"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
										"PREFIX map: <>"+
										"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
										"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
										"PREFIX vocab: <vocab/>"+
										"PREFIX jdbc: <http://d2rq.org/terms/jdbc/>";

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//generateMapping2RDF();
		//GenerateMapping();//映射生成.ttl文本
		//startServer();
		executeSparql();
	}

	public RDB2RDF() {
		
	}
	/**
	 * 启动D2R server
	 * @throws IOException 
	 */
	public static void startServer() throws IOException{
		log.debug("--------load start----------");
		long start = System.currentTimeMillis();
		// 启动服务 ，执行下面命令d2r-server Academic.n3
		File dir = new File(D2RQ_DIR);//打开服务所在目录 因为命令必须在服务目录下执行

		String cmd = RDB2RDF.D2RQ_DIR + "//d2r-server.bat "+OUTPUT;

		process = Runtime.getRuntime().exec(cmd, null, dir);
		log.debug("--------load end----------");
		long end = System.currentTimeMillis();
		log.debug("----d2r server start  success takes :"+(end-start)+"ms");
	}

	/**
	 *  自动生成虚拟的RDB-RDF映射文件
	 * @throws IOException
	 */
	public static void GenerateMapping() throws IOException {
		log.debug("--------load start----------");
		long start = System.currentTimeMillis();
		// 命令格式：d2rq.generate_mapping -u u -p p -d d -o o connecturl
		File dir = new File(D2RQ_DIR);//打开服务所在目录 因为命令必须在服务目录下执行

		String cmd = RDB2RDF.D2RQ_DIR + "//generate-mapping.bat -u " + RDB2RDF.USER
				+ " -p " + PASS + " -d " + RDB2RDF.DRIVER + " -o " + RDB2RDF.OUTPUT + " "
				+ RDB2RDF.LOCAL_CONNECT;

		process = Runtime.getRuntime().exec(cmd, null, dir);
		log.debug("--------load end----------");
		long end = System.currentTimeMillis();
		log.debug("----generate .ttl txt success takes :"+(end-start)+"ms");
	}
	
	/**
	 * 通过dump命令生成RDF文件
	 * dump-rdf -u root -p 123456 -f RDF/XML-ABBREV -o academic-dump.rdf jdbc:mysql://localhost/Academic 
	 */
	public static void generateMapping2RDF() throws  Exception{
		log.debug("--------load start----------");
		long start = System.currentTimeMillis();
		File dir = new File(D2RQ_DIR);
		String cmd = RDB2RDF.D2RQ_DIR + "//dump-rdf.bat -u " + RDB2RDF.USER
		+ " -p " + RDB2RDF.PASS + " -f " + RDB2RDF.FORMAT + " -o " + OUT_RDF + " "
		+ RDB2RDF.LOCAL_CONNECT;
		process = Runtime.getRuntime().exec(cmd, null, dir);
		log.debug("--------load end----------");
		long end = System.currentTimeMillis();
		log.debug("---generate RDF success takes :"+(end-start)+"ms");
		
	}
	
	
	/**
	 * 根据生成的虚拟RDF文件mapping.ttl进行查询
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private static Model executeSparql() throws Exception {
		log.debug("--------------------select start-------------");
		ModelD2RQ d2rqModel = new ModelD2RQ("file:doc/ttl/ontology.ttl");

		String sparql = PREFIX + " SELECT DISTINCT ?class WHERE { [] a ?class } ORDER BY ?class ";

		String sparql1 = Utils.readTxt(new File(SPARQL_FILE));
		log.debug("-------------------" + sparql1);
		Query q = QueryFactory.create(sparql1);
		ResultSet rs = QueryExecutionFactory.create(q, d2rqModel).execSelect();
		log.debug("----------------sparql查询的内容------------------");
		ResultSetFormatter.out(System.out, rs, q);// 格式化输出到控制台
		String result = ResultSetFormatter.asXMLString(rs);
		log.debug(result);
		return d2rqModel;
	}
	
	



}
