package com.example.controller.dao.implement;

import com.example.controller.tda.list.LinkedList;

public interface InterfazDao <T> {
    public LinkedList<T> listAll();
    public T get(Integer id) throws Exception;
    public T persist(T object) throws Exception;
    public T merge(T object, Integer index) throws Exception;
    public T remove(Integer id) throws Exception;   
}