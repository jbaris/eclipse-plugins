package ar.com.fluxit.packageExplorerAddOns.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * PatternFilter de proyectos
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class ProjectsPatternFilter extends PatternFilter {

	private final ProjectsFilter filter;

	public ProjectsPatternFilter(ProjectsFilter filter) {
		this.filter = filter;
	}

	private String getName(Object element) {
		if (element instanceof IJavaProject) {
			return ((IJavaProject) element).getElementName();
		}
		if (element instanceof IProject) {
			return ((IProject) element).getName();
		}
		return "";
	}

	@Override
	public boolean isElementSelectable(Object element) {
		return filter.isProject(element);
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		if (filter.isProject(element)) {
			return wordMatches(getName(element));
		}
		return false;
	}

	@Override
	protected boolean isParentMatch(Viewer viewer, Object element) {
		if (filter.select(element)) {
			return super.isParentMatch(viewer, element);
		}
		return false;
	}

}
