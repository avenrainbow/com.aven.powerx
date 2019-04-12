package server.bean.service;

import server.bean.IPMIHost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface  IPMIHostService {

    List<IPMIHost> findAllByParams(IPMIHost params);

    IPMIHost add(IPMIHost ipmiHost);

    void remove(Long hostId);

    IPMIHost findById(Long hostId);

    IPMIHost update(IPMIHost ipmiHost);

    IPMIHost findByIdSync(Long hostId) throws Exception;
}
