package ipmiServer.bean;

import java.util.Map;


public class IPMIHostModel {


    private String hostState;//主机状态


    private String hostSNId;//序列号
    

    private String hostManufacturer;//生产厂商
    

    private Map<String,Object> sensorReadings;
    
    public String getHostState() {
        return hostState;
    }

    public void setHostState(String hostState) {
        this.hostState = hostState;
    }

    public String getHostSNId() {
        return hostSNId;
    }

    public void setHostSNId(String hostSNId) {
        this.hostSNId = hostSNId;
    }

    public Map<String, Object> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(Map<String, Object> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public String getHostManufacturer() {
        return hostManufacturer;
    }

    public void setHostManufacturer(String hostManufacturer) {
        this.hostManufacturer = hostManufacturer;
    }
}
