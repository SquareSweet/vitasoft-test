package me.sqsw.vitasofttest.repository;

import me.sqsw.vitasofttest.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}