package ar.com.fluxit.packageExplorerAddOns.jdt.extras;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Refresh Action
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class RefreshAction extends Action implements IViewActionDelegate {

	private org.eclipse.jdt.ui.actions.RefreshAction refreshAction;

	@Override
	public void init(IViewPart arg0) {
		refreshAction = new org.eclipse.jdt.ui.actions.RefreshAction(arg0
				.getSite());
	}

	@Override
	public void run(IAction arg0) {
		refreshAction.run();
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		refreshAction.selectionChanged(arg1);
	}

}
