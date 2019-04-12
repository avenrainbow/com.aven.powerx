package server.bean;


import javax.persistence.*;

/**
 * @类说明 通过IPMI管理的物理主机传感器数据记录
 * @author 郑熠
 * @create 2016-3-17 13:31:41
 */
@Entity
@Table(name = "IPMI_HOST_SDR")
public class IPMIHostSDR {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "HOST_ID")
    private Long                hostId;

    @Column(name = "SDR_KEY")
    private String sdrKey;

    @Column(name = "SDR_VALUE")
    private String sdrValue;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getSdrKey() {
        return sdrKey;
    }

    public void setSdrKey(String sdrKey) {
        this.sdrKey = sdrKey;
    }

    public String getSdrValue() {
        return sdrValue;
    }

    public void setSdrValue(String sdrValue) {
        this.sdrValue = sdrValue;
    }
}
