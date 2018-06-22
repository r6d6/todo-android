package com.example.angelo.todo.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaskTest {
    @Test
    public void testTaskShouldSetName() {
        Task task = new Task("John Doe");

        assertEquals("John Doe", task.getName());
    }

    @Test
    public void testTaskShouldSetId() {
        Task task = new Task("John Doe");
        task.setId(5);

        assertEquals(5, task.getId());
    }
}
