public class TasksView extends VBox {
    
    public TasksView() {
        this.getStyleClass().add("tasks-view");
        
        // Header with add button
        HBox header = new HBox();
        header.getStyleClass().add("tasks-header");
        
        Label title = new Label("Tasks");
        title.getStyleClass().add("section-title");
        
        Button addTask = new Button("+ New Task");
        addTask.getStyleClass().add("add-task-button");
        addTask.setOnAction(e -> showAddTaskDialog());
        
        header.getChildren().addAll(title, new Pane(), addTask);
        HBox.setHgrow(title, Priority.ALWAYS);
        
        // Task categories
        TabPane taskCategories = new TabPane();
        taskCategories.getTabs().addAll(
            createTaskTab("Upcoming", createUpcomingTasks()),
            createTaskTab("Today", createTodayTasks()),
            createTaskTab("Completed", createCompletedTasks())
        );
        
        this.getChildren().addAll(header, taskCategories);
    }
    
    private Tab createTaskTab(String name, Node content) {
        Tab tab = new Tab(name);
        tab.setContent(content);
        tab.setClosable(false);
        return tab;
    }
    
    private ListView<TaskItem> createUpcomingTasks() {
        ListView<TaskItem> listView = new ListView<>();
        listView.getItems().addAll(
            new TaskItem("Prepare project presentation", "2025-04-25", Priority.HIGH),
            new TaskItem("Review quarterly reports", "2025-04-28", Priority.MEDIUM),
            new TaskItem("Call client about new requirements", "2025-04-30", Priority.LOW)
        );
        return listView;
    }
    
    private ListView<TaskItem> createTodayTasks() {
        ListView<TaskItem> listView = new ListView<>();
        listView.getItems().addAll(
            new TaskItem("Complete JavaFX UI design", "2025-04-19", Priority.HIGH),
            new TaskItem("Reply to email enquiries", "2025-04-19", Priority.MEDIUM)
        );
        return listView;
    }
    
    private ListView<TaskItem> createCompletedTasks() {
        ListView<TaskItem> listView = new ListView<>();
        listView.getItems().addAll(
            new TaskItem("Submit weekly report", "2025-04-15", Priority.MEDIUM, true),
            new TaskItem("Order new office supplies", "2025-04-14", Priority.LOW, true)
        );
        return listView;
    }
    
    private void showAddTaskDialog() {
        Dialog<TaskItem> dialog = new Dialog<>();
        dialog.setTitle("Add New Task");
        
        // Dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField taskName = new TextField();
        taskName.setPromptText("Task name");
        
        DatePicker datePicker = new DatePicker();
        
        ComboBox<Priority> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll(Priority.LOW, Priority.MEDIUM, Priority.HIGH);
        priorityCombo.setValue(Priority.MEDIUM);
        
        grid.add(new Label("Task:"), 0, 0);
        grid.add(taskName, 1, 0);
        grid.add(new Label("Due date:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Priority:"), 0, 2);
        grid.add(priorityCombo, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        // Add buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        
        dialog.showAndWait();
    }
    
    // Task item class for list
    public static class TaskItem extends HBox {
        
        public TaskItem(String name, String dueDate, Priority priority, boolean completed) {
            this.getStyleClass().add("task-item");
            
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(completed);
            
            Label taskName = new Label(name);
            taskName.getStyleClass().add("task-name");
            if (completed) {
                taskName.getStyleClass().add("completed");
            }
            
            Label date = new Label(dueDate);
            date.getStyleClass().add("task-date");
            
            Circle priorityIndicator = new Circle(5);
            priorityIndicator.getStyleClass().add(priority.toString().toLowerCase() + "-priority");
            
            Button editButton = new Button("✎");
            editButton.getStyleClass().add("edit-button");
            
            Button deleteButton = new Button("🗑");
            deleteButton.getStyleClass().add("delete-button");
            
            HBox actions = new HBox(editButton, deleteButton);
            actions.getStyleClass().add("task-actions");
            
            this.getChildren().addAll(
                checkBox, priorityIndicator, taskName, new Pane(), date, actions
            );
            HBox.setHgrow(taskName, Priority.ALWAYS);
        }
        
        public TaskItem(String name, String dueDate, Priority priority) {
            this(name, dueDate, priority, false);
        }
    }
    
    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}