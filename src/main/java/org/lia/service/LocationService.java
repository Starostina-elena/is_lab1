package org.lia.service;

import org.lia.models.utils.Location;
import org.lia.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location saveLocation(Double x, Float y, Float z, String name) {
        Location location = new Location();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        location.setName(name);
        locationRepository.save(location);
        return location;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Page<Location> getLocationsPaged(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public long count() {
        return locationRepository.count();
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    public Page<Location> getLocationsPagedAndFiltered(Pageable pageable, String filter) {
        Specification<Location> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%"));
        }
        return locationRepository.findAll(spec, pageable);
    }

    public long countFiltered(String filter) {
        Specification<Location> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%"));
        }
        return locationRepository.count(spec);
    }

    public Iterable<Location> findLocationsByNameSubstring(String search) {
        return locationRepository.findLocationsByNameSubstring(search);
    }
}
