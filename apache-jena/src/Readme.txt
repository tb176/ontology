ache-jena是关于apache-jena-3.0.0的一个项目，主要提供jena操作ontology文本。
参数需求：1、必须引用apache-jena-3.0.0中lib下的所有jar包.
   		 2、使用log4j日志，引用jar包	log4j-1.2.17.jar、log4j-api-2.0.jar、log4j-core-2.0.jar
		 3、必须使用JDK1.8以及更高版本，否则会A java Exception has Occurred，然后出现这样的错误：
		 Exception in thread "main" java.lang.UnsupportedClassVersionError: org/apache/jena/sparql/core/Prologue : Unsupported major.minor version 52.0。
		 4、Util包是常用的工具类，方便其他类调用。