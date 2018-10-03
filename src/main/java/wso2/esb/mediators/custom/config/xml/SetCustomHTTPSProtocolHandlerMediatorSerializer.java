package wso2.esb.mediators.custom.config.xml;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.AbstractMediatorSerializer;

import wso2.esb.mediators.custom.protocolhandler.SetCustomHTTPSProtocolHandlerMediator;

/**
 * 
 * Mediator serializer used to write out the configuration of a SetCustomHTTPSProtocolHandlerMediator object to the XML format
 * 
 * @author LGalkina
 *
 */

public class SetCustomHTTPSProtocolHandlerMediatorSerializer extends AbstractMediatorSerializer {

	public String getMediatorClassName() {
		return SetCustomHTTPSProtocolHandlerMediator.class.getName();
	}

	protected OMElement serializeSpecificMediator(Mediator m) {
		if (!(m instanceof SetCustomHTTPSProtocolHandlerMediator)) {
            handleException("Unsupported mediator passed in for serialization : " + m.getType());
        }
		
		OMElement mediatorElem = fac.createOMElement(SetCustomHTTPSProtocolHandlerMediatorFactory.SET_CUSTOM_PROTOCOL_HANDLER_Q);
        saveTracingState(mediatorElem, m);

        SetCustomHTTPSProtocolHandlerMediator mediator = (SetCustomHTTPSProtocolHandlerMediator) m;

        if (mediator.getProtocolHadlerType() != null) {
        	mediatorElem.addAttribute("id", mediator.getProtocolHadlerType(), nullNS);
        }
        
        return mediatorElem;
	}

}
