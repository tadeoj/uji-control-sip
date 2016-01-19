package es.uji.control.sip.ui;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class CloseAddon implements IWindowCloseHandler {

	@Inject
	@Optional
	public void startupComplete(@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) MApplication application, EModelService modelService) {
		MWindow window = (MWindow) modelService.find("es.uji.control.sip.ui.window.id", application);
		window.getContext().set(IWindowCloseHandler.class, this);
	}

	@Override
	public boolean close(MWindow window) {

		if (MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Confirmación", "Esta seguro que quiere cerrar el programa.")) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
