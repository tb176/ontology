package d2rq.interfaces;

public interface D2RQInterface {
	// 根据虚拟映射文件查询所有的类
	public String getAllCls() throws Exception;

	// 第二个方法，根据客户号查询关联信息
	public String getMsgByCustno(String custno) throws Exception;

}
