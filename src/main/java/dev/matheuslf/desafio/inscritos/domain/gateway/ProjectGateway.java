package dev.matheuslf.desafio.inscritos.domain.gateway;

import java.util.List;

import dev.matheuslf.desafio.inscritos.domain.model.Project;

public interface ProjectGateway {
    Project save(Project project);
    List<Project> listAllProjects();
    Project findProjectById(Long id);
}
