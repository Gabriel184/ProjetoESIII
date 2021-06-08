package br.com.ssp.ematricula.controller.command;

import br.com.ssp.ematricula.controller.facade.Facade;
import br.com.ssp.ematricula.controller.facade.IFacade;

public abstract class AbstractCommand implements ICommand {
	protected IFacade fachada = new Facade();
}
