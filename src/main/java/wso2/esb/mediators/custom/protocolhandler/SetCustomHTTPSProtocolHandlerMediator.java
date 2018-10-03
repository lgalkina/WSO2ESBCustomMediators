package wso2.esb.mediators.custom.protocolhandler;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.axis2.java.security.SSLProtocolSocketFactory;
import org.apache.axis2.java.security.TrustAllTrustManager;
import org.apache.axis2.transport.http.HTTPConstants;

/**
 * 
 * Custom mediator to set custom protocol handler for https protocol,
 * axis2 
 *  
 * (by setting CUSTOM_PROTOCOL_HANDLER property of axis2 message context),
 * can be used to set protocol handler 
 * 
 * @author LGalkina
 *
 */

public class SetCustomHTTPSProtocolHandlerMediator extends AbstractMediator {

	private static final String EASY_SSL_PROTOCOL_HANDLER = "easySSL";
	
	private static final String TRUST_ALL_PROTOCOL_HANDLER = "trustAll";
	
	private static final String PROTOCOL_HTTPS = "https";
	
	private static final int PORT_HTTPS = 443;
	
	private String protocolHadlerType;
	
	public boolean mediate(MessageContext context) {
		
		if(protocolHadlerType == null) {
			super.getLog(context).auditError("Mediator can't proceed because protocol hadler type is NULL");
			handleException("Mediator can't proceed because protocol hadler type is NULL", context);
		}
		
		try {
			if (protocolHadlerType.equals(TRUST_ALL_PROTOCOL_HANDLER)) {
				setEasySSLProtocolHandler();
			} else if (protocolHadlerType.equals(EASY_SSL_PROTOCOL_HANDLER)) {
				setTrustAllProtocolHandler();
			} else {
				super.getLog(context).auditWarn("Unknown protocol handler type, contine using standard protocol handler");
			}
		} catch (Exception e) {
			super.getLog(context).auditError("Exception while setting custom https protocol handler: " + e.getMessage());
            handleException("Exception while setting custom https protocol handler", e, context);
		}
		
		return true;
	}
	
	/**
	 * Set SSLProtocolSocketFactory with  as protocol socket factory instead of the standard one for https protocol
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private void setTrustAllProtocolHandler() throws GeneralSecurityException, IOException {
		SSLContext sslContext = SSLContext.getInstance("http");
		sslContext.init(null, new TrustManager[] {new TrustAllTrustManager()}, null);
		Protocol protocolHandler = new Protocol(PROTOCOL_HTTPS, new SSLProtocolSocketFactory(sslContext), PORT_HTTPS);		
		setCustomprotocolHandler(protocolHandler);
	}
	
	/**
	 * Set EasySSLProtocolSocketFactory as protocol socket factory instead of the standard one for https protocol
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private void setEasySSLProtocolHandler() throws GeneralSecurityException, IOException {
		Protocol protocolHandler = new Protocol(PROTOCOL_HTTPS, new EasySSLProtocolSocketFactory(), PORT_HTTPS);
		setCustomprotocolHandler(protocolHandler);
	}
	
	/**
	 * Set protocolHandler to CUSTOM_PROTOCOL_HANDLER property of Axis2 message context
	 * @param protocolHandler
	 */
	private void setCustomprotocolHandler(Protocol protocolHandler) {
		org.apache.axis2.context.MessageContext axisMsgCtx = new org.apache.axis2.context.MessageContext();  
		axisMsgCtx.getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, protocolHandler);
	}

	public String getProtocolHadlerType() {
		return protocolHadlerType;
	}

	public void setProtocolHadlerType(String protocolHadlerType) {
		this.protocolHadlerType = protocolHadlerType;
	}
}