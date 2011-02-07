package ar.com.fluxit.packageExplorerAddOns.terminal;

import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import ar.com.fluxit.packageExplorerAddOns.Activator;

/**
 * Open in Terminal Helper
 * 
 * @author jbaris
 * @author http://code.google.com/p/eclipse-openinterminal
 */
public class Terminal {

	private static String[] getCommand(IPath path) {
		String[] result = Platform.getResourceString(
				Activator.getDefault().getBundle(), "%Open_Command").split(" ");
		int i = searchPathIndex(result);
		result[i] = result[i].replace("%%", path.toOSString());
		return result;
	}

	private static int searchPathIndex(String[] command) {
		for (int i = 0; i < command.length; i++) {
			if (command[i].contains("%%"))
				return i;
		}
		return -1;
	}

	public static void openInTerminal(Shell shell, IFile file) {
		try {
			Runtime.getRuntime().exec(
					getCommand(file.getLocation().removeLastSegments(1)));
		} catch (final IOException e) {
			showDialog(shell, "Could not open terminal: " + e.getMessage());
		}
	}

	public static void openInTerminal(Shell shell, IPath path) {
		try {
			Runtime.getRuntime().exec(getCommand(path));
		} catch (final IOException e) {
			showDialog(shell, "Could not open terminal: " + e.getMessage());
		}
	}

	public static void openInTerminal(Shell shell, Object object) {
		if (object instanceof IFile) {
			// Open the file
			Terminal.openInTerminal(shell, (IFile) object);
		} else if (object instanceof IContainer) {
			// Open the container
			Terminal.openInTerminal(shell, ((IContainer) object).getLocation());
		} else if (object instanceof IProjectNature) {
			// Open the project folder
			Terminal.openInTerminal(shell, ((IProjectNature) object)
					.getProject().getLocation());
		} else if (object instanceof IJavaElement) {
			// Get the full path to the Java element
			final IJavaElement javaElement = (IJavaElement) object;
			IPath fullPath = javaElement.getJavaProject().getProject()
					.getLocation().append(
							javaElement.getPath().removeFirstSegments(1));

			// Trim the path if this is not a container
			if (object instanceof ICompilationUnit) {
				fullPath = fullPath.removeLastSegments(1);
			}

			// Open it
			Terminal.openInTerminal(shell, fullPath);
		} else {
			// No applicatble selection
			showDialog(shell, "No applicable resource selected: "
					+ object.getClass());
		}
	}

	public static void showDialog(Shell shell, String message) {
		MessageDialog.openInformation(shell, "OpenInTerminal Plug-in", message);
	}

}
