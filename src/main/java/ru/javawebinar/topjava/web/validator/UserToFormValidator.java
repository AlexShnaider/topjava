package ru.javawebinar.topjava.web.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class UserToFormValidator implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass);
    }

    @Override
    public void validate(Object oUser, Errors errors) {
        UserTo user = (UserTo) oUser;

        try {
            User existedUser = service.getByEmail(user.getEmail());
            if (!existedUser.getId().equals(user.getId())) {
                errors.rejectValue("email", "", "Such email already exist");
            }
        } catch (NotFoundException e) {
        }
    }
}
