package dev.matheuslf.desafio.inscritos.application.usecases.interfaces;

public interface BaseUseCase<I, O> {
    O execute(I input);
}
