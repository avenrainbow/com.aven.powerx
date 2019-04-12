package server.util;

import server.bean.IPMIHost;
import server.bean.IPMIHostModel;


public interface AbstractCommand {

	public IPMIHostModel getHostPowerStatus(IPMIHost newIPMIHost,
											IPMIHostModel iPMIHostModel) throws Exception;

	public IPMIHostModel getSDRInfo(IPMIHost newIPMIHost, IPMIHostModel iPMIHostModel)throws Exception;

	public IPMIHostModel getHostInfo(IPMIHost newIPMIHost, IPMIHostModel iPMIHostModel)throws Exception;

	public IPMIHostModel syncHost(IPMIHost iPMIHost)throws Exception;

	public String operationHost(IPMIHost iPMIHost, String operation)throws Exception;

}
