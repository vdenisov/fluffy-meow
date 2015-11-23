package org.plukh.fluffymeow.ui.login;

import com.ejt.vaadin.loginform.LoginForm;
import com.google.common.eventbus.EventBus;
import com.vaadin.ui.*;
import org.vaadin.webinar.i18n.Messages;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;

public class HeaderLoginForm extends LoginForm implements Translatable {
    protected TextField textField;
    protected PasswordField passwordField;
    protected Button button;

    protected transient EventBus eventBus;

    @Inject
    public HeaderLoginForm(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
    }

    @Override
    protected Component createContent(TextField textField, PasswordField passwordField, Button button) {
        this.textField = textField;
        this.passwordField = passwordField;
        this.button = button;

        HorizontalLayout layout = new HorizontalLayout();

        layout.setSpacing(true);

        layout.addComponent(textField);
        layout.addComponent(passwordField);
        layout.addComponent(button);

        layout.setComponentAlignment(button, Alignment.BOTTOM_LEFT);

        return layout;
    }

    @Override
    public void updateMessageStrings() {
        Messages messages = Messages.getInstance();

        textField.setCaption(messages.getMessage("header.login.emaillabel"));
        passwordField.setCaption(messages.getMessage("header.login.passwordlabel"));
        button.setCaption(messages.getMessage("header.login.loginbutton"));
    }

    @Override
    protected void login(String userName, String password) {
        super.login(userName, password);

        //TODO: proper login (with error notification) here
        eventBus.post(new LoginEventImpl());
    }
}
