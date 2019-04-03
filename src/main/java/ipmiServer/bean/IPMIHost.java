package ipmiServer.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @类说明 通过IPMI管理的物理主机
 * @author 郑熠
 * @create 2016-3-17 13:31:41
 */
@Entity
@Table(name = "IPMI_HOST")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class IPMIHost {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "HOST_NAME")
    private String hostName;

    @Column(name = "HOST_STATE")
    private String hostState;

    @Column(name = "HOST_OPER_STATE")
    private String hostOperState;

    @Column(name = "IPMI_IP")
    private String ipmiIp;

    @Column(name = "IPMI_NAME")
    private String ipmiName;

    @Column(name = "IPMI_PASSWORD")
    private String ipmiPassword;

    @Column(name = "HOST_OS")
    private String hostOS;

    @Column(name = "HOST_MANUFACTURER")
    private String hostManufacturer;

    @Column(name = "HOST_SNID")
    private String hostSNId;

    @Column(name = "ADD_TIME")
    private Date addTime;

    @Transient
    private String hostId;

    @Transient
    private List<IPMIHostSDR> sdrList;

    @Transient
    private Map<String,Object> sensorReadings;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostState() {
        return hostState;
    }

    public void setHostState(String hostState) {
        this.hostState = hostState;
    }

    public String getIpmiIp() {
        return ipmiIp;
    }

    public void setIpmiIp(String ipmiIp) {
        this.ipmiIp = ipmiIp;
    }

    public String getIpmiName() {
        return ipmiName;
    }

    public void setIpmiName(String ipmiName) {
        this.ipmiName = ipmiName;
    }

    public String getIpmiPassword() {
        return ipmiPassword;
    }

    public void setIpmiPassword(String ipmiPassword) {
        this.ipmiPassword = ipmiPassword;
    }

    public String getHostOS() {
        return hostOS;
    }

    public void setHostOS(String hostOS) {
        this.hostOS = hostOS;
    }

    public String getHostSNId() {
        return hostSNId;
    }

    public void setHostSNId(String hostSNId) {
        this.hostSNId = hostSNId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public List<IPMIHostSDR> getSdrList() {
        return sdrList;
    }

    public void setSdrList(List<IPMIHostSDR> sdrList) {
        this.sdrList = sdrList;
    }

    public String getHostManufacturer() {
        return hostManufacturer;
    }

    public void setHostManufacturer(String hostManufacturer) {
        this.hostManufacturer = hostManufacturer;
    }

    public String getHostOperState() {
        return hostOperState;
    }

    public void setHostOperState(String hostOperState) {
        this.hostOperState = hostOperState;
    }

    public Map<String, Object> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(Map<String, Object> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }
}
