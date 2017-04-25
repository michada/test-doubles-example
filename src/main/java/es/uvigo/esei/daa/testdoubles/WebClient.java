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
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Esta clase de ejemplo define un único método que descarga el contenido de un
 * recurso, identificado por su URL, y lo devuelve en forma de cadena de texto.
 * 
 * En este caso, nos encontramos con el problema de simular una interfaz
 * externa (la de red), lo que suele complicar la realización de las pruebas.
 * 
 * @author Miguel Reboiro-Jato
 */
public class WebClient {
	public String getContent(URL url) throws IOException {
		try (InputStreamReader isr = new InputStreamReader(url.openStream())) {
			final StringBuilder sb = new StringBuilder();
			
			final char[] buffer = new char[2048];
			
			int count;
			while ((count = isr.read(buffer)) != -1) {
				sb.append(buffer, 0, count);
			}
			
			return sb.toString();
		}
	}
}
