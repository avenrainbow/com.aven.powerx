package server.handler;

import com.vmware.vcenter.Datacenter;
import com.vmware.vcenter.DatacenterTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import server.bean.Platform;
import server.bean.dao.PlatformRepository;
import server.hypervisor.vmware.VapiClientFactory;


@Configuration
public class VMwareDatacenterHandler {

    @Autowired
    public PlatformRepository platformService;

    public DatacenterTypes.Info getDatacenterById(String datacenterId) throws Exception {

        Platform platform = platformService.getOne(1l);
        Datacenter datacenterService = VapiClientFactory.getDatacenterService(platform);
        DatacenterTypes.Info datacenterInfo = datacenterService.get(datacenterId);
        System.out.println(datacenterInfo.getHostFolder());
        System.out.println(datacenterInfo.getVmFolder());
        System.out.println(datacenterInfo.getDatastoreFolder());
        return datacenterInfo;

    }
}
