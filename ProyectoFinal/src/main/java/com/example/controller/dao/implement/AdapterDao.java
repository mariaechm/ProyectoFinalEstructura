package com.example.controller.dao.implement;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import com.example.controller.tda.list.LinkedList;
import com.google.gson.Gson;


@SuppressWarnings("unchecked")
public abstract class AdapterDao<T> implements InterfazDao<T> {
    
    private Class<?> clazz;

    protected Gson g;
    protected String className;

    LinkedList<T> listAll;

    
    //CONSTRUCTOR VACIO
    public AdapterDao() {}

    //CONSTRUCTOR
    public AdapterDao(Class<?> clazz) {
        this.clazz = clazz;
        this.className = clazz.getSimpleName();
        this.g = new Gson();
    }

    public T[] getArray() {
        try {
            Type arrayType = Array.newInstance(clazz, 0).getClass();
            T[] array = g.fromJson(JsonFileManager.readFile(className), arrayType);
            if(array == null) {
                T[] objects = (T[])Array.newInstance(clazz, 0);
                return objects;
            }
            return array;
        } catch (Exception e) {
            System.out.println("AdapterDao.getArray() dice: " + e.getMessage());
            T[] objects = (T[])Array.newInstance(clazz, 0);
            return objects;
        }
    }

    //OBTENER LA LISTA DE TODOS LOS OBJETOS
    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>(clazz);
        list.toList(getArray());
        return list;
    }

    //OBTENER UN OBJETO
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
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
        JsonFileManager.saveFile(list.toArray(), className);
        return object;
    }

    //ACTUALIZAR UN OBJETO
    public T merge(T object, Integer id) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, list.getIndice("id", id));
        JsonFileManager.saveFile(list.toArray(), className);
        return object;
    }
  
    //ELIMINAR UN OBJETO
    public T remove(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        Integer indice = list.getIndice("id", id);
        T object = list.get(indice);
        list.delete(indice);
        JsonFileManager.saveFile(list.toArray(), className);
        return object;
        
    }
}
