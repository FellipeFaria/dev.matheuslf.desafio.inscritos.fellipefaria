package dev.matheuslf.desafio.inscritos.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long>,
                                              JpaSpecificationExecutor<TaskEntity> {
}
