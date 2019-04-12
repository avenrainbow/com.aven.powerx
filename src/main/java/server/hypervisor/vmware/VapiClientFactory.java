package server.hypervisor.vmware;

import com.vmware.vapi.bindings.StubConfiguration;
import com.vmware.vapi.protocol.HttpConfiguration;
import com.vmware.vcenter.Datacenter;
import server.bean.Platform;
import server.common.vmware.SslUtil;
import server.common.vmware.authentication.VapiAuthenticationHelper;


public class VapiClientFactory {

    private static Boolean skipServerVerification = true;

    private static StubConfiguration sessionStubConfig = null;

    private static VapiAuthenticationHelper vapiAuthHelper = new VapiAuthenticationHelper();


    /**
     * 组装HttpConfiguration
     * @return
     * @throws Exception
     */
    private static HttpConfiguration buildHttpConfiguration() throws Exception {
        HttpConfiguration httpConfig =
                new HttpConfiguration.Builder()
                        .setSslConfiguration(buildSslConfiguration())
                        .getConfig();

        return httpConfig;
    }

    /**
     * 信任SSL验证
     * @return
     * @throws Exception
     */
    private static HttpConfiguration.SslConfiguration buildSslConfiguration() throws Exception {
        HttpConfiguration.SslConfiguration sslConfig = null;

        if(skipServerVerification) {
            /*
             * Below method enables all VIM API connections to the server
             * without validating the server certificates.
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            SslUtil.trustAllHttpsCertificates();

            /*
             * Below code enables all vAPI connections to the server
             * without validating the server certificates..
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            sslConfig = new HttpConfiguration.SslConfiguration.Builder()
                    .disableCertificateValidation()
                    .disableHostnameVerification()
                    .getConfig();
        } else {
            /*
             * Set the system property "javax.net.ssl.trustStore" to
             * the truststorePath
             */
            /* System.setProperty("javax.net.ssl.trustStore", this.truststorePath);
            KeyStore trustStore =
                    SslUtil.loadTrustStore(this.truststorePath,
                            this.truststorePassword);
            HttpConfiguration.KeyStoreConfig keyStoreConfig =
                    new HttpConfiguration.KeyStoreConfig("", this.truststorePassword);
            sslConfig =
                    new HttpConfiguration.SslConfiguration.Builder()
                            .setKeyStore(trustStore)
                            .setKeyStoreConfig(keyStoreConfig)
                            .getConfig();*/
        }

        return sslConfig;
    }

    /**
     * 获取vapi连接信息
     * @return
     * @throws Exception
     */
    public static void getServiceInstance(Platform platform) throws Exception {


        if(sessionStubConfig == null){

            String server = platform.getIp();
            String username = platform.getUsername();
            String password = platform.getPassword();
            try {
                HttpConfiguration httpConfig = buildHttpConfiguration();
                sessionStubConfig = vapiAuthHelper.loginByUsernameAndPassword(server, username, password, httpConfig);
            }catch(Exception e){
                throw new Exception(e);
            }
        }

/*          Datacenter datacenterService = vapiAuthHelper.getStubFactory().createStub(Datacenter.class, sessionStubConfig);
            DatacenterTypes.Info datacenterInfo = datacenterService.get("datacenter-2");
            System.out.println(datacenterInfo.getHostFolder());
            System.out.println(datacenterInfo.getVmFolder());
            System.out.println(datacenterInfo.getDatastoreFolder());*/

            /*Host hostService = vapiAuthHelper.getStubFactory().createStub(Host.class, sessionStubConfig);
            HostTypes.FilterSpec filterSpec = new HostTypes.FilterSpec();
            Set<String> names = new HashSet<>();
            names.add("192.168.103.86");
            filterSpec.setNames(names);

            List<HostTypes.Summary> hostList = hostService.list(filterSpec);

            for(HostTypes.Summary hostSum : hostList){

                System.out.println(hostSum.getName());
            }*/
    }

    /**
     * 关闭vapi连接
     */
    public static void logout(){
        vapiAuthHelper.logout();
    }

    /**
     * 获取vcenter datacenter服务接口
     * @param platform
     * @return
     * @throws Exception
     */
    public static Datacenter getDatacenterService(Platform platform) throws Exception {
        getServiceInstance(platform);
        Datacenter datacenterService = vapiAuthHelper.getStubFactory().createStub(Datacenter.class, sessionStubConfig);
        return datacenterService;
    }


}
