package es.ubu.inf.edat.p02_2324;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import es.ubu.inf.edat.p02_2324.ColaMixta;
import junit.framework.TestCase;

/**
 *
 */
public class TestColaMixta extends TestCase {

	ColaMixta<Character> cola;

	// TODO - cola mixta de un tamaño de nodo diferente

	protected void setUp() throws Exception {
		cola = new ColaMixta<Character>(3);
	}

	protected void tearDown() throws Exception {
		cola.clear();
	}

	public void testSize() {
		assertEquals(0, cola.size());
		testOffer_dosSegmentos();
		assertEquals(5, cola.size());
	}

	public void testOffer_unSegmento() {
		assertTrue(cola.offer('a'));
		assertTrue(cola.offer('b'));
		assertTrue(cola.offer('c'));

		// [a,b,c]

		assertEquals("El tamaño de la cola no coincide con el esperado tras la inserción", 3, cola.size());
	}

	public void testOffer_dosSegmentos() {
		testOffer_unSegmento();

		assertTrue(cola.offer('d'));
		assertTrue(cola.offer('e'));

		// [a,b,c][d,e]

		assertEquals("El tamaño de la cola no coincide con el esperado tras la inserción", 5, cola.size());
	}

	public void testPeek() {
		assertEquals(0, cola.size());
		testOffer_dosSegmentos();
		assertEquals((Character) 'a', cola.peek());
		assertEquals(5, cola.size());
		assertEquals((Character) 'a', cola.peek());
		assertEquals(5, cola.size());
	}

	public void testPeek_Posicion1() {
		assertEquals(0, cola.size());
		testOffer_dosSegmentos();
		// [a,b,c][d,e]
		assertEquals((Character) 'a', cola.peek(0));
		assertEquals(5, cola.size());
		assertEquals((Character) 'd', cola.peek(3));
		assertEquals(5, cola.size());
	}

	public void testPeek_Null() {
		assertEquals(0, cola.size());
		assertEquals(null, cola.peek());
	}

	public void testPoll() {
		assertEquals(0, cola.size());
		testOffer_dosSegmentos();
		assertEquals((Character) 'a', cola.poll());
		assertEquals((Character) 'b', cola.poll());
		assertEquals((Character) 'c', cola.poll());
		assertEquals(2, cola.size());
		assertEquals((Character) 'd', cola.poll());
		assertEquals((Character) 'e', cola.poll());
		assertEquals(0, cola.size());
	}

	public void testIterator() {
		testOffer_dosSegmentos();
		List<Character> colaEsperada = Arrays.asList('a', 'b', 'c', 'd', 'e');
		Iterator<Character> it = cola.iterator();
		Iterator<Character> itEsperado = colaEsperada.iterator();
		while (itEsperado.hasNext()) {
			Character recuperado = it.next();
			Character esperado = itEsperado.next();
			assertEquals(esperado, recuperado);
		}
	}

	public void testAdd() {
		assertTrue(cola.add('a'));
		assertTrue(cola.add('b'));
		assertTrue(cola.add('c'));
		assertTrue(cola.add('d'));
		assertTrue(cola.add('e'));
		assertTrue(cola.add('f'));
		assertEquals(6, cola.size());
	}

	public void testAddAll() {
		List<Character> colaEsperada = Arrays.asList('a', 'b', 'c', 'd', 'e');

		this.cola.addAll(colaEsperada);

		Iterator<Character> it = cola.iterator();
		Iterator<Character> itEsperado = colaEsperada.iterator();

		while (itEsperado.hasNext()) {
			Character recuperado = it.next();
			Character esperado = itEsperado.next();

			assertEquals(esperado, recuperado);
		}
	}

	public void testClear() {
		assertEquals(0, cola.size());
		testOffer_dosSegmentos();
		assertEquals(5, cola.size());
		cola.clear();
		assertEquals(0, cola.size());
		assertEquals(null, cola.peek());
	}

}
