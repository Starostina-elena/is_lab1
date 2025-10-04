package org.lia.controller.editor;

import org.lia.models.utils.Location;
import org.lia.service.LocationService;
import java.beans.PropertyEditorSupport;

public class LocationEditor extends PropertyEditorSupport {
    private final LocationService locationService;

    public LocationEditor(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            Long id = Long.valueOf(text);
            Location location = locationService.findById(id);
            setValue(location);
        }
    }
}

