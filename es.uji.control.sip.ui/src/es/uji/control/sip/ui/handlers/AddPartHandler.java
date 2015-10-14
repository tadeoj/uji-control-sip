package es.uji.control.sip.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;

public class AddPartHandler {

	private final static String ID_STACK = "es.uji.control.sip.ui.partStack.id";
	private final static String ID_WINDOW = "es.uji.control.sip.ui.window.id";
	private final static String ID_PART_PREFIX = "es.uji.control.sip.ui.partStack.id";

	private int partCnt = 0;

	@Execute
	public void execute(EModelService modelService, MApplication app, EPartService partService) {

		MPartStack stack = (MPartStack) modelService.find(ID_STACK, app);
		if (stack == null) {
			stack = modelService.createModelElement(MPartStack.class);
			stack.setElementId(ID_STACK);

			MWindow window = (MWindow) modelService.find(ID_WINDOW, app);
			if (window == null) {
				window = modelService.createModelElement(MTrimmedWindow.class);
				window.setElementId(ID_WINDOW);
				window.setLabel("Ventana");
				window.getPersistedState().put(IPresentationEngine.STYLE_OVERRIDE_KEY, "" + SWT.SHELL_TRIM);

				app.getChildren().add(window);
			}

			window.getChildren().add(0, stack);
		}
		partCnt++;
		MPart part = modelService.createModelElement(MPart.class);
		part.setElementId(ID_PART_PREFIX + partCnt);
		part.setContributionURI("bundleclass://es.uji.control.sip.ui/es.uji.control.sip.ui.parts.UsersPart");
		part.setCloseable(true);
		part.setLabel("Part " + partCnt);
		part.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);

		stack.getChildren().add(part);
		partService.showPart(part, PartState.ACTIVATE);

	}
}
