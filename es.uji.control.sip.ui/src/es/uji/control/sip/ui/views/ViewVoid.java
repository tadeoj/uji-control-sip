package es.uji.control.sip.ui.views;

import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.ui.swt.views.SubModuleView;
import org.eclipse.riena.ui.swt.lnf.LnfKeyConstants;
import org.eclipse.riena.ui.swt.lnf.LnfManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import es.uji.control.sip.ui.controllers.ViewVoidController;

public class ViewVoid extends SubModuleView {

	public static final String ID = ViewVoid.class.getName();
	
	private ViewVoidController viewVoidController;

	@Override
	public void setFocus() {
	}

	@Override
	protected void basicCreatePartControl(Composite parent) {
		parent.setBackground(LnfManager.getLnf().getColor(LnfKeyConstants.SUB_MODULE_BACKGROUND));
		parent.setLayout(new GridLayout(1, false));
	}
	
	@Override
	protected ViewVoidController createController(ISubModuleNode subModuleNode) {	
		viewVoidController = new ViewVoidController(subModuleNode);
		return viewVoidController;
	}
	
	@Override
	public void dispose() {
	}	
}
