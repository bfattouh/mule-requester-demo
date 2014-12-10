package com.appnov.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;


/**
 * 
 * @author Fattouh
 *
 */
public class MuleRequesterTest extends FunctionalTestCase {
	

    @Override
    protected String getConfigResources() {
        return "businessLogic.xml, global-config.xml";
    }
    

    @Test
    public void testFlow() throws Exception {
    	MessageProcessor flow = muleContext.getRegistry().lookupObject("ftp-flow-write");
    	flow.process(getTestEvent(null));
		MuleClient client = muleContext.getClient();	
		MuleMessage result = client.request("ftp://YourFtpUser:YourFtpPassword@localhost/documents/ftp", RECEIVE_TIMEOUT);
		assertNotNull(result); 
		assertNull(result.getExceptionPayload());
		assertFalse(result.getPayload() instanceof NullPayload);
		assertEquals(result.getPayloadAsString(), "\"test\",\"test\",\"test\",\"test\"");
    }


}
