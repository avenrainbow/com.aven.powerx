package ipmiServer.util;

import ipmiServer.bean.IPMIHost;
import ipmiServer.bean.IPMIHostModel;


public interface AbstractCommand {

	public IPMIHostModel getHostPowerStatus(IPMIHost newIPMIHost,
											IPMIHostModel iPMIHostModel) throws Exception;

	public IPMIHostModel getSDRInfo(IPMIHost newIPMIHost, IPMIHostModel iPMIHostModel)throws Exception;

	public IPMIHostModel getHostInfo(IPMIHost newIPMIHost, IPMIHostModel iPMIHostModel)throws Exception;

	public IPMIHostModel syncHost(IPMIHost iPMIHost)throws Exception;

	public String operationHost(IPMIHost iPMIHost, String operation)throws Exception;

}
