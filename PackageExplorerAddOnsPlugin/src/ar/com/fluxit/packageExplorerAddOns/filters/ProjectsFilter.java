package ar.com.fluxit.packageExplorerAddOns.filters;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IWorkingSet;

/**
 * Filtro de proyectos
 * 
 * @author jbaris
 */
@SuppressWarnings("restriction")
public class ProjectsFilter extends ViewerFilter {

	private boolean onlyJavaProjects = false;

	public boolean isOnlyJavaProjects() {
		return onlyJavaProjects;
	}

	public boolean isProject(Object element) {
		if (element instanceof IJavaProject) {
			return true;
		} else {
			if (element instanceof Project) {
				return !isOnlyJavaProjects();
			} else {
				return false;
			}
		}
	}

	public boolean isWorkingSet(Object element) {
		if (element instanceof IWorkingSet) {
			return true;
		}
		return false;
	}

	public boolean select(Object element) {
		return isWorkingSet(element) || isProject(element);
	}

	/**
	 * @return
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return select(element);
	}

	public void setOnlyJavaProjects(boolean onlyJavaProjects) {
		this.onlyJavaProjects = onlyJavaProjects;
	}

}
