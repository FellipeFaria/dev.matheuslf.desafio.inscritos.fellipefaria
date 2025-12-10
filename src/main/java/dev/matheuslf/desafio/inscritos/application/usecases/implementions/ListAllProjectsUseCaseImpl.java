package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import java.util.List;

import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;

public class ListAllProjectsUseCaseImpl implements BaseUseCase<Void, List<Project>> {
    private final ProjectGateway gateway;

    public ListAllProjectsUseCaseImpl(ProjectGateway projectGateway) {
        this.gateway = projectGateway;
    }

    @Override
    public List<Project> execute(Void input) {
        return this.gateway.listAllProjects();
    }

}
