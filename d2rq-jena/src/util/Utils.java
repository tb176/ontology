package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class Utils {
private static Logger log = LogManager.getLogger(Utils.class.getName());
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 如果为null或者为"" 则返回true
	 */
	public static boolean isNull(String str) {
		if (null2String(str).length() <= 0)
			return true;
		return false;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		if (o == null)
			return true;
		if(o instanceof String){
			if (null2String(o).length() <= 0)
				return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return 如果字符串 不为null 并且不为 "" 返回true 否则返回false
	 */
	public static boolean isNotNull(String str) {
		if (null2String(str).length() <= 0)
			return false;
		return true;
	}

	/**
	 * 判断对象是否不为空
	 * 
	 * @param o
	 * @return 如果为不为null 返回true 否则返回false
	 */
	public static boolean isNotNull(Object o) {
		if (o == null)
			return false;
		return true;
	}

	/**
	 * null2string
	 * 
	 * @param str
	 * @return
	 */
	public static String null2String(String str) {
		if (str == null || str.trim().length() <= 0
				|| str.trim().toLowerCase().equals("null")
				|| str.trim().toLowerCase().equals("nullnull")
				|| str.trim().toLowerCase().equals("nullnullnull")
				|| str.trim().toLowerCase().equals("nullnullnullnull"))
			return "";
		return str;
	}

	/**
	 * null2String
	 * 
	 * @param o
	 * @return
	 */
	public static String null2String(Object o) {
		if (o == null)
			return "";
		return o.toString();
	}

	/**
	 * 返回整数 如果异常 则返回0
	 * @param str
	 * @return
	 */
	public static int string2int(String str){
		if(isNotNull(str)){
			try{
				return Integer.valueOf(str).intValue();
			}catch(Exception ex){
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * 返回整数 如果异常 则返回0
	 * @param str
	 * @return 
	 */
	public static int string2int(Object o){
		String str = null2String(o);
		return string2int(str);
	}
	
	/**
	 * 对字符串进行trim
	 * 
	 * @param str
	 * @return
	 */
	public static String stringTrim(String str) {
		return null2String(str).trim();
	}

	/**
	 * 去掉字符串的\r \n
	 * @param str
	 * @return
	 */
	public static String stringReturn(String str){
		str = null2String(str);
		str = str.replaceAll("\r", " ");
		str = str.replaceAll("\n", " ");
		return str;
	}
	/**
	 * 校验字符串数组是否存在指定的字符 进行了过滤开始和结束的空格
	 * 
	 * @param strs
	 *            字符串数组
	 * @param str
	 *            指定的字符串
	 * @return 如果参数有一个为空返回-1 如果存在 返回下标 否则返回-1
	 * @throws Exception
	 */
	public static int isExistsStr(String[] strs, String str) throws Exception {
		if (Utils.isNull(strs) || Utils.isNull(str))
			return -1;
		for (int i = 0; i < strs.length; i++) {
			if (Utils.isNotNull(strs[i]) && strs[i].trim().equals(str.trim()))
				return i;
		}
		return -1;
	}

	/**
	 * 把对象转换成字符串
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static <T> String toString(T t) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			if (t == null)
				return sb.toString();
			Method[] ms = t.getClass().getMethods();
			Object value;
			String fieldName;
			for (Method m : ms) {
				fieldName = m.getName();
				if (fieldName.indexOf("get") >= 0) {
					fieldName = fieldName.substring(
							fieldName.indexOf("get") + 3, fieldName.length());
					value = m.invoke(t);
					if (value instanceof Date) {
						value = DateUtils.date2String((Date) value,
								"yyyy-MM-dd HH:mm:ss");// 转换成date
					} else if (value instanceof Timestamp) {
						value = DateUtils.date2String(new Date(
								((Timestamp) value).getTime()),
								"yyyy-MM-dd HH:mm:ss");// 转换成date
					}
					sb.append(fieldName).append("=").append(value).append(";");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 文件转换成字节流
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static byte[] file2byte(File f) throws Exception {
		if (f == null || !f.exists())
			return null;
		// 2.输出byte[]到前台
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(Integer.valueOf(
				f.length() + "").intValue());
		int MaxLen = 1024 * 5;
		byte[] buf = new byte[MaxLen];
		int num = -1;
		try {
			while ((num = bis.read(buf, 0, MaxLen)) != -1) {
				baos.write(buf, 0, num);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			bis.close();
			fis.close();
		}
		return baos.toByteArray();
	}

	/**
	 * 用来把字符串数数组转换成字符串，默认取数组的第一个内容
	 * 主要是用来处理页面传递的参数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> stringArry2String(Map<String,String[]> map)throws Exception{
		if(map==null)return null;
		Map<String,String> tmp = new HashMap<String,String>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			if(isNull(entry.getKey()))continue;
			if(isNotNull(entry.getValue())&&entry.getValue().length>0){
				tmp.put(entry.getKey(),entry.getValue()[0]);
			}else{
				tmp.put(entry.getKey(),null);
			}
		}
		return tmp;
	}
	
	/**
	 * 获取id
	 * @return
	 * @throws Exception
	 */
	public static String getUUID()throws Exception{
		UUID id = UUID.randomUUID();
		if(Utils.isNotNull(id))
			return id.toString().replaceAll("-", "");
		return null;
	}
	
	/**
	 * 判断是否是汉字
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

	
	public  static List<Map<String,Object>>   convertMapList(List<Map<String,Object>> lists)
	{
     List<Map<String,Object>> _list=new ArrayList<Map<String,Object>>();
		
	    for (Map<String, Object> map : lists) {
	    	Map<String,Object> _map=new HashMap<String,Object>();
	    	Iterator iter = map.entrySet().iterator();
	    	while (iter.hasNext()) {
	    	Map.Entry entry = (Map.Entry) iter.next();
	    	String key = String.valueOf(entry.getKey());
	    	String val = String.valueOf(entry.getValue());
	    	_map.put(key.toLowerCase(), val);
	    	}
			_list.add(_map);
		}
	    return _list;
	}
	
    /**
     * 获取两个List的不同元素
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> getDiffrent(List<String> list1, List<String> list2) {
        long st = System.currentTimeMillis();
        Map<String,Integer> map = new HashMap<String,Integer>(list1.size()+list2.size());
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if(list2.size()>list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer cc = map.get(string);
            if(cc!=null)
            {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for(Map.Entry<String, Integer> entry:map.entrySet())
        {
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent4 total times "+(System.currentTimeMillis()-st));
        return diff;
        
    }
    
    /**
     * @author TengBei
     * 使用异或运算 --简单的加密解密
     */
	private static final String key0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZacbdefghljklmnopqrstuvwxyz1234567890";
	private static final Charset charset = Charset.forName("UTF-8");
	private static byte[] keyBytes = key0.getBytes(charset);
	
	public static String encode(String enc){
		byte[] b = enc.getBytes(charset);
		for(int i=0,size=b.length;i<size;i++){
			for(byte keyBytes0:keyBytes){
				b[i] = (byte) (b[i]^keyBytes0);
			}
		}
		return new String(b);
	}
	
	public static String decode(String dec){
		byte[] e = dec.getBytes(charset);
		byte[] dee = e;
		for(int i=0,size=e.length;i<size;i++){
			for(byte keyBytes0:keyBytes){
				e[i] = (byte) (dee[i]^keyBytes0);
			}
		}
		return new String(e);
	}
	/**
	 * 将map中不能直接转化为json的类型先转化为String
	 * @return
	 */
    public static List<Map<String,Object>> mapObjectTOString(List<Map<String,Object>> lm){
    	for (Map<String, Object> map : lm) {
    		Object value="";
			Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
			while(entries.hasNext()){
				Map.Entry<String, Object> entry=entries.next();
				value = entry.getValue();
				if(entry.getValue() instanceof java.sql.Date){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					value = sdf.format(value);
					if("0001-01-01".equals(value.toString().trim())){
						value="";
					}
					entry.setValue(value);
				}
			}
		}

    	return lm;
    }
    /**
	 * 用来读取properties文件
	 * 根据key取对应的value值
	 */
	
	public static  String getProperty(String key) throws Exception{
		Properties prop = new Properties();
		InputStream in = 
			new BufferedInputStream(new FileInputStream(new File("doc/data/sparql.properties")));
        prop.load(in);
        in.close();
        return prop.getProperty(key);
	}
	
	
	  //字符串写出到文本
    public static void  write2Txt(String str) throws Exception{
    	FileWriter fw = null;
    	String path = Utils.getProperty("outPath");
  		File f = new File(path);
  		try {
  			if (!f.exists()) {
  				f.createNewFile();
  			}
  			fw = new FileWriter(f);
  			BufferedWriter out = new BufferedWriter(fw);
  			// FileOutputStream fos = new FileOutputStream(f); 
            // OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8"); 
  			out.write(str.toString());
  			out.close();
  			log.debug("====写入文本成功~");
  		} catch (IOException e) {
  			e.printStackTrace();
  		} 
    }
	
    
    // 按照行号读取文本数据 因为数据量偏大这里读取了前100万条数据
    	public static String readByNum(File file) throws Exception {
    		StringBuffer sb = new StringBuffer();
    		FileReader fr = new FileReader(file);
    		LineNumberReader lr = new LineNumberReader(fr);
    		lr.setLineNumber(10);// 从第10行开始读取100行
    		for (int i = 0; i < 100; i++) {
    			log.debug(lr.readLine());// 打印读取的数据
    		}
    		return sb.toString();

    	}
       
       /**
   	 * 一次读取整个文件
   	 */
   	   public static String readToString(File file) {
              Long filelength = file.length();     //获取文件长度
              byte[] filecontent = new byte[filelength.intValue()];
              try {
                  FileInputStream in = new FileInputStream(file);
                  in.read(filecontent);
                  in.close();
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              return new String(filecontent);//返回文件内容,默认编码
      }
    
   	   /**
        * 读取txt文件的内容
        * @param file 想要读取的文件对象
        * 一次读取一行记录
        * 每次读取5m数据放入缓存，读取5000行存入文本，然后继续读取
        * @return 返回文件内容
        * @throws Exception 
        */
       public static String readTxt(File file) throws Exception{
     		
       	StringBuffer sb = new StringBuffer(); 
       	
           try{
           	FileInputStream in = new FileInputStream(file);  
               // 指定读取文件时以UTF-8的格式读取  如果文件超过2G可以改为10M缓存
               BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"),5*1024*1024); //  用5M的缓冲读取文本文件  
             // BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
               String s = null;
               long start = System.currentTimeMillis();
               while((s = br.readLine())!=null){//使用readLine方法，一次读一行
               	sb.append(s);
               }
               long end = System.currentTimeMillis();
               log.debug("====方法[txt2String]耗时："+(end-start)+"毫秒");
               in.close();
               br.close();
           }catch(Exception e){
               e.printStackTrace();
           }
           return sb.toString();
       }
	
	/**
	 * 测试主方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		    System.out.println(getProperty("reverseSQL"));
		    System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
	        System.out.println(Utils.class.getClassLoader().getResource(""));
	        System.out.println(ClassLoader.getSystemResource(""));
	        System.out.println(Utils.class.getResource(""));
	        System.out.println(Utils.class.getResource("/")); // Class文件所在路径
	        System.out.println(new File("/").getAbsolutePath());
	        System.out.println(System.getProperty("verbSql"));
	        
	        String sql = Utils.getProperty("reverseSQL");
	        System.out.println(sql);
	}
}
