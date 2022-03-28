package scs.lab;

import com.sun.management.OperatingSystemMXBean;
import javafx.application.Platform;

import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class MeasureCpuStats implements Runnable {
    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);

    private ViewFXMLController viewFXMLController;

    public MeasureCpuStats() {
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    public void join() throws InterruptedException {
        worker.join();
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            String cpuUsage = (Math.floor(osBean.getCpuLoad() * 10000)/100) + "%";
            System.out.println(cpuUsage);
            Platform.runLater(() -> updateGUI(cpuUsage));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // do something here
        }
    }

    public void setViewFXMLController(ViewFXMLController viewFXMLController) {
        this.viewFXMLController = viewFXMLController;
    }

    private void updateGUI(String cpuUsage) {
        this.viewFXMLController.updateCpuStatsLabel(cpuUsage);
    }

    public Thread getWorker() {
        return worker;
    }
}
