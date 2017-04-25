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

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * En este caso, los tests se hacen usando un mock de URL que nos permite
 * definir el comportamiento concreto que queremos que exhiba y, además, nos
 * permite comprobar si el objeto testeado hace las invocaciones esperadas.
 * 
 * Por lo tanto, este tipo de prueba se puede considerar de caja negra, pues en
 * los asserts se evalúa la salida ante una entrada determinada, y de caja
 * blanca, pues se evalúa el flujo de ejecución a través del objeto "mockeado".
 * 
 * @author Miguel Reboiro-Jato
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WebClient.class)
public class WebClientMockTest extends WebClientTest {
	private URL mockURL;
	
	@Before
	public void setUp() throws Exception {
		// Creación del mock de URL
		this.mockURL = createMock(URL.class);
	}
	
	@After
	public void tearDown() throws Exception {
		verify(this.mockURL);
	}
	
	@Override
	protected URL getHelloWorldUrl() {
		try {
			expect(this.mockURL.openStream())
				.andReturn(new ByteArrayInputStream("Hello World".getBytes()));
			
			replay(this.mockURL);
			
			return this.mockURL;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected URL getErrorUrl() {
		try {
			expect(this.mockURL.openStream())
			.andThrow(new FileNotFoundException());
		
			replay(this.mockURL);
			
			return this.mockURL;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
