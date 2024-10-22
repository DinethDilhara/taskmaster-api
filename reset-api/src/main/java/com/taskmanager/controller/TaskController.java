package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService = new TaskService();

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) throws IOException, ClassNotFoundException {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public void createTask(@RequestBody Task task) throws IOException, ClassNotFoundException {
        taskService.createTask(task);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.ok("Task added successfully!");
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return taskService.loadTasks();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        boolean isRemoved = taskService.deleteTaskById(id);
        if (!isRemoved) {
            return ResponseEntity.status(404).body("Task not found");
        }
        return ResponseEntity.ok("Task deleted successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        boolean isUpdated = taskService.updateTask(id, updatedTask);
        if (!isUpdated) {
            return ResponseEntity.status(404).body("Task not found");
        }
        return ResponseEntity.ok("Task updated successfully!");
    }
}
