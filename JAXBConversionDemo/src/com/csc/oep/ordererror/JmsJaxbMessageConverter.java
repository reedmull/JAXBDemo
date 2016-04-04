package com.csc.oep.ordererror;

import com.bea.wlevs.adapters.jms.api.InboundMessageConverter;
import com.bea.wlevs.adapters.jms.api.MessageConverterException;
import com.bea.wlevs.adapters.jms.api.OutboundMessageConverter;
import com.oracle.cep.mappers.api.Mapper;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class JmsJaxbMessageConverter implements InboundMessageConverter, OutboundMessageConverter {
    
    private OrderErrorEvent result;
    private Mapper xmlMapper;
    
    public JmsJaxbMessageConverter() {
        super();
    }

    @Override
    public List convert(Message message) throws MessageConverterException{
        
        List<OrderErrorEvent> eventsList = new ArrayList<>();
        
        if(message instanceof TextMessage ){
            
            TextMessage textMessage = (TextMessage) message;
            
            try {
                StringReader reader = new StringReader(textMessage.getText());
                result = (OrderErrorEvent) xmlMapper.createUnmarshaller().unmarshal(new StreamSource(reader));
                eventsList.add(result);
                System.out.println("JMS Message Converted to OrderErrorEvent");
            } catch (Exception e) {   
                System.out.println(e.getMessage());   
            }     
            
        } else {         
             System.out.println("NO TEXT MESSAGE");    
        }
       
        return eventsList;
    }

    @Override
    public List<Message> convert(Session session, Object object) throws MessageConverterException, JMSException {
        
        String strResult= null;
        
        try {
            OrderErrorEvent errorEvent = (OrderErrorEvent)object;       
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            xmlMapper.createMarshaller().marshal(errorEvent, result);
            strResult = writer.toString();
            
            System.out.println("OrderErrorEvent Converted to JMS Message");
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        };
       
        TextMessage m = session.createTextMessage();
        m.setText(strResult);
        List<Message> result = new ArrayList<Message>();
        result.add(m);
        return result;
    }

    public void setXmlMapper(Mapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public Mapper getXmlMapper() {
        return xmlMapper;
    }

}
