/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todoweb.dao;

import com.todoweb.models.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.todoweb.util.HibernateSessionFactoryUtil;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 * Класс для работы с базой данных сущности Task
 * @author VBuglak
 */
@Repository
public class TaskDao {
/**
 * Метод для нахождения задачи по его id
 * @param id id задачи, которую нужно найти
 * @return найденный объект класса Task 
 */
    public Task findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Task.class, id);
    }
/**
 * Метод для добавления новой задачи в бд
 * @param task объект, который нужно добавить в бд
 */
    public void save(Task task){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(task);
            tx1.commit();
        }
    }
/**
 * Метод для изменения уже существующего объекта в бд
 * @param task измененый объект, который нужно обновить в бд
 */
    public void update(Task task){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(task);
            tx1.commit();
        }
    }
/**
 * Метод для удаления задачи из бд
 * @param task объект, который нужно удалить 
 */
    public void delete(Task task){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(task);
            tx1.commit();
        }
    }
/**
 * Метод для получения списка всех задач из бд
 * @return List<Task> все задачи из бд
 */
    public List<Task> findAll(){
        List<Task> tasks = (List<Task>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Task").list();
        return tasks;
    }
}