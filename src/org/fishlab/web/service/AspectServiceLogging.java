package org.fishlab.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class AspectServiceLogging {
	private static Log log=LogFactory.getLog("AOP");
    public void doAfter(JoinPoint jp) {  
        log.info("doAfter: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
    }  
  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        long time = System.currentTimeMillis();  
        Object retVal = pjp.proceed();  
        time = System.currentTimeMillis() - time;  
        log.info("doAround:process time: " + time + " ms");  
        return retVal;  
    }  
  
    public void doBefore(JoinPoint jp) {  
        log.info("doBefore: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
    }  
  
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        log.warn("doThrowing: " + jp.getTarget().getClass().getName()  
                + "." + jp.getSignature().getName() + " throw exception");  
        log.warn(ex.getMessage());  
    }  
  
    protected void sendEx(String ex) {
    	log.warn("foundException:"+ex);
    }  
}
