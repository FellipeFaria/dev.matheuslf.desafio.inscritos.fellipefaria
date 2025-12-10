package dev.matheuslf.desafio.inscritos.infra.service;

import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.infra.mappers.ProjectMapper;
import dev.matheuslf.desafio.inscritos.infra.persistence.ProjectEntity;
import dev.matheuslf.desafio.inscritos.infra.persistence.ProjectEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService implements ProjectGateway {
    private final ProjectEntityRepository repository;
    private final ProjectMapper mapper;

    public ProjectService(ProjectEntityRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity projectEntity = this.mapper.toEntity(project);

        var savedProject = this.repository.save(projectEntity);

        return this.mapper.toDomain(savedProject);
    }

    @Override
    public List<Project> listAllProjects() {
        var projects = this.repository.findAll();

        return projects.stream().map(this.mapper::toDomain).toList();
    }

    @Override
    public Project findProjectById(Long id) {
        var project = this.repository.findById(id).orElse(null);

        return this.mapper.toDomain(project);
    }
}
