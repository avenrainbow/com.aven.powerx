package server.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;

import java.util.Map;

import server.bean.IPMIHost;
import server.bean.IPMIHostModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * ipmi物理主机管理
 * 
 * @author zhengyi
 * @date 2017.4.10
 */
public class IpmiToolsUtil extends LocalAbstractCommand {

    private static final Logger logger = LoggerFactory.getLogger(IpmiToolsUtil.class);


    /**
     * 获取主机电源状态
     * 
     * @return
     * @throws Exception
     */
    public IPMIHostModel getHostPowerStatus(IPMIHost ipmiHost, IPMIHostModel iPMIHostModel) throws Exception {
        String cmdLine = "ipmitool -I lanplus" + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
            + ipmiHost.getIpmiPassword() + " power status";
        execCmd(cmdLine);
        String resultStr = getResultStr();

        if (resultStr.indexOf("Error") > -1 && resultStr.indexOf("session") > -1) {
            logger.info("---------------------resultStr:" + resultStr);
            throw new Exception("物理主机[" + ipmiHost.getIpmiIp() + "]连接异常，无法添加。");
        }
        else if (resultStr.indexOf("Chassis Power") == -1) {
            logger.info("-------------------resultStr:" + resultStr);
            throw new Exception("物理主机[" + ipmiHost.getIpmiIp() + "]连接异常，无法添加。");
        }
        iPMIHostModel = new IPMIHostModel();
        if (!StringUtils.isEmpty(resultStr)) {
            String[] split = resultStr.split("is");
            String hostState = split[2].trim();
            if ("on".equals(hostState)) {
                iPMIHostModel.setHostState("OK");
            }
            else if ("off".equals(hostState)) {
                iPMIHostModel.setHostState("STOPPED");
            }
        }
        return iPMIHostModel;
    }

    /**
     * 获取主机传感器信息
     * 
     * @return
     * @throws Exception
     */
    public IPMIHostModel getSDRInfo(IPMIHost ipmiHost, IPMIHostModel iPMIHostModel) throws Exception {
        String cmdLine = "ipmitool -I lanplus" + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
            + ipmiHost.getIpmiPassword() + " sensor";
        execCmd(cmdLine);
        String resultStr = getResultStr();
        String[] split = resultStr.split("\n");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (int j = 0; j < split.length; j++) {
            if (StringUtils.isEmpty(split[j])) {
                continue;
            }
            String[] splitStrs = split[j].split("\\|");
            if (splitStrs.length == 10 && !(splitStrs[1].trim().indexOf("0x0") > -1) && !(splitStrs[1].trim().indexOf("na") > -1)) {
                resultMap.put(splitStrs[0].trim(), splitStrs[1].trim() + " " + splitStrs[2].trim());
            }
        }
        iPMIHostModel.setSensorReadings(resultMap);
        return iPMIHostModel;
    }

    /**
     * 获取主机基础信息：厂商，序列号
     * 
     * @return
     * @throws Exception
     */
    public IPMIHostModel getHostInfo(IPMIHost ipmiHost, IPMIHostModel iPMIHostModel) throws Exception {
        String cmdLine = "ipmitool -I lanplus" + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
            + ipmiHost.getIpmiPassword() + " fru";
        execCmd(cmdLine);
        String resultStr = getResultStr();
        String[] split = resultStr.split("\n");
        Map<String, String> resultMap = new HashMap<String, String>();
        for (int j = 0; j < split.length; j++) {
            if (StringUtils.isEmpty(split[j])) {
                continue;
            }
            String[] split2 = split[j].split(":");
            if (split2.length > 1) {
                resultMap.put(split2[0].trim(), split2[1].trim());
            }
        }
        String hostManufacturer = resultMap.get("Product Manufacturer");
        iPMIHostModel.setHostManufacturer(hostManufacturer);
        String hostSNId = resultMap.get("Product Serial");
        iPMIHostModel.setHostSNId(hostSNId);
        return iPMIHostModel;
    }

    /**
     * 物理主机电源操作：开机|关机|重启
     * 
     * @return
     * @throws Exception
     */
    public String operationHost(IPMIHost ipmiHost, String operation) throws Exception {
        String cmdLine = "ipmitool -I lanplus" + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
            + ipmiHost.getIpmiPassword() + " power " + operation;
        if ("powerOn".equals(operation)) {
            cmdLine = "ipmitool -I lanplus " + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
                + ipmiHost.getIpmiPassword() + " power on";

        }
        else if ("powerOff".equals(operation)) {
            cmdLine = "ipmitool -I lanplus " + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
                + ipmiHost.getIpmiPassword() + " power off";

        }
        else if ("restart".equals(operation)) {
            cmdLine = "ipmitool -I lanplus" + " -H " + ipmiHost.getIpmiIp() + " -U " + ipmiHost.getIpmiName() + " -P "
                + ipmiHost.getIpmiPassword() + " power reset";

        }
        execCmd(cmdLine);
        String resultStr = getResultStr();
        return resultStr;
    }

