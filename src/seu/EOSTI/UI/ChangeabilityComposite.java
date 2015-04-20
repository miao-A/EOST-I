package seu.EOSTI.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class ChangeabilityComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ChangeabilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblChagneabilityComposite = new Label(this, SWT.NONE);
		lblChagneabilityComposite.setBounds(24, 39, 162, 28);
		lblChagneabilityComposite.setText("Chagneability Composite");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
