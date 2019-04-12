package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.vcenter.DatacenterTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import server.handler.VMwareDatacenterHandler;

/**
 *  vSphere datacenter操作接口
 */
@RestController
@RequestMapping(value = "/vmware/datacenters")
public class VMwareDatacenterApiController {


    @Autowired
    private VMwareDatacenterHandler vMwareDatacenterHandler = new VMwareDatacenterHandler();

    @GetMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String info(@PathVariable("id") String id) throws Exception {

        DatacenterTypes.Info datacenterInfo = vMwareDatacenterHandler.getDatacenterById(id);

        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(datacenterInfo);
        return mapJakcson;
    }

}