    // =============================业务 start=========================
  /**
     * 同步IPMI物理主机信息
     * 
     * @param ipmiHost
     * @return
     * @throws Exception
     */
    public IPMIHostModel syncHost(IPMIHost ipmiHost) throws Exception {
        IPMIHostModel iPMIHostModel = new IPMIHostModel();
        // 获取服务器电源状态
        iPMIHostModel = getHostPowerStatus(ipmiHost, iPMIHostModel);
        // 获取服务器传感器信息
        iPMIHostModel = getSDRInfo(ipmiHost, iPMIHostModel);
        // 获取服务器基础信息
        iPMIHostModel = getHostInfo(ipmiHost, iPMIHostModel);

        return iPMIHostModel;
    }
    /*
    private QueryResult<IPMIHost> checkIsDeleted(IPMIHost ipmiHost) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ipmiIp", ipmiHost.getIpmiIp());
        ipmiHostRepository.findAll()
        IPMIHostDaoService iPMIHostDaoService = WceDaoServiceHelper.getIPMIHostDaoService();
        QueryResult<IPMIHost> result = iPMIHostDaoService.findByExample(params, true);
        return result;
    }

    private void syncIPMIHostInfo(IPMIHost ipmiHost, IPMIHostModel iPMIHostModel) {
        ipmiHost.setHostState(iPMIHostModel.getHostState());
        ipmiHost.setHostManufacturer(iPMIHostModel.getHostManufacturer());
        ipmiHost.setHostSNId(iPMIHostModel.getHostSNId());
        ipmiHostRepository(ipmiHost);
    }

    private void syncIPMIHostSDR(IPMIHost ipmiHost, IPMIHostModel iPMIHostModel) {
        IPMIHostSDRDaoService iPMIHostSDRDaoService = WceDaoServiceHelper.getIPMIHostSDRDaoService();

        Map<String, Object> sensorReadings = iPMIHostModel.getSensorReadings();
        List<IPMIHostSDR> dbList = getSDRListFromDB(ipmiHost);
        List<IPMIHostSDR> delList = new ArrayList<IPMIHostSDR>();
        List<IPMIHostSDR> updateList = new ArrayList<IPMIHostSDR>();
        for (int i = 0; i < dbList.size(); i++) {
            boolean isDel = true;
            for (Map.Entry<String, Object> entry : sensorReadings.entrySet()) {
                if (StringUtil.isNotNull(entry.getKey()) && StringUtil.equals(dbList.get(i).getSdrKey(), entry.getKey())) {
                    IPMIHostSDR iPMIHostSDR = dbList.get(i);
                    iPMIHostSDR.setSdrValue(entry.getValue().toString());
                    updateList.add(iPMIHostSDR);
                    sensorReadings.remove(entry.getKey());
                    isDel = false;
                    break;
                }
            }
            if (isDel) {
                delList.add(dbList.get(i));
            }
        }
        // 更新已有数据
        for (int updateNum = 0; updateNum < updateList.size(); updateNum++) {
            iPMIHostSDRDaoService.update(updateList.get(updateNum));
        }
        // 删除多余数据
        for (int delNum = 0; delNum < delList.size(); delNum++) {
            iPMIHostSDRDaoService.delete(delList.get(delNum).getId());
        }
        // 增加新数据
        addNewSDR(sensorReadings, ipmiHost);
    }

    private void addNewSDR(Map<String, Object> sensorReadings, IPMIHost iPMIHost) {
        IPMIHostSDRDaoService iPMIHostSDRDaoService = WceDaoServiceHelper.getIPMIHostSDRDaoService();
        for (Map.Entry<String, Object> entry : sensorReadings.entrySet()) {
            if (StringUtil.isNotNull(entry.getKey())) {
                IPMIHostSDR iPMIHostSDR = new IPMIHostSDR();
                iPMIHostSDR.setHostId(iPMIHost.getId());
                iPMIHostSDR.setSdrKey(entry.getKey());
                iPMIHostSDR.setSdrValue(entry.getValue().toString());
                iPMIHostSDRDaoService.save(iPMIHostSDR);
            }
        }
    }

    *//**
     * 根据物理主机ID获取硬件信息列表
     * 
     * @return
     *//*
    private List<IPMIHostSDR> getSDRListFromDB(IPMIHost iPMIHost) {
        IPMIHostSDRDaoService iPMIHostSDRDaoService = WceDaoServiceHelper.getIPMIHostSDRDaoService();
        List<IPMIHostSDR> list = new ArrayList<IPMIHostSDR>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("hostId", iPMIHost.getId());
        QueryResult<IPMIHostSDR> queryResult = iPMIHostSDRDaoService.findByExample(params, true);
        list = queryResult.getResultList();
        return list;
    }*/

    // =============================业务 end=========================

    public static void main(String[] args) throws Exception {
        Process p = null;
        StringBuffer strBuff = new StringBuffer();
        try {
            BufferedInputStream in = null;
            p = Runtime.getRuntime().exec("ipmitool -I lanplus -H 10.0.1.101 -U USERID -P PASSW0RD power status");
            // p = Runtime.getRuntime().exec("ping 10.10.111.217");
            // p = Runtime.getRuntime().exec("cmd.exe");

            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) { // p.exitValue()==0表示正常结束，1：非正常结束
                    in = new BufferedInputStream(p.getErrorStream());
                }
            }
            else {
                in = new BufferedInputStream(p.getInputStream());
            }
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String strLine = "";
            while ((strLine = inBr.readLine()) != null) {
                strBuff.append(strLine + "\n");
            }
            System.out.println(strBuff.toString());
            // setResultStr(strBuff.toString());
            if (in != null) {
                in.close();
            }
            if (p.exitValue() == 1) {
                throw new Exception(strBuff.toString());
            }
        }
        catch (Exception e) {
            logger.info("执行命令失败，失败原因：" + e);
            throw new Exception(e);

        }
        finally {
            if (p != null) {
                try {
                    p.getOutputStream().close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
