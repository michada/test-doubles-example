/*
 * Copyright 2015 Miguel Reboiro Jato
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.uvigo.esei.daa.testdoubles;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.After;
import org.junit.Before;

/**
 * En este caso, los test se hacen utilizando un servidor real. Concretamente,
 * se hace uso de Jetty, un servidor completo pero ligero, con una
 * configuración mínima.
 * 
 * Al contrario que al usar mocks, en este caso no es necesario definir el
 * comportamiento de las dependencias a bajo nivel, pues su funcionalidad es
 * completa. Por lo tanto, en este caso se trata de una prueba de caja negra.
 * 
 * @author Miguel Reboiro Jato
 * @see http://download.eclipse.org/jetty/stable-9/apidocs/
 */
public class WebClientFakeTest extends WebClientTest {
	private String helloWorldURL;
	private String errorURL;
	
	private Server server;
	
	@Before
	public void setUp() throws Exception {
		this.client = new WebClient();
		
		// Configuración del servidor Jetty
		this.server = new Server(8888);

		final SimpleHandler handler = new SimpleHandler();
		handler.addMapping("/helloworld", "Hello World");
		
		this.server.setHandler(handler);
		
		this.server.start();
		
		this.helloWorldURL = "http://localhost:8888/helloworld";
		this.errorURL = "http://localhost:8888/missing";
	}
	
	@Override
	protected URL getHelloWorldUrl() {
		try {
			return new URL(this.helloWorldURL);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected URL getErrorUrl() {
		try {
			return new URL(this.errorURL);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@After
	public void tearDown() throws Exception {
		this.server.stop();
	}
	
	private static final class SimpleHandler extends AbstractHandler {
		private final Map<String, String> mapping;
		
		public SimpleHandler() {
			this.mapping = new HashMap<>();
		}
		
		public void addMapping(String key, String content) {
			this.mapping.put(key, content);
		}
		
		@Override
		public void handle(
			String target,
			Request baseRequest, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws IOException, ServletException {
			response.setContentType("text/html;charset=utf-8");
			baseRequest.setHandled(true);
			
			if (this.mapping.containsKey(target)) {
				response.setStatus(HttpServletResponse.SC_OK);

				final PrintWriter writer = response.getWriter();
				writer.write(this.mapping.get(target));
				writer.flush();
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
}
