package es.ubu.inf.edat.p02_2324;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


public class ColaMixta<E> extends AbstractQueue<E> implements Queue<E>{

	protected class NodoMixto extends AbstractQueue<E> implements Queue<E> {

		private List<E> contenido; //contenido del nodo
		private NodoMixto siguiente; //permite enlazar al nodo siguiente
		
		NodoMixto(int tamanoLista){
			contenido = new ArrayList<E>(tamanoLista);
			siguiente = null;
		}
		
		protected void setNextNodo(NodoMixto nodoSiguiente) {
			this.siguiente = nodoSiguiente;
		}
		
		protected NodoMixto getNextNodo() {
			return this.siguiente;
		}
		
		/**
		 * Añade un elemento a la lista
		 * 
		 * @param e elemento a añadir a la lista
		 */
		@Override
		public boolean offer(E e) {
			boolean estOp = false;
			if(contenido.size() != MAX_TAMANO_NODO) {
				contenido.add(e);
				estOp = true;
			}
			
			return estOp;
		}
		
		/**
		 * Devulve el primer elemento de la lista eliminandolo
		 * 
		 * @return primer elemento de la lista
		 */
		@Override
		public E poll() {
			if(contenido.isEmpty()) return null;
			E e = contenido.get(0);
			contenido.remove(0);
			return e;
		}
		
		/**
		 * Devuelve el elemento mas recientemente añadido a la lista
		 * @return último elemento en añadirse a la lista
		 */
		@Override
		public E peek() {

			if(contenido.isEmpty()) throw new NoSuchElementException("La lista está vacía");
			
			return contenido.get(contenido.size() - 1);
		}
		
		
		@Override
		public Iterator<E> iterator() {
			return contenido.iterator();
		}
		
		
		/**
		 * @return devuelve el tamaño de la lista
		 */
		@Override
		public int size() {
			return contenido.size();
		}
		
		/**
		 * Elimina todos los elementos de la lista
		 */
		@Override
		public void clear() {
			contenido.clear();
		}

	}
	
	private final int MAX_TAMANO_NODO; //Maximo tamaño del nodo
	private int tamanoCola; //Maximo tamaño de la cola
	private NodoMixto inicio, fin, actual;
	private int N; //numero de elementos de la cola
	private boolean colaConTamanoMaximo;

	/**
	 * Constructor de la clase
	 * 
	 * @param tamanoNodo Numero de elementos que se introducen como maximo en un
	 *                   nodo
	 * @throws IllegalArgumentException si el tamaño del nodo no es correcto
	 */
	public ColaMixta(int tamanoNodo) {
		if(tamanoNodo < 1) 
			throw new IllegalArgumentException("Tamaño de nodo incorrecto, el tamaño tiene que ser mayor que 0.");
		
		MAX_TAMANO_NODO = tamanoNodo;
		tamanoCola = 0;
		inicio = null;
		fin = null;
		actual = null;
		N = 0;
		colaConTamanoMaximo = false;
	}
	
	/**
	 * Constructor de la clase
	 * 
	 * @param tamanoNodo Numero de elementos que se introducen como maximo en un
	 *                   nodo
	 * @param tamanoCola Tamaño maximo de elementos de la cola
	 * @throws IllegalArgumentException si el tamaño del nodo no es correcto
	 */
	public ColaMixta(int tamanoNodo, int tamanoCola) {
		if(tamanoNodo < 1 || tamanoCola < 1) 
			throw new IllegalArgumentException("Parámetro/s incorrecto/s");
		
		MAX_TAMANO_NODO = tamanoNodo;
		this.tamanoCola = tamanoCola;
		inicio = null;
		fin = null;
		actual = null;
		N = 0;
		colaConTamanoMaximo = true;
	}
	
	/**
	 * Devuelve el nodo que esté en la posición de la cola indicada en el parámetro
	 * 
	 * @param index posición en la cola del parámetro
	 * @return elemento en la posiciòn indicada en el parámetro index
	 */
	public E peek(int index) {
		if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException("Indice fuera de rango");
		//if(isEmpty()) throw new NoSuchElementException("No hay elementos en la cola");
		
		NodoMixto nodoAnterior = inicio;
		for(int i = 1; i <= index; i++) {
			nodoAnterior = nodoAnterior.getNextNodo();
		}
		return nodoAnterior.peek();
	}

