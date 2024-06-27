package org.hmcampoverde.service;

import org.hmcampoverde.entity.Rol;

public interface RolService {

    public Rol findByName(String name);

    public Rol getReferenceById(Long id);
}
