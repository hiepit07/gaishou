package vn.bananavietnam.ict.common.aop;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 *
 */
@Aspect
@Component
public class MethodProgressLogAspect {

    private static Logger logger = Logger.getLogger(MethodProgressLogAspect.class);

    private Util util = new Util();
    
    @Before("execution(* vn.bananavietnam.ict.banana.service.*.*(..))")
    public void before(JoinPoint jp) {
        StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD START");
        logger.info(new String(sb));
    }
    @Before("execution(* vn.bananavietnam.ict.banana.db.mapper.*.*(..))")
    public void beforeBananaMapper(JoinPoint jp) {
        StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD START");
        logger.info(new String(sb));
    }
    @Before("execution(* vn.bananavietnam.ict.common.db.mapper.*.*(..))")
    public void beforeCommonMapper(JoinPoint jp) {
        StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD START");
        logger.info(new String(sb));
    }

    @After("execution(* vn.bananavietnam.ict.banana.service.*.*(..))")
    public void after(JoinPoint jp) {
    	StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD END");
        logger.info(new String(sb));
    }
    @After("execution(* vn.bananavietnam.ict.banana.db.mapper.*.*(..))")
    public void afterBananaMapper(JoinPoint jp) {
    	StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD END");
        logger.info(new String(sb));
    }
    @After("execution(* vn.bananavietnam.ict.common.db.mapper.*.*(..))")
    public void afterCommonMapper(JoinPoint jp) {
    	StringBuilder sb = new StringBuilder("");
        sb.append(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ",");
        sb.append(jp.getTarget().getClass().getName() + ",");
        sb.append(jp.getSignature().getName() + ",");
        sb.append(changeArgsToString(jp.getArgs()) + " METHOD END");
        logger.info(new String(sb));
    }
    
    
    /**
     * Convert args to string
     * @param args
     * @return string are converted from args
     */
    private String changeArgsToString (Object[] args){
    	String ret = "";
    	try {
    		ret = new ObjectMapper().writeValueAsString(args);
    	} catch (Exception e){
    		ret = Arrays.toString(args);
    	}  	
    	return ret;    	
    }
}