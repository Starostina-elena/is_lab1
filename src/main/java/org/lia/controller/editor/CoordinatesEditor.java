package org.lia.controller.editor;

import org.lia.models.utils.Coordinates;
import org.lia.service.CoordinatesService;
import java.beans.PropertyEditorSupport;

public class CoordinatesEditor extends PropertyEditorSupport {
    private final CoordinatesService coordinatesService;

    public CoordinatesEditor(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            Long id = Long.valueOf(text);
            Coordinates coordinates = coordinatesService.findById(id);
            setValue(coordinates);
        }
    }
}


