package com.example.controller.dao.implement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
//import java.util.Scanner;
import com.example.controller.tda.list.LinkedList;
import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public abstract class AdapterDao<T> implements InterfazDao<T> {
    
    private Class<?> clazz;
    protected Gson g;
    public static String URL = "./media/";
    
    //CONSTRUCTOR VACIO
    public AdapterDao() {}

    //CONSTRUCTOR
    public AdapterDao(Class<?> clazz) {
        this.clazz = clazz;
        this.g = new Gson();
    }

    //OBTENER LA LISTA DE TODOS LOS OBJETOS
    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            list.toList(matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //OBTENER UN OBJETO
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        System.out.println("ID: " + id);
        System.out.println("LISTA: " + list);
        try {
            return list.busquedaBinaria("id", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se encontro el objeto con el id: " + id);
        }
    }

    //GUARDAR UN OBJETO
    public T persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        String info = g.toJson(list.toArray());
        saveFile(info);
        return object;
    }

    //ACTUALIZAR UN OBJETO
    public T merge(T object, Integer id) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, list.getIndice("id", id));
        String info = g.toJson(list.toArray());
        saveFile(info);
        return object;
    }
  
    //ELIMINAR UN OBJETO
    public T remove(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        Integer indice = list.getIndice("id", id);
        T object = list.get(indice);
        list.delete(indice);
        String info = g.toJson(list.toArray());
        saveFile(info);
        return object;
        
    }

    //GUARDAR EL ARCHIVO JSON
    private void saveFile(String data) throws Exception {
        FileWriter f = new FileWriter(URL + clazz.getSimpleName() + ".json");
        f.write(data);
        f.flush();
        f.close();
    }

    //LEER EL ARCHIVO JSON
    private String readFile() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(URL + clazz.getSimpleName() + ".json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
            
        }
        reader.close();
        return sb.toString().trim();
        /* Scanner in = new Scanner(new FileReader(URL + clazz.getSimpleName() + ".json"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return sb.toString(); */
    }
}