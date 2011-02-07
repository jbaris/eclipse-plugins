package ar.com.fluxit.packageExplorerAddOns.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.dialogs.DialogUtil;

import ar.com.fluxit.packageExplorerAddOns.dialogs.PackageExplorerFinderDialog;

/**
 * Handler para package explorer finder
 * 
 * @author jbaris
 */
@SuppressWarnings("restriction")
public class OpenPackageExplorerFinderHandler extends AbstractHandler {

	@Override
	public void dispose() {

	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		final IWorkbenchWindow dw = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		final IWorkbenchPage page = dw.getActivePage();

		IPackagesViewPart view;
		try {
			view = (IPackagesViewPart) page
					.showView(PackageExplorerFinderDialog.PACKAGE_EXPLORER_VIEW_ID);

			final PackageExplorerFinderDialog dialog = new PackageExplorerFinderDialog(
					Workbench.getInstance().getActiveWorkbenchWindow().getShell());
			dialog.open(view.getTreeViewer().getInput());

		} catch (final PartInitException e) {
			DialogUtil.openError(dw.getShell(), "Error",
					"La accion no esta disponible", e);
		}
		return null;
	}
}
