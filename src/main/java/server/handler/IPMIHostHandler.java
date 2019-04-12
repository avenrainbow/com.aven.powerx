package server.handler;


import server.bean.IPMIHost;
import server.bean.dao.IPMIHostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IPMIHostHandler {

    @Autowired
    private IPMIHostRepository ipmiHostDao;

    public void queryIPMIHost() {

        List<IPMIHost> ipmiHostList = ipmiHostDao.findAll();

    }

    public IPMIHost  addIPMIHost(IPMIHost ipmiHost){
        return ipmiHostDao.save(ipmiHost);
    }
}
