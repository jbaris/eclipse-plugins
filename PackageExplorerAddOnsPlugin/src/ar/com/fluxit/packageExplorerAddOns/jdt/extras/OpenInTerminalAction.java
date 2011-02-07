package ar.com.fluxit.packageExplorerAddOns.jdt.extras;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import ar.com.fluxit.packageExplorerAddOns.terminal.Terminal;

/**
 * Open in terminal Action
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class OpenInTerminalAction extends Action implements IViewActionDelegate {

	private ISelection selection;
	private IViewPart viewPart;

	@Override
	public void init(IViewPart arg0) {
		viewPart = arg0;
	}

	@Override
	public void run(IAction arg0) {
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			for (final Object object : structuredSelection.toList()) {
				Terminal.openInTerminal(viewPart.getSite().getShell(), object);
			}
		}
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		selection = arg1;
	}

}
