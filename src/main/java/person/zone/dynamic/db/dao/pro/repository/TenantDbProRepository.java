package person.zone.dynamic.db.dao.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import person.zone.dynamic.db.dao.pro.entity.TenantDbPro;

@Repository
public interface TenantDbProRepository extends JpaRepository<TenantDbPro, Integer> {
}
