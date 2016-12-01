package com.allowify.config;

import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class TomcatConfig {

	
	 @Bean
	    public EmbeddedServletContainerCustomizer containerCustomizer() {
		 
		 return new EmbeddedServletContainerCustomizer() {
		      @Override public void customize(ConfigurableEmbeddedServletContainer container) {
		       
		            container.setPort(8090);
		      }
		    };
	    }
	 
 /* private static final Logger logger = LoggerFactory.getLogger(TomcatConfig.class);

  @Bean(name = "tomcatCustomizer")
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new EmbeddedServletContainerCustomizer() {
      @Override public void customize(ConfigurableEmbeddedServletContainer container) {
        if (container instanceof TomcatEmbeddedServletContainerFactory) {
          TomcatEmbeddedServletContainerFactory tomcatFactory =
              (TomcatEmbeddedServletContainerFactory) container;
          tomcatFactory.addConnectorCustomizers(sslConnectorCustomizer());
        }
      }
    };
  }

  @Value("${connector.https.enabled}")
  private Boolean httpsEnabled;
  @Value("${connector.https.keystoreFile}")
  private Resource keystoreFile;
  @Value("${connector.https.keystorePass}")
  private String keystorePass;
  @Value("${connector.https.keyAlias}")
  private String keyAlias;

  @Bean(name = "tomcatSslConnectorCustomizer")
  public TomcatConnectorCustomizer sslConnectorCustomizer() {

    if (!httpsEnabled) {
      return new TomcatConnectorCustomizer() {
        @Override public void customize(Connector connector) {
        }
      };
    }

    return new TomcatConnectorCustomizer() {
      @Override public void customize(Connector connector) {
        connector.setSecure(true);
        connector.setScheme("https");
        connector.setAttribute("keyAlias", keyAlias);
        connector.setAttribute("keystorePass", keystorePass);
        try {
          connector.setAttribute("keystoreFile", keystoreFile.getFile());
        } catch (IOException e) {
          logger.error("cannot load keystore", e);
          throw new IllegalStateException("Cannot load keystore", e);
        }
        connector.setAttribute("clientAuth", "false");
        connector.setAttribute("sslProtocol", "TLS");
        connector.setAttribute("SSLEnabled", true);
      }
    };
  }
  */
}