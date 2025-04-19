public class MainLayout extends BorderPane {
    
    public MainLayout() {
        // Set up the main application layout
        this.setTop(createTopBar());
        this.setLeft(createSidebar());
        this.setCenter(createMainContent());
        
        // Apply stylesheets
        this.getStylesheets().add(getClass().getResource("/css/modern-theme.css").toExternalForm());
    }
    
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        
        // App title
        Label appTitle = new Label("Personal Scheduler");
        appTitle.getStyleClass().add("app-title");
        
        // Search box
        TextField searchField = new TextField();
        searchField.setPromptText("Search events, tasks or messages...");
        searchField.getStyleClass().add("search-field");
        
        // User profile
        Circle profilePic = new Circle(20);
        profilePic.getStyleClass().add("profile-pic");
        
        // Add elements to topBar with spacing
        topBar.getChildren().addAll(appTitle, new Pane(), searchField, profilePic);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        
        return topBar;
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        
        // Menu buttons
        Button calendarBtn = createMenuButton("Calendar", "calendar-icon");
        Button tasksBtn = createMenuButton("Tasks", "tasks-icon");
        Button messagesBtn = createMenuButton("Messages", "messages-icon");
        Button settingsBtn = createMenuButton("Settings", "settings-icon");
        
        sidebar.getChildren().addAll(
            calendarBtn, 
            tasksBtn, 
            messagesBtn, 
            new Separator(), 
            settingsBtn
        );
        
        return sidebar;
    }
    
    private Button createMenuButton(String text, String iconClass) {
        Button button = new Button(text);
        button.getStyleClass().addAll("menu-button", iconClass);
        return button;
    }
    
    private StackPane createMainContent() {
        StackPane contentArea = new StackPane();
        contentArea.getStyleClass().add("content-area");
        
        // Initial view is calendar
        contentArea.getChildren().add(new CalendarView());
        
        return contentArea;
    }
}