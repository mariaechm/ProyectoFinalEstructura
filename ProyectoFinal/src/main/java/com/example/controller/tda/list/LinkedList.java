package com.example.controller.tda.list;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.example.controller.exception.ListEmptyException;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    Class<?> clazz;

    // ORDENAMIENTO
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    // CONSTRUCTOR
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public LinkedList(Class<?> claxs) {
        this();
        this.clazz = claxs;
    }

    // GETTERS Y SETTERS
    public Node<E> getHeader() {
        return header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    // LIMPIAR LA LISTA
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    // OBTENER TAMAÑO DE LA LISTA
    public Integer getSize() {
        return this.size;
    }

    // VERIFICAR SI LA LISTA ESTA VACIA
    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    // AGREGAR UN NODO AL INICIO
    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    // AGREGAR UN NODO AL FINAL
    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    // AGREGAR
    public void add(E info) {
        addLast(info);
    }

    // AGREGAR UN NODO A LA LISTA EN UNA POSICION ESPECIFICA
    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1); // Obtener el nodo anterior al que busco
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search); // Nodo que voy a agregar y apunto al nodo siguiente
            search_preview.setNext(help); // Al anterior le asigno el nuevo nodo, como siguiente
            this.size++;
        }
    }

    // OBTENER UN NODO
    public E get(Integer index) throws Exception {
        return getNode(index).getInfo();
    }

    // OBTENER UN NODO EN UNA POSICION ESPECIFICA
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacía");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, Índice Fuera de Rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                // Obtener el nodo siguiente de otro nodo y asi sucesivamente
                search = search.getNext();
            }
            return search;
        }
    }

    // ACTUALIZAR UN NODO EN UNA POSICION ESPECIFICA
    public void update(E data, Integer index) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacía");
        } else if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error, Índice Fuera de Rango");
        } else if (index == 0) {
            header.setInfo(data);
        } else if (index == (size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < index) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    // ELIMINAR UN NODO AL INICIO
    public void deleteHeader() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacía");
        } else {
            Node<E> delete = this.header; // El nodo por eliminar es el primero
            this.header = delete.getNext(); // El segundo nodo ahora es el primero
            delete.setNext(null); // El nodo eliminado apunta a null
            delete = null; // Valor null del nodo eliminado
            this.size--;
        }
    }

    // ELIMINAR UN NODO AL FINAL
    public void deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacía");
        } else {
            if (this.last == null) {
                this.deleteHeader();
            } else {
                Node<E> delete = this.last; // El nodo por eliminar es el elementoMayor
                delete.setNext(null); // Apunta a null
                this.last = this.getNode(this.size - 2); // Ahora el elementoMayor nodo es el anterior al eliminado
                delete = null; // Valor null del nodo eliminado
                this.last.setNext(null); // El nuevo elementoMayor nodo apunta a null
                this.size--;
            }
        }
    }

    // ELIMINAR UN NODO EN UNA POSICION ESPECIFICA
    public void delete(Integer index) throws Exception {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacía");
        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Error, Índice Fuera de Rango");
        } else if (index == 0) {
            this.deleteHeader();
        } else if (index == this.size - 1) {
            this.deleteLast();
        } else {
            Node<E> previus = getNode(index - 1); // Nodo previo al nodo por eliminar
            Node<E> eliminar = previus.getNext(); // Nodo a eliminar
            previus.setNext(eliminar.getNext()); // El nodo anterior al eliminado, apunta al siguiente del eliminado
            eliminar.setNext(null); // El nodo eliminado ahora apunta a null
            eliminar = null;
            this.size--;
        }
    }

    @Override // Sobreescribo el metodo toString para personalizarlo
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext(); // Recorro al otro nodo
            }
        } catch (Exception e) {
            sb.append(e.getMessage()); // getMessage devuelve el mensaje puntual de error
        }
        return sb.toString();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    // CONVERTIR LA LISTA A UN ARREGLO
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        Class<?> clase = (this.clazz != null) ? this.clazz : this.header.getInfo().getClass();  
        E[] array = (E[])Array.newInstance(clase, this.size);
        Node<E> node = this.header;
        for(int i = 0; i < this.size; i++) {
            array[i] = node.getInfo();
            node = node.getNext();
        }
        return array;
    }

    // CONVERTIR UN ARREGLO A UNA LISTA
    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    // COMPARAR
    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            // Menor a mayor
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
                // Mayor a menor
            default:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
        }
    }

    // COMPARAR OBJETOS
    private Boolean compararObjetos(Object a, Object b) {
        if (a instanceof Number && b instanceof Number) {
            Number c = (Number) a;
            Number d = (Number) b;
            return c.doubleValue() == d.doubleValue();
        } else if (a instanceof String && b instanceof String) {
            return ((String) a).contains((String) b);
        } else {
            return false;
        }
    }

    // COMPARAR ATRIBUTOS
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    // VERIFICAR SI EL ATRIBUTO EXISTE
    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().equals(attribute)) {
                method = aux;
                break;
            }
        }

        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().equals(attribute)) {
                    method = aux;
                    break;
                }
            }
        }

        if (method != null) {
            return method.invoke(a);
        }
        return null;
    }

    
    public LinkedList<E> buscarPorAtributo(String attribute, Object x) throws Exception {
        LinkedList<E> list = new LinkedList<>(this.clazz);
        if(isEmpty()) return list; 
        E[] array = this.toArray(); 
        for(int i = 0; i < array.length; i++) { 
            if(compararObjetos(exist_attribute(array[i], attribute), x)) {
                list.add(array[i]); 
            }
        }
        return list; 
    }

    // QUICK SORT
    private Integer particionArray(String attribute, E[] arr, Integer elementoMenor, Integer elementoMayor, Integer tipoOrden) throws Exception {
        E pivot = arr[elementoMayor]; 
        Integer j = elementoMenor - 1;
        for (int i = elementoMenor; i <= elementoMayor - 1; i++) {
            if (atrribute_compare(attribute, arr[i], pivot, tipoOrden)) {
                j++;
                intercambio(arr, j, i);
            }
        }
        intercambio(arr, j + 1, elementoMayor); 
        return j + 1;
    }

   
    private void intercambio(E[] arr, Integer i, Integer j) {
        E x = arr[i];
        arr[i] = arr[j];
        arr[j] = x;
    }

   
    private void quickSort(String attribute, E[] arr, Integer elementoMenor, Integer elementoMayor, Integer tipoOrden) throws Exception {
        if (elementoMenor < elementoMayor) {
            int posPivot = particionArray(attribute, arr, elementoMenor, elementoMayor, tipoOrden);
            quickSort(attribute, arr, elementoMenor, posPivot - 1, tipoOrden); 
            quickSort(attribute, arr, posPivot + 1, elementoMayor, tipoOrden); 
        }
    }

  
    public LinkedList<E> quickSort(String attribute, Integer tipoOrden) throws Exception {
        if (isEmpty()) return this; 
        E[] arr = this.toArray(); 
        final Integer elementoMayor = arr.length - 1;
        final Integer elementoMenor = 0;
        quickSort(attribute, arr, elementoMenor, elementoMayor, tipoOrden); 
        return this.toList(arr); 
    }

    // MERGE SORT
    private void merge(String atribute, E arr[], int left, int middle, int right, Integer tipoOrden) throws Exception {
        Class<?> classs = this.header.getInfo().getClass(); 
        
        int n1 = middle - left + 1; 
        int n2 = right - middle; 
        @SuppressWarnings("unchecked")
        E ArrayLeft[] = (E[]) Array.newInstance(classs, n1); 
        @SuppressWarnings("unchecked")
        E ArrayRight[] = (E[]) Array.newInstance(classs, n2);
     
        for (int i = 0; i < n1; ++i) {
            ArrayLeft[i] = arr[left + i];
        }
      
        for (int j = 0; j < n2; ++j) {
            ArrayRight[j] = arr[middle + 1 + j];
        }
        int i = 0, j = 0;
        
        int k = left;

        while (i < n1 && j < n2) {
            if (atrribute_compare(atribute, ArrayLeft[i], ArrayRight[j], tipoOrden)) {
                arr[k] = ArrayLeft[i];
                i++;
            } else {
                arr[k] = ArrayRight[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = ArrayLeft[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = ArrayRight[j];
            j++;
            k++;
        }
    }


    private void mergeSort(String attribute, E arr[], int left, int right, Integer tipoOrden) throws Exception {
        if (left < right) {
            int middle = left + (right - left) / 2; 
            mergeSort(attribute, arr, left, middle, tipoOrden); 
            mergeSort(attribute, arr, middle + 1, right, tipoOrden); 
            merge(attribute, arr, left, middle, right, tipoOrden); 
        }
    }


    public LinkedList<E> mergeSort(String attribute, Integer tipoOrden) throws Exception {
        if (isEmpty()) return this; 
        E[] array = this.toArray(); 
        final Integer left = 0;
        final Integer right = array.length - 1;
        mergeSort(attribute, array, left, right, tipoOrden); 
        reset();
        return this.toList(array); 
    }

    // SHELL SORT
    private int shellSort(String attribute, E[] arr, Integer tipoOrden) throws Exception {
        int longitud = arr.length;   
        for (int espacio = longitud / 2; espacio > 0; espacio /= 2) {         
            for (int i = espacio; i < longitud; i += 1) {
                E temp = arr[i];
                int j;        
                for (j = i; j >= espacio
                        && !atrribute_compare(attribute, arr[j - espacio], temp, tipoOrden); j -= espacio)
                    arr[j] = arr[j - espacio];
                arr[j] = temp;
            }
        }
        return 0;
    }


    public LinkedList<E> shellSort(String attribute, Integer tipoOrden) throws Exception {
        if (isEmpty())
            return this; 
        E[] array = this.toArray(); 
        shellSort(attribute, array, tipoOrden); 
        return this.toList(array); 
    }

    //BUSQUEDA LINEAL BINARIA
    public LinkedList<E> busquedaLinealBinaria(String attribute, Object x) {
        if(isEmpty()) return new LinkedList<>(this.clazz); 
        try { 
            Integer indice = getIndice(attribute, x); 
            Integer i = indice.intValue(); 
            E objeto = get(indice); 
            E[] arr = this.toArray(); 
            LinkedList<E> lista = new LinkedList<>();
       
            while(indice >= 0 && compararObjetos(exist_attribute(arr[indice], attribute),exist_attribute(objeto, attribute))) {
                lista.add(arr[indice]);
                indice--;
            }
            indice = i+1;
     
            while(indice < this.size && compararObjetos(exist_attribute(arr[indice], attribute),exist_attribute(objeto, attribute))) {
                lista.add(arr[indice]);
                indice++;
            }
            return lista;  
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }


    // BUSQUEDA BINARIA
    private int busquedaBinaria(E arr[], Object x, String attribute) throws Exception {
        int elementMenor = 0, elementMayor = arr.length - 1;
        while (elementMenor <= elementMayor) {
            int mid = elementMenor + (elementMayor - elementMenor) / 2;
            if (exist_attribute(arr[mid], attribute).equals(x)) return mid; 
            if (compare(exist_attribute(arr[mid], attribute), x, 1)) {
                elementMenor = mid + 1; 
            } else {
                elementMayor = mid - 1; 
            }
        }
        return -1; 
    }

    
    public E busquedaBinaria(String attribute, Object x) throws Exception {
        if (isEmpty()) return null; 
        try{
            E[] arr = this.toArray(); 
            mergeSort(attribute,arr,0,arr.length-1,ASC);
            return arr[busquedaBinaria(arr, x, attribute)]; 
        } catch (Exception e) {
            System.out.println("LinkedList.busquedaBinaria() dice: " + e.getMessage());
            return null;
        }
    }

    public Integer getIndice(String attribute, Object x) throws Exception {
        if (isEmpty()) return -1; 
        E[] arr = this.toArray();
        mergeSort(attribute, arr, 0, arr.length-1, ASC);; 
        return busquedaBinaria(arr, x, attribute); 
    }
}


