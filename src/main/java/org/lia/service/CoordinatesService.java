package org.lia.service;

import org.lia.models.utils.Coordinates;
import org.lia.repository.CoordinatesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    public CoordinatesService(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    public Coordinates saveCoordinates(Coordinates coordinates) {
        return coordinatesRepository.save(coordinates);
    }

    public Page<Coordinates> getCoordinatesPaged(Pageable pageable) {
        return coordinatesRepository.findAll(pageable);
    }

    public long count() {
        return coordinatesRepository.count();
    }

    public Coordinates findById(Long id) {
        return coordinatesRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        coordinatesRepository.deleteById(id);
    }

    public Iterable<Coordinates> findAll() {
        return coordinatesRepository.findAll();
    }
}
