package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TasckControllerTest {
	@Mock
	private TaskRepo taskRepo;

	@InjectMocks
	private TaskController controller;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void naoDeveSalvarTarefaSemDescrição() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setDueDate(null);
		todo.setTask("tteste");
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		todo.setTask("teste1");
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setDueDate(LocalDate.of(2030, 01, 01));
		todo.setTask("teste1");
		controller.save(todo);

		Mockito.verify(taskRepo).save(todo);

	}
}
