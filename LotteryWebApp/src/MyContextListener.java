import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class MyContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        File dir = new File("EncryptedFiles");
        if(dir.exists()) {
            for (File file : dir.listFiles()){
                if (!file.isDirectory()){
                    file.delete();
                }
            }

            dir.delete();
        }
    }
}