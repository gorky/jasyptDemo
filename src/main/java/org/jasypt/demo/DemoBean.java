/*
 * Copyright (c) 2016 Cloudvisory LLC. All rights reserved.
 */
package org.jasypt.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Stephen Davidson
 *
 */
@Service
public class DemoBean implements InitializingBean {

  /**
   * Logger for the class.
   */
  static final Logger LOGGER = LoggerFactory.getLogger(DemoBean.class);
  
  @Value("demo.jasypt.value")
  private String aValue;

  @Override
  public void afterPropertiesSet() throws Exception {
    LOGGER.info("Value: {}", this.aValue);
  }
  
  /**
   * Constructor.
   */
  public DemoBean() {
    //end <init>
  }
  
  /**
   * Demo method that logs the parameter and setting.
   * @param obj the Object to be logged.
   */
  public void doSomething(final Object obj){
    LOGGER.info("Param: {}", obj);
    LOGGER.info("Value: {}", this.aValue);
  }


}
