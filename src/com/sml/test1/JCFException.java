package com.sml.test1;

/**
 * JCF系统异常的基础类型。
 * JCF系统异常处理的基本原则：
 * 1：保证编程时仍然符合java的异常处理习惯：通过不同的异常的class类型，而非errorCode来区分异常类型
 * 2：errorCode的值统一规划。
 * 
 * 为了符合上述原则，需要遵守以下编程习惯
 * 1）定义不同的java class来标示不同的异常。
 * 2）不同的异常java类需要继承JCFException
 * 3）在异常java类的实现内部对JCFException errorCode进行赋值。
 * 4）应用逻辑处理过程中抛出异常的时候仍然遵守java程序员习惯的做法，直接创建特定类型的java异常，不需要指定errorCode
 * 
 * 示例
 * 1) how to implements a special exception
 * class TimeoutException extends JCFException{
 * public TimeoutException(String message){
 * 		super(ERROR_TIMEOUT, message);
 * }
 * 
 * }
 * 
 * 2)how to throw a special exception
 * ....
 * throw new TimeoutException("invoke service("+service.name+") time out");
 * ....
 * 
 * @author xuhb
 *
 */
public class JCFException extends Exception {
	private static final long serialVersionUID = -1752713618418806192L;
	private int errorCode;
	
	protected JCFException(int errorCode){
		this.setErrorCode(errorCode);
	}
    protected JCFException(int errorCode, String message) {
    	super(message);
		this.setErrorCode(errorCode);
    }

    protected JCFException(int errorCode, String message, Throwable cause) {
        super(message, cause);
		this.setErrorCode(errorCode);
    }

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage(){
		return "ErrorCode={" + this.errorCode + "} " + super.getMessage();
	}
}
