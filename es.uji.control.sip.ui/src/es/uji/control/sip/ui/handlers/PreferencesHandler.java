package es.uji.control.sip.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import es.uji.control.sip.preferences.ContributedPreferenceNode;
import es.uji.control.sip.preferences.PrefMgr;

public class PreferencesHandler {

	@Execute
	public void execute(Shell shell, @PrefMgr PreferenceManager manager) {
		PreferenceDialog dialog = new PreferenceDialog(shell, manager) {

			@Override
			protected TreeViewer createTreeViewer(Composite parent) {
				TreeViewer viewer = super.createTreeViewer(parent);

				viewer.setComparator(new ViewerComparator() {

					@Override
					public int category(Object element) {
						if (element instanceof ContributedPreferenceNode && ("general".equals(((ContributedPreferenceNode) element).getId()))) {
							return -1;
						}
						return 0;
					}
				});

				return viewer;
			}
		};

		dialog.open();
	}
}
