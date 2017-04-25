/*
 * Copyright 2016 Miguel Reboiro Jato
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

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

/**
 * Superclase para los tests de WebClient. Esta superclase crea la instancia
 * que se va a probar (client) y contiene los tests, de modo que las subclases
 * solo deben implementar los métodos getHelloWorldUrl() y getErrorUrl().
 * 
 * Esta refactorización sirve para mostrar más claramente que las
 * funcionalidades que se evalúan en WebClientFakeTest y WebClientMockTest son
 * exactamente las mismas, y que lo único que varía es el contexto (fixture).
 * 
 * @author Miguel Reboiro-Jato
 *
 */
public abstract class WebClientTest {
	protected WebClient client = new WebClient();
	
	protected abstract URL getHelloWorldUrl();
	protected abstract URL getErrorUrl();
	
	@Test
	public final void testHelloWorld() throws IOException {
		final URL url = getHelloWorldUrl();
		
		assertEquals(
			"Hello World",
			this.client.getContent(url)
		);
	}
	
	@Test(expected = FileNotFoundException.class)
	public final void testError() throws IOException {
		final URL url = getErrorUrl();
		
		this.client.getContent(url);
	}
}
