package core.history;

import model.history.Node;
import model.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CustomLinkedList<T extends Task> {
    private final Map<Integer, Node<T>> indexNode = new HashMap<>();
    private Node<T> headTasks = null;
    private Node<T> lastTasks = null;

    public void linkLast(int idTask, T task) {
        Node<T> newNodeTask = new Node<>(task);
        if (indexNode.containsKey(idTask)) {
            removeNode(indexNode.get(idTask));
        }
        indexNode.put(idTask, newNodeTask);
        if (headTasks == null) {
            headTasks = newNodeTask;
        } else {
            lastTasks.setNextNode(newNodeTask);
            newNodeTask.setPrevNode(lastTasks);
        }
        lastTasks = newNodeTask;
    }

    public List<T> getTasks() {
        if (headTasks == null) {
            return new ArrayList<>();
        }

        List<T> tasksInArray = new ArrayList<>(indexNode.size());
        Node<T> currentNode = headTasks;

        while (currentNode != null) {
            tasksInArray.add(currentNode.getData());
            currentNode = currentNode.getNextNode();
        }

        return tasksInArray;
    }

    public void remove(int idTask) {
        if (!indexNode.containsKey(idTask)) {
            return;
        }

        removeNode(indexNode.get(idTask));
        indexNode.remove(idTask);
    }

    private void removeNode(Node<T> node) {
        if (node == headTasks) {
            if (headTasks == lastTasks) {
                headTasks = null;
                lastTasks = null;
            } else {
                headTasks = headTasks.getNextNode();
                headTasks.setPrevNode(null);
            }
        } else if (node == lastTasks) {
            lastTasks = lastTasks.getPrevNode();
            lastTasks.setNextNode(null);
        } else {
            node.getPrevNode().setNextNode(node.getNextNode());
            node.getNextNode().setPrevNode(node.getPrevNode());
        }
    }
}
