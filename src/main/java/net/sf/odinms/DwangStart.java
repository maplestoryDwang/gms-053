package net.sf.odinms;

import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.net.login.LoginServer;
import net.sf.odinms.net.world.WorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一键启动类
 *
 * @author dwang
 * @version 1.0
 * @since 2026/7/3 14:02
 */
public class DwangStart {

    private static final Logger log = LoggerFactory.getLogger(DwangStart.class);

    public static void main(final String[] args) {
        // ====================================================================
        // 1. 硬编码注入所有启动参数 (原本的 -D 参数)
        // ====================================================================
        log.info("正在加载系统核心参数配置...");

        // 通用基础配置
        System.setProperty("net.sf.odinms.recvops", "recvops.properties");
        System.setProperty("net.sf.odinms.sendops", "sendops.properties");

        // 【注意】这里统一 WZ 路径。如果你的 WZ 文件夹叫 wz，请确保各个服务端能在这个路径下读取到
        System.setProperty("net.sf.odinms.wzpath", "./wz");

        // 服务端各自的特殊配置文件
        System.setProperty("net.sf.odinms.login.config", "login.properties");
        System.setProperty("net.sf.odinms.channel.config", "channel.properties");

        // ====================================================================
        // 2. 配置 RMI 内置的安全 SSL 证书
        // 【注意】由于单进程内 SSL 密钥库只能全局指定一个，这里统一使用 world 的证书。
        // 因为 OdinMS 内部只是通过 RMI 自连，单进程内三个服务共用一整套 keystore 是完全可行的。
        // ====================================================================
        System.setProperty("javax.net.ssl.keyStore", "world.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "mysecretpassword");
        System.setProperty("javax.net.ssl.trustStore", "world.truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "mysecretpassword");

        log.info("参数加载完毕，开始串行引导各个子服务...");

        // ====================================================================
        // 3. 严格顺序启动子线程
        // ====================================================================

        // [步骤一] 启动 WorldServer
        try {
            log.info(" [1/3] 正在本地进程中拉起 WorldServer...");
            WorldServer.main(args);
        } catch (Exception e) {
            log.error("WorldServer 引导失败，单进程初始化中断！", e);
            System.exit(1);
        }

        // 稳妥起见，静置 2 秒等待 WorldServer 的 RMI 注册表彻底创建完毕
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // [步骤二] 异步启动 LoginServer
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(" [2/3] 正在本地进程中拉起 LoginServer...");
                    LoginServer.getInstance().run();
                } catch (Exception e) {
                    log.error("LoginServer 线程抛出致命异常！", e);
                }
            }
        }, "LoginServer-Thread").start();

        // [步骤三] 异步启动 ChannelServer
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(" [3/3] 正在本地进程中拉起 ChannelServer 频道群...");
                    ChannelServer.main(args);
                } catch (Exception e) {
                    log.error("ChannelServer 线程抛出致命异常！", e);
                }
            }
        }, "ChannelServer-Thread").start();

        log.info("=================================================");
        log.info(" OdinMS 整合版中央控制台已完全托管运行。");
        log.info("=================================================");
    }

}
