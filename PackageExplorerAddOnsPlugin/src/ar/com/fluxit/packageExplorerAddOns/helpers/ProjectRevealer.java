package ar.com.fluxit.packageExplorerAddOns.helpers;

import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.DialogUtil;

import ar.com.fluxit.packageExplorerAddOns.dialogs.PackageExplorerFinderDialog;

@SuppressWarnings("restriction")
public class ProjectRevealer {

	private static class Holder {

		private static ProjectRevealer instance = new ProjectRevealer();

	}

	public static ProjectRevealer getInstance() {
		return Holder.instance;
	}

	private ProjectRevealer() {
	}

	public void selectAndReveal(Object selectedProject) {
		final IWorkbenchWindow dw = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		final IWorkbenchPage page = dw.getActivePage();
		try {
			final IPackagesViewPart view = (IPackagesViewPart) page
					.showView(PackageExplorerFinderDialog.PACKAGE_EXPLORER_VIEW_ID);
			view.getTreeViewer().collapseAll();
			view.selectAndReveal(selectedProject);
			view.getTreeViewer().setExpandedState(selectedProject, true);
		} catch (final PartInitException e) {
			DialogUtil
					.openError(
							dw.getShell(),
							"Error",
							"No se puede ubicar el proyecto en la vista de paquetes",
							e);
		}
	}

}
