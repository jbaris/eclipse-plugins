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
 * @author Juan Barisich (<a
 *         href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 * @author http://code.google.com/p/eclipse-openinterminal
 */
public class FileOpener {

	public enum OpenerType {

		NAUTILUS("%Open_Command_Nautilus"), TERMINAL("%Open_Command_Terminal");

		private String command;

		OpenerType(String command) {
			this.command = command;
		}

		String getCommand() {
			return this.command;
		}
	}

	private static String[] getCommand(IPath path, String string) {
		String[] result = Platform.getResourceString(
				Activator.getDefault().getBundle(), string).split(" ");
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

	private static void open(Shell shell, IFile file, OpenerType nautilus) {
		try {
			Runtime.getRuntime().exec(
					getCommand(file.getLocation().removeLastSegments(1),
							nautilus.getCommand()));
		} catch (final IOException e) {
			showDialog(shell, "Could not open terminal: " + e.getMessage());
		}
	}

	private static void open(Shell shell, IPath path, OpenerType nautilus) {
		try {
			Runtime.getRuntime().exec(getCommand(path, nautilus.getCommand()));
		} catch (final IOException e) {
			showDialog(shell, "Could not open terminal: " + e.getMessage());
		}
	}

	public static void open(Shell shell, Object object, OpenerType nautilus) {
		if (object instanceof IFile) {
			// Open the file
			FileOpener.open(shell, (IFile) object, nautilus);
		} else if (object instanceof IContainer) {
			// Open the container
			FileOpener.open(shell, ((IContainer) object).getLocation(), nautilus);
		} else if (object instanceof IProjectNature) {
			// Open the project folder
			FileOpener.open(shell, ((IProjectNature) object).getProject()
					.getLocation(), nautilus);
		} else if (object instanceof IJavaElement) {
			// Get the full path to the Java element
			final IJavaElement javaElement = (IJavaElement) object;
			IPath fullPath = javaElement.getJavaProject().getProject()
					.getLocation()
					.append(javaElement.getPath().removeFirstSegments(1));

			// Trim the path if this is not a container
			if (object instanceof ICompilationUnit) {
				fullPath = fullPath.removeLastSegments(1);
			}
			// Open it
			FileOpener.open(shell, fullPath, nautilus);
		} else {
			// No applicatble selection
			showDialog(shell,
					"No applicable resource selected: " + object.getClass());
		}
	}

	private static void showDialog(Shell shell, String message) {
		MessageDialog.openInformation(shell, "OpenInTerminal Plug-in", message);
	}

}
