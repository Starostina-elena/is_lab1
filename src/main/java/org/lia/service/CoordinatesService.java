package org.lia.service;

import org.lia.models.utils.Coordinates;
import org.lia.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CoordinatesService {
    @Autowired
    private CoordinatesRepository coordinatesRepository;

    public void saveCoordinates(Integer x, Long y) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        coordinatesRepository.save(coordinates);
    }
}
