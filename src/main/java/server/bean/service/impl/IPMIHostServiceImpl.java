package server.bean.service.impl;

import server.bean.IPMIHost;
import server.bean.IPMIHostModel;
import server.bean.dao.IPMIHostRepository;
import server.bean.service.IPMIHostService;
import server.util.IpmiToolsUtil;
import server.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IPMIHostServiceImpl implements IPMIHostService {

    @Autowired
    public IPMIHostRepository ipmiHostRepository;

    @Override
    public List<IPMIHost> findAllByParams(IPMIHost params) {
        Specification<IPMIHost> specification = (Specification<IPMIHost>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!StringUtil.isNull(params.getIpmiIp())) {
                list.add(cb.like(root.get("ipmiIp").as(String.class), "%" + params.getIpmiIp() + "%"));
            }

            return cb.and(list.toArray(new Predicate[list.size()]));
        };

        List<IPMIHost> ipmiHosts = ipmiHostRepository.findAll(specification);


        return ipmiHosts;
    }

    @Override
    public IPMIHost add(IPMIHost ipmiHost) {
        IPMIHost newIpmiHost = ipmiHostRepository.save(ipmiHost);
        return newIpmiHost;
    }

    @Override
    public void remove(Long hostId) {
        ipmiHostRepository.deleteById(hostId);
    }

    @Override
    public IPMIHost findById(Long hostId) {
        return ipmiHostRepository.getOne(hostId);
    }

    @Override
    public IPMIHost update(IPMIHost ipmiHost) {
        return ipmiHostRepository.save(ipmiHost);
    }

    @Override
    public IPMIHost findByIdSync(Long hostId) throws Exception {
        IPMIHost ipmiHost = ipmiHostRepository.getOne(hostId);

        IpmiToolsUtil ipmiToolsUtil = new IpmiToolsUtil();

        IPMIHostModel ipmiHostModel = ipmiToolsUtil.syncHost(ipmiHost);

        ipmiHost.setHostState(ipmiHostModel.getHostState());

        ipmiHost.setHostManufacturer(ipmiHostModel.getHostManufacturer());

        ipmiHost.setHostSNId(ipmiHostModel.getHostSNId());

        ipmiHost.setSensorReadings(ipmiHostModel.getSensorReadings());

        return ipmiHost;
    }

}
