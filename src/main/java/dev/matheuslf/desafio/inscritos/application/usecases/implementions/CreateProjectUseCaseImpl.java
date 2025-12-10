package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;

public class CreateProjectUseCaseImpl implements BaseUseCase<Project, Project> {
    private final ProjectGateway gateway;

    public CreateProjectUseCaseImpl(ProjectGateway projectGateway) {
        this.gateway = projectGateway;
    }

    @Override
    public Project execute(Project project) {
        return this.gateway.save(project);
    }
    
}
