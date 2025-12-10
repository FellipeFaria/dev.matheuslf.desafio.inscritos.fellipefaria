package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;

public class FindProjectByIdUseCaseImpl implements BaseUseCase<Long, Project> {
    private final ProjectGateway gateway;

    public FindProjectByIdUseCaseImpl(ProjectGateway projectGateway) {
        this.gateway = projectGateway;
    }

    @Override
    public Project execute(Long id) {
        var existent = this.gateway.findProjectById(id);

        if (existent == null) {
            throw new ProjectNotFoundException();
        }

        return existent;
    }
    
}
