package org.plukh.fluffymeow.ui.login;

import com.ejt.vaadin.loginform.LoginForm;
import com.google.common.eventbus.EventBus;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;
import org.vaadin.webinar.i18n.Messages;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;

public class HeaderLoginForm extends LoginForm implements Translatable {
    private static final Logger log = LogManager.getLogger(HeaderLoginForm.class);

    protected TextField textField;
    protected PasswordField passwordField;
    protected Button button;
    protected Button registerLink;

    protected transient EventBus eventBus;

    @Inject
    public HeaderLoginForm(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
    }

    @Override
    protected Component createContent(TextField textField, PasswordField passwordField, Button button) {
        log.trace("Creating content of HeaderLoginForm...");

        this.textField = textField;
        this.passwordField = passwordField;
        this.button = button;

        registerLink = new Button();
        registerLink.addStyleName(ValoTheme.BUTTON_LINK);
        registerLink.addClickListener(event -> UI.getCurrent().getNavigator().navigateTo(UserRegistrationView.VIEW_NAME));

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        //layout.setWidth("100%");

        layout.addComponent(textField);
        layout.addComponent(passwordField);
        layout.addComponent(button);
        layout.addComponent(registerLink);

        layout.setComponentAlignment(button, Alignment.BOTTOM_LEFT);
        layout.setComponentAlignment(registerLink, Alignment.BOTTOM_LEFT);

        updateMessageStrings();

        log.debug("Content of HeaderLoginForm created");

        return layout;
    }

    @Override
    public void updateMessageStrings() {
        Messages messages = Messages.getInstance();

        textField.setCaption(messages.getMessage("header.login.emaillabel"));
        passwordField.setCaption(messages.getMessage("header.login.passwordlabel"));
        button.setCaption(messages.getMessage("header.login.loginbutton"));
        registerLink.setCaption(messages.getMessage("header.login.registerlink"));
    }

    @Override
    protected void login(String userName, String password) {
        super.login(userName, password);

        //TODO: proper login (with error notification) here
        eventBus.post(new LoginEventImpl());
    }
}
