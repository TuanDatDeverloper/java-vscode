public class MessagingView extends BorderPane {
    
    public MessagingView() {
        this.setLeft(createContactsList());
        this.setCenter(createChatArea());
    }
    
    private VBox createContactsList() {
        VBox contacts = new VBox();
        contacts.getStyleClass().add("contacts-list");
        
        // Search contacts
        TextField searchContacts = new TextField();
        searchContacts.setPromptText("Search contacts...");
        searchContacts.getStyleClass().add("contacts-search");
        
        // Contacts list
        ListView<ContactItem> contactsList = new ListView<>();
        contactsList.getStyleClass().add("contacts-listview");
        contactsList.getItems().addAll(
            new ContactItem("Alex Johnson", "Online", true),
            new ContactItem("Sara Williams", "In a meeting", false),
            new ContactItem("Mike Chen", "Away", false),
            new ContactItem("Lisa Garcia", "Online", false)
        );
        
        contacts.getChildren().addAll(searchContacts, contactsList);
        VBox.setVgrow(contactsList, Priority.ALWAYS);
        
        return contacts;
    }
    
    private BorderPane createChatArea() {
        BorderPane chatArea = new BorderPane();
        chatArea.getStyleClass().add("chat-area");
        
        // Chat header
        HBox chatHeader = new HBox();
        chatHeader.getStyleClass().add("chat-header");
        
        Circle profilePic = new Circle(20);
        profilePic.getStyleClass().add("contact-profile-pic");
        
        VBox contactInfo = new VBox();
        Label contactName = new Label("Alex Johnson");
        contactName.getStyleClass().add("contact-name");
        Label contactStatus = new Label("Online");
        contactStatus.getStyleClass().add("contact-status");
        contactInfo.getChildren().addAll(contactName, contactStatus);
        
        Button callButton = new Button("📞");
        Button videoButton = new Button("📹");
        Button infoButton = new Button("ℹ");
        
        HBox actions = new HBox(callButton, videoButton, infoButton);
        actions.getStyleClass().add("chat-actions");
        
        chatHeader.getChildren().addAll(profilePic, contactInfo, new Pane(), actions);
        HBox.setHgrow(contactInfo, Priority.ALWAYS);
        
        // Chat messages
        ScrollPane messagesScroll = new ScrollPane();
        messagesScroll.getStyleClass().add("messages-scroll");
        
        VBox messagesContainer = new VBox();
        messagesContainer.getStyleClass().add("messages-container");
        
        // Add sample messages
        messagesContainer.getChildren().addAll(
            createReceivedMessage("Hi there! How's the application design coming along?"),
            createSentMessage("Going well! Just working on the messaging component now."),
            createReceivedMessage("Great! Don't forget to add notification capabilities."),
            createSentMessage("Will do. I'm also adding theme customization options.")
        );
        
        messagesScroll.setContent(messagesContainer);
        messagesScroll.setFitToWidth(true);
        
        // Message input area
        HBox inputArea = new HBox();
        inputArea.getStyleClass().add("message-input-area");
        
        Button attachButton = new Button("📎");
        attachButton.getStyleClass().add("attach-button");
        
        TextField messageInput = new TextField();
        messageInput.setPromptText("Type a message...");
        messageInput.getStyleClass().add("message-input");
        
        Button sendButton = new Button("➤");
        sendButton.getStyleClass().add("send-button");
        
        inputArea.getChildren().addAll(attachButton, messageInput, sendButton);
        HBox.setHgrow(messageInput, Priority.ALWAYS);
        
        // Assemble chat area
        chatArea.setTop(chatHeader);
        chatArea.setCenter(messagesScroll);
        chatArea.setBottom(inputArea);
        
        return chatArea;
    }
    
    private HBox createReceivedMessage(String text) {
        HBox container = new HBox();
        container.getStyleClass().add("message-container");
        
        TextFlow message = new TextFlow(new Text(text));
        message.getStyleClass().addAll("message", "received-message");
        
        container.getChildren().add(message);
        return container;
    }
    
    private HBox createSentMessage(String text) {
        HBox container = new HBox();
        container.getStyleClass().add("message-container");
        container.setAlignment(Pos.CENTER_RIGHT);
        
        TextFlow message = new TextFlow(new Text(text));
        message.getStyleClass().addAll("message", "sent-message");
        
        container.getChildren().add(message);
        return container;
    }
    
    // Contact item class for list
    public static class ContactItem extends HBox {
        
        public ContactItem(String name, String status, boolean active) {
            this.getStyleClass().add("contact-item");
            if (active) {
                this.getStyleClass().add("active-contact");
            }
            
            Circle avatar = new Circle(20);
            avatar.getStyleClass().add("contact-avatar");
            
            VBox info = new VBox();
            Label contactName = new Label(name);
            contactName.getStyleClass().add("contact-list-name");
            
            Label statusLabel = new Label(status);
            statusLabel.getStyleClass().add("contact-list-status");
            
            info.getChildren().addAll(contactName, statusLabel);
            
            Circle statusIndicator = new Circle(5);
            statusIndicator.getStyleClass().add(
                status.toLowerCase().contains("online") ? "online-indicator" : "offline-indicator"
            );
            
            this.getChildren().addAll(avatar, info, new Pane(), statusIndicator);
            HBox.setHgrow(info, Priority.ALWAYS);
            setAlignment(Pos.CENTER_LEFT);
        }
    }
}