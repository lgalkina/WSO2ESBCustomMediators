package wso2.esb.mediators.custom.config.xml;

import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.AbstractMediatorFactory;
import org.apache.synapse.config.xml.XMLConfigConstants;

import wso2.esb.mediators.custom.protocolhandler.SetCustomHTTPSProtocolHandlerMediator;

/**
 * Mediator factory used to read the SetCustomHTTPSProtocolHandlerMediator configuration from the XML 
 * and initialize the SetCustomHTTPSProtocolHandlerMediator object
 * 
 * @author LGalkina
 * 
 */

public class SetCustomHTTPSProtocolHandlerMediatorFactory extends AbstractMediatorFactory {

	public static final QName SET_CUSTOM_PROTOCOL_HANDLER_Q = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE, "setCustomHTTPSProtocolHandler");
    private static final QName PROTOCOL_HANDLER_TYPE_Q = new QName(XMLConfigConstants.NULL_NAMESPACE, "type");
	
	public QName getTagQName() {
		return SET_CUSTOM_PROTOCOL_HANDLER_Q;
	}

	protected Mediator createSpecificMediator(OMElement elem, Properties properties) {
		
		SetCustomHTTPSProtocolHandlerMediator mediator = new SetCustomHTTPSProtocolHandlerMediator();
		processAuditStatus(mediator, elem);
		
		OMAttribute protocolHadlerType = elem.getAttribute(PROTOCOL_HANDLER_TYPE_Q);
        if (protocolHadlerType != null) {
        	mediator.setProtocolHadlerType(protocolHadlerType.getAttributeValue());
        }

        return mediator;
	}

}
