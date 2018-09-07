/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todoweb.controllers;
import com.todoweb.models.Task;
import com.todoweb.dao.TaskDao;
import com.todoweb.util.HibernateSessionFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Контролер, который обрабатывает запросы связанные с задачами
 * @author VBuglak
 */

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class MainController {
    private static final Logger log = Logger.getLogger(MainController.class);
    @Autowired
    private TaskDao taskdaoimp;
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        log.debug("Starting index");
        map.put("msg", "Hello Spring 4 Web MVC!");
        log.debug("index done");
        return "index";
    }
/**
 * Получаем и отображаем список задач, при ошибках выводим сообщение через параметр message
 * @param message необязательный параметр для вывода сообщений об ошибках
 * @param model спринговская модель, которая содержит объекты: tasklist(лист задач), edittask(объект для изменения задачи), message(для отображения ошибки из параметра message)   
 * @return tasks.jsp
 */  
    @RequestMapping(value = "task", method = RequestMethod.GET)
    public String tasks(@RequestParam(required=false) String message, Model model){
        log.debug("Starting tasks");
        try{
            List <Task> tasklist = taskdaoimp.findAll();  
            model.addAttribute("tasklist", tasklist);
            model.addAttribute("edittask", new Task());
        }
        catch (Throwable e) {
            log.error("Something failed", e);
        }
        if(message!=null){
            model.addAttribute("message", message);
        }
        log.debug("tasks done");
        return "tasks";
    }
    /**
     * Получаем форму для создания задачи
     * @param model спринговская модель, которая содержит объекты: task(объект для добавления в бд) 
     * @return addtask.jsp
     */
    @RequestMapping(value = "addtaskform", method = RequestMethod.GET)
    public String addtaskget(Model model){ 
        log.debug("Starting addtaskget");
        Task task = new Task();
        model.addAttribute("task", task);
        log.debug("addtaskget done");
        return "addtask";
    }
    /**
     * Добавляем в бд новую задачу и возвращаем пользователя к списку задач
     * @param task объект с фронта, который нужно добавить в бд
     * @param model спринговская модель, которая содержит объекты: err_message(для отображения ошибки)
     * @return если есть ошибки addtask.jsp иначе redirect:/task
     */
    @RequestMapping(value = "addtask", method = RequestMethod.POST)
    public String addtaskpost(@ModelAttribute("task")Task task, Model model) { 
        log.debug("Starting addtaskpost");
        if (task.getName().equals("")){
            model.addAttribute("err_message", "Поле с названием задачи не заполнено");
            return "addtask";
        } else if(task.getName().length()>=30){
            model.addAttribute("err_message", "Недопустимая длина названия");
            return "addtask";
        }
        try{
            taskdaoimp.save(task);
        }
        catch(Throwable e){
            log.error("Something failed", e);
            model.addAttribute("err_message", "Ошибка добавления в бд");
            return "addtask";
        }
        log.debug("addtaskpost done");
        return "redirect:/task";
    }
    /**
     * Удаляем задачу из бд и обновляем страничку
     * @param model Спринговская модель
     * @param id id удаляемой задачи, которую мы получаем из url
     * @return если есть ошибки redirect:/task?message=delete error иначе redirect:/task
     */
    @RequestMapping(value = "deletetask{id}", method = RequestMethod.DELETE)
    public String deletetasks(Model model, @PathVariable int id){ 
        log.debug("Starting deletetasks");
        try{
            Task taskdel = taskdaoimp.findById(id);
            taskdaoimp.delete(taskdel);
        }
        catch (Throwable e) {
            log.error("Something failed", e);
            return "redirect:/task?message=delete error";
        }
        log.debug("deletetasks done");
        return "redirect:/task";    
    }
    /**
     * Получаем форму для редактирования задачи с подстановкой значений в инпуты
     * @param task объект с фронта, который нужно изменить в бд
     * @param id id изменяемого объекта
     * @param model спринговская модель, которая содержит объекты: task(объект для изменения в бд)
     * @return addtask.jsp
     */
    @RequestMapping(value = "edittaskform", method = RequestMethod.GET)
    public String edittasksform(@ModelAttribute("edittask")Task task, Model model){ 
        log.debug("Starting edittasksform");
        model.addAttribute("task", task);
        log.debug("edittasksform done");
        return "addtask";
    }
    /**
     * Изменяем задачу в бд и возвращаем к списку задач
     * @param task обновленный объект с фронта, который нужно изменить в бд
     * @param model спринговская модель, которая содержит объекты: err_message(для отображения ошибки)
     * @return если есть ошибки addtask.jsp иначе redirect:/task
     */
    @RequestMapping(value = "edittask{id}", method = RequestMethod.POST)
    public String edittasks(@ModelAttribute("task")Task task, Model model) { 
        log.debug("Starting edittasks");
        if (task.getName().equals("")){
            model.addAttribute("err_message", "Поле с названием задачи не заполнено");
            return "addtask";
        } else if(task.getName().length()>=30){
            model.addAttribute("err_message", "Недопустимая длина названия");
            return "addtask";
        }
        try{
            taskdaoimp.update(task);
        }
        catch (Throwable e) {
            log.error("Something failed", e);
            model.addAttribute("err_message", "Ошибка в бд при обновлении данных");
            return "addtask";
        }
        log.debug("edittasks done");
        return "redirect:/task";
    }
}
