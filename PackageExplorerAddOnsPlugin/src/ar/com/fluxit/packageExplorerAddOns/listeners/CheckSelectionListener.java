package ar.com.fluxit.packageExplorerAddOns.listeners;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredTree;

import ar.com.fluxit.packageExplorerAddOns.filters.ProjectsFilter;
/**
 * Check listener
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class CheckSelectionListener extends SelectionAdapter {

	private final ProjectsFilter projectsFilter;
	private final FilteredTree filteredTree;

	public CheckSelectionListener(ProjectsFilter projectsFilter,
			FilteredTree filteredTree) {
		this.filteredTree = filteredTree;
		this.projectsFilter = projectsFilter;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		projectsFilter.setOnlyJavaProjects(((Button) e.widget).getSelection());
		// fuerza a que se actualice el filtro
		final Text filterControl = filteredTree.getFilterControl();
		filterControl.setText(filterControl.getText());
	}

}
