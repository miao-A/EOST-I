package seu.EOSTI.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class CompatibilityComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblCompatibilityComposite = new Label(this, SWT.NONE);
		lblCompatibilityComposite.setBounds(46, 55, 150, 17);
		lblCompatibilityComposite.setText("Compatibility Composite");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