	/**
	 * Iterador que permite recorrer todos los elementos de la cola. Debe recorrer
	 * cada segmento de la misma (empleando el iterador del nodo) y pasar al nodo
	 * siguiente para repetir la operación. Se detiene al no haber más nodos.
	 *
	 * @param <E>
	 */
	private class IteradorMixto implements Iterator<E> {
		
		private NodoMixto actual;
		
		private IteradorMixto(NodoMixto primero) {
			actual = primero;
		}
		
		@Override
		public boolean hasNext() {
			return actual != null;
		}

		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			E item = actual.element();
			actual = actual.getNextNodo();
			return item;
			
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Añade un elemento al final de la cola si es posible
	 * 
	 * @param e elemento a añadir a la cola
	 * @return true si el elemento se ha añadido, false caso contrario
	 */
	@Override
	public boolean offer(E e) {
		boolean estOp = false; //determina si la operación ha sido exitosa
		if(colaConTamanoMaximo) {
			if(tamanoCola != N) {
				estOp = addElement(e);
			}else {
				estOp = false;
			}
		}else {
			estOp = addElement(e);
		}
		
		return estOp;
	}
	
	/**
	 * Añade un elemento al final de la cola
	 * 
	 * @param e elemento a añadir
	 * @return true si el elemento se ha añadido, false caso contrario
	 */
	private boolean addElement(E e)
	{
		boolean estOp = false;
		if(inicio == null) {
			inicio = new NodoMixto(MAX_TAMANO_NODO);
			inicio.offer(e);
			fin = inicio;
			actual = inicio;
			estOp = true;
			N++;
		}else {
			fin = new NodoMixto(MAX_TAMANO_NODO);
			fin.offer(e);
			actual.setNextNodo(fin); //guarda el nodo anterior al recien añadido
			actual = fin;
			estOp = true;
			N++;
		}
		return estOp;
	}
	
	/**
	 * Añade un elemento al final de la cola si es posible
	 * 
	 * @param e elemento a añadir a la cola
	 * @return true si el elemento se ha añadido, false caso contrario
	 * @throws IllegalStateException si la memoria de la cola está llena
	 */
	public boolean add(E e) {
		if(colaConTamanoMaximo) {
			if(tamanoCola == N) throw new IllegalStateException("Memoria de la cola llena");
		}
		
		return offer(e);
	}
	
	/**
	 * Añade todos los elementos de una coleccion
	 * 
	 * @param elements elementos a añadir a la cola
	 * @return true si se ha añadido correctamente, false caso contrario
	 */
	public boolean addAll(Collection<? extends E> elements) {
		boolean estOp;
		try {
			for(E elem : elements) {
				add(elem);
			}
			estOp = true;
		}catch(Exception e) {
			estOp = false;
		}
		
		return estOp;
	}

	/**
	 * Devuelve el primer elemento de la cola eliminandolo de la misma
	 * , si la cola está vacía devuelve null
	 */
	@Override
	public E poll() {
		
		if(N == 0) return null;
		
		NodoMixto NodoADevolver = inicio;
		inicio = inicio.getNextNodo();

		N--;
		return NodoADevolver.element();
	}
	
	/**
	 * Devuelve el primer elemento de la cola sin eleminarlo
	 * 
	 * @return el primer elemento de la cola
	 */
	@Override
	public E peek() {
		if(isEmpty()) return null;
		
		return inicio.peek();
	}
	
	/**
	 * Elimina todos los elementos de la cola
	 */
	@Override
	public void clear() {
		inicio = actual = fin = null;
		N = 0;
	}

	/**
	 * Devuelve el iterador de la clase
	 * 
	 * @return iterador de la clase
	 */
	@Override
	public Iterator<E> iterator() {
		return new IteradorMixto(this.inicio);
	}

	/**
	 * Devuelve el tamaño de la cola
	 */
	@Override
	public int size() {
		return N;
	}
	
	/**
	 * Devuelve true si la lista está vacía, false caso contrario
	 */
	public boolean isEmpty() {
		return N == 0;
	}	

}
