package ipmiServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipmiServer.bean.IPMIHost;
import ipmiServer.bean.IPMIHostModel;
import ipmiServer.bean.service.IPMIHostService;
import ipmiServer.util.IpmiToolsUtil;
import ipmiServer.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ipmi")
public class IPMIHostApiontroller {

    @Resource
    public IPMIHostService ipmiHostService;



    /**
     * 添加IPMI主机
     *
     * @param ipmiHost
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody IPMIHost ipmiHost) throws JsonProcessingException {
        if (!StringUtil.isNull(ipmiHost)) {
            IPMIHost newIPMIHost = ipmiHostService.add(ipmiHost);
            ObjectMapper mapper = new ObjectMapper();
            String mapJakcson = mapper.writeValueAsString(newIPMIHost);
            return mapJakcson;
        } else {
            return "error";
        }
    }

    /**
     * 获取IPMI主机列表
     *
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String list(@RequestParam(value="ipmiIp",required=false) String ipmiIp) throws Exception {
        IPMIHost ipmiHost = new IPMIHost();
        if(!StringUtil.isNull(ipmiIp)){
            ipmiHost.setIpmiIp(ipmiIp);
        }
        List<IPMIHost> ipmiHostList = ipmiHostService.findAllByParams(ipmiHost);
        Map<String, Object> result = new HashMap<>();
        result.put("hosts", ipmiHostList);
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(result);
        return mapJakcson;
    }

    /**
     * 移除IPMI主机
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") String id) throws JsonProcessingException {
        Long hostId = Long.parseLong(id);
        ipmiHostService.remove(hostId);
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(result);
        return mapJakcson;
    }

    /**
     * 修改IPMI主机连接信息
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@PathVariable("id") String id, @RequestBody IPMIHost ipmiHost) throws JsonProcessingException {
        Long hostId = Long.parseLong(id);
        IPMIHost host = ipmiHostService.findById(hostId);
        host.setHostName(ipmiHost.getHostName());
        host.setIpmiIp(ipmiHost.getIpmiIp());
        host.setIpmiName(ipmiHost.getIpmiName());
        host.setIpmiPassword(ipmiHost.getIpmiPassword());
        IPMIHost newIPMIHost = ipmiHostService.update(host);

        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(newIPMIHost);
        return mapJakcson;
    }

    /**
     * 同步获取单台主机的IPMI信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String info(@PathVariable("id") String id) throws Exception {
        IPMIHost ipmiHost = ipmiHostService.findByIdSync(Long.parseLong(id));
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(ipmiHost);
        return mapJakcson;
    }


}
