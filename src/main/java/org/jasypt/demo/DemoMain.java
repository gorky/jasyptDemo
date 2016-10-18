package org.jasypt.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring bootstrap class to use embedded Tomcat Server.
 * Demo of issues with Spring Boot/Jasypt integration.
 * @author Steve Davidson
 */
@Configuration
@ComponentScan(basePackages = {"org.jasypt.demo"})
@ImportResource({"/jasypt-context.xml"})
@PropertySource("classpath:/application.properties")
// Per https://github.com/ulisesbocchio/jasypt-spring-boot the following will enable support
// for Encyrpted properties.
@EnableAutoConfiguration
public class DemoMain {
  
  /**
   * Logger for the class.
   */
  static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

  
  /**
   * Main method.
   * @param args Command line arguments.
   * @throws Throwable thrown if any errors occur during startup.
   */
  public static void main(final String args[]) throws Throwable{
    @SuppressWarnings("resource")
    final ConfigurableApplicationContext ctx = SpringApplication.run(DemoMain.class, args);
    LOGGER.info("Created the ctx:{}", ctx.getDisplayName());
    Runtime.getRuntime().addShutdownHook(new ShutDownThread(ctx));
  }
  
  /**
   * A thread to run the closing of the context.
   */

  public static class ShutDownThread extends Thread {

    private ConfigurableApplicationContext ctx;

    /**
     * Constructor.
     * @param ctx The Context to shutdown.
     */
    public ShutDownThread(final ConfigurableApplicationContext ctx) {
      this.ctx = ctx;
    }

    @Override
    public void run() {
      if (this.ctx != null) {
        try {
          LOGGER.warn("Closing the context:{}", this.ctx.getDisplayName());
          this.ctx.close();
          LOGGER.warn("Closed the context:{}", this.ctx.getDisplayName());
        } catch (final Exception e) {
          LOGGER.warn("Unable to close context:{}", this.ctx.getDisplayName(), e);
        }
      }
    }
  }
}
