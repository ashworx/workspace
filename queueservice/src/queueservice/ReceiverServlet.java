package queueservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public class ReceiverServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8704999023378839428L;

	@Resource(name = "myTestQueue")
	private Queue queue;

	@Resource
	private ConnectionFactory connectionFactory;

	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection;
		try {
			connection = connectionFactory.createConnection();
	        connection.start();
	
	        // Create a Session
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
	        // Create a MessageProducer from the Session to the Topic or Queue
	        MessageProducer producer = session.createProducer(queue);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	
	        // Create a message
	        TextMessage message = session.createTextMessage(req.getParameter("message-body"));
	
	        // Tell the producer to send the message
	        producer.send(message);
	        
	        PrintWriter writer = resp.getWriter();
	        writer.write("OK");
	
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
