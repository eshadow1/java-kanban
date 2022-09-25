package model.history;

import model.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void getAndSetNextNode() {
        Task task = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        Node<Task> node = new Node<>(task);
        assertNull(node.getNextNode());

        Task taskNext = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        Node<Task> nodeNext = new Node<>(taskNext);
        node.setNextNode(nodeNext);
        assertEquals(nodeNext, node.getNextNode());
    }

    @Test
    void getAndSetPrevNode() {
        Task task = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        Node<Task> node = new Node<>(task);
        assertNull(node.getNextNode());

        Task taskPrev = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        Node<Task> nodePrev = new Node<>(taskPrev);
        node.setPrevNode(nodePrev);
        assertEquals(nodePrev, node.getPrevNode());
    }

    @Test
    void getData() {
        Task task = Task.fromArrayString(new String[]{"35", "TASK", "task1", "NEW", "test1"});
        Node<Task> node = new Node<>(task);
        assertEquals(task, node.getData());
    }
}