package org.lia.controller.editor;

import org.lia.models.person.Person;
import org.lia.service.PersonService;
import java.beans.PropertyEditorSupport;

public class PersonEditor extends PropertyEditorSupport {
    private final PersonService personService;

    public PersonEditor(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            Long id = Long.valueOf(text);
            Person person = personService.findById(id);
            setValue(person);
        }
    }
}

