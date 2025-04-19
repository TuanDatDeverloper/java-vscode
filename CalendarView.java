public class CalendarView extends BorderPane {
    
    private final ToggleGroup viewToggle = new ToggleGroup();
    
    public CalendarView() {
        this.setTop(createCalendarHeader());
        this.setCenter(createMonthView()); // Default to month view
    }
    
    private HBox createCalendarHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("calendar-header");
        
        // Date display
        Label currentDate = new Label("April 2025");
        currentDate.getStyleClass().add("current-date");
        
        // Navigation buttons
        Button prevBtn = new Button("◀");
        Button nextBtn = new Button("▶");
        Button todayBtn = new Button("Today");
        
        prevBtn.getStyleClass().add("nav-button");
        nextBtn.getStyleClass().add("nav-button");
        todayBtn.getStyleClass().add("today-button");
        
        // View toggle buttons
        ToggleButton dayView = new ToggleButton("Day");
        ToggleButton weekView = new ToggleButton("Week");
        ToggleButton monthView = new ToggleButton("Month");
        
        dayView.setToggleGroup(viewToggle);
        weekView.setToggleGroup(viewToggle);
        monthView.setToggleGroup(viewToggle);
        monthView.setSelected(true); // Default
        
        HBox viewToggleBox = new HBox(dayView, weekView, monthView);
        viewToggleBox.getStyleClass().add("view-toggle");
        
        // Add event button
        Button addEvent = new Button("+ Add Event");
        addEvent.getStyleClass().add("add-event-button");
        
        header.getChildren().addAll(
            prevBtn, todayBtn, nextBtn, 
            currentDate, 
            new Pane(), // Flexible space
            viewToggleBox,
            addEvent
        );
        HBox.setHgrow(currentDate, Priority.ALWAYS);
        
        return header;
    }
    
    private GridPane createMonthView() {
        GridPane monthGrid = new GridPane();
        monthGrid.getStyleClass().add("month-grid");
        
        // Set up days of week header
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.getStyleClass().add("day-header");
            monthGrid.add(dayLabel, i, 0);
        }
        
        // Create day cells
        for (int week = 1; week <= 6; week++) {
            for (int day = 0; day < 7; day++) {
                VBox dayCell = createDayCell(week, day);
                monthGrid.add(dayCell, day, week);
            }
        }
        
        return monthGrid;
    }
    
    private VBox createDayCell(int week, int day) {
        VBox cell = new VBox();
        cell.getStyleClass().add("day-cell");
        
        // Date number
        int date = (week - 1) * 7 + day - 1; // Just an example calculation
        if (date >= 0 && date < 30) {
            Label dateLabel = new Label(String.valueOf(date + 1));
            dateLabel.getStyleClass().add("date-label");
            
            // Example events
            if (date == 15) {
                Label event1 = new Label("10:00 AM - Meeting");
                Label event2 = new Label("2:00 PM - Dentist");
                event1.getStyleClass().add("event-item");
                event2.getStyleClass().add("event-item");
                cell.getChildren().addAll(dateLabel, event1, event2);
            } else if (date == 22) {
                Label event = new Label("All day - Conference");
                event.getStyleClass().add("event-item");
                cell.getChildren().addAll(dateLabel, event);
            } else {
                cell.getChildren().add(dateLabel);
            }
        }
        
        return cell;
    }
}