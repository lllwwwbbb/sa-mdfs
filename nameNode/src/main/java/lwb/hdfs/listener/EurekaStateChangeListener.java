package lwb.hdfs.listener;

import com.netflix.appinfo.InstanceInfo;
import lwb.hdfs.service.DataNodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {

    private Logger logger = Logger.getLogger(EurekaInstanceCanceledEvent.class);

    private final DataNodeService dataNodeService;

    @Autowired
    public EurekaStateChangeListener(DataNodeService dataNodeService) {
        this.dataNodeService = dataNodeService;
    }

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info(event.getAppName() + " 服务下线");
        dataNodeService.onDataNodeDown(event.getServerId());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        // 检查 status
        if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
            logger.info(instanceInfo.getAppName() + "进行注册");
            dataNodeService.onDataNodeRegister(instanceInfo.getInstanceId(), instanceInfo.getIPAddr(), instanceInfo.getPort());
        }
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        logger.info(event.getAppName() + " 服务进行续约");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        logger.info("Eureka Server 启动");
    }
}
