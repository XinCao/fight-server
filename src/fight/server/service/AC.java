package fight.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author caoxin
 */
public class AC implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(AC.class);
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        AC.ac = ac;
    }

    public static ApplicationContext getAC() {
        if (ac == null) {
            log.error("this ApplicationContext is null!");
        }
        return ac;
    }
}