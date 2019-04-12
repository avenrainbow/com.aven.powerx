package server.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.bean.IPMIHost;
import server.bean.IPMIHostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class LocalAbstractCommand implements AbstractCommand{

	private static final Logger logger = LoggerFactory.getLogger(LocalAbstractCommand.class);

	private String resultStr;
	private String errorStr;
	private int resultCode;
	 public void execCmd(String command) throws Exception {
		 Process p = null;
	        StringBuffer strBuff = new StringBuffer();
	        try {
	            BufferedInputStream in = null;
				logger.info("--------------commond start: " + command);
	            p = Runtime.getRuntime().exec(command);
	            
	                in = new BufferedInputStream(p.getInputStream());
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
	            
	            String strLine = "";
	        	logger.info("--------------while start");

	            while ((strLine = inBr.readLine()) != null) {
	                strBuff.append(strLine + "\n");
	            }
	            logger.info("---------------strBuff.toString():"+strBuff.toString());
	            logger.info("--------------while end");
	            setResultStr(strBuff.toString());
	            if (in != null) {
	                in.close();
	            }
	        }
	        catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            throw new Exception(e);

	        }
	        finally {
	            if (p != null) {
	                p.getOutputStream().close();
	            }
	        }
	    }
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	public String getErrorStr() {
		return errorStr;
	}
	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	@Override
	public IPMIHostModel getHostPowerStatus(IPMIHost newIPMIHost,
											IPMIHostModel iPMIHostModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IPMIHostModel getSDRInfo(IPMIHost newIPMIHost,
			IPMIHostModel iPMIHostModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IPMIHostModel getHostInfo(IPMIHost newIPMIHost,
			IPMIHostModel iPMIHostModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IPMIHostModel syncHost(IPMIHost iPMIHost) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String operationHost(IPMIHost iPMIHost, String operation)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
