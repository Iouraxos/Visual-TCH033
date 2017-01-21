package topic;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import core.Ressource;
import electrical.Complex;
import electrical.Electrical;

public class HighPassRCFilter extends AbstractRCFilter implements SelectionListener{

	public HighPassRCFilter(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void loadImage() {
		
		Ressource ressource = Ressource.getIntanceOf();
		
		schematicNoLoad = ressource.getFilterImage(Ressource.TYPE_HIGH_PASS_RC, false);
		schematiWithLoad = ressource.getFilterImage(Ressource.TYPE_HIGH_PASS_RC, true);
	}
	
	@Override
	protected void recalculate(double f) {
		
		Complex zc;
		Complex zr;
		Complex zrl;
		
		fc = 1. / (2 * Math.PI * r * c);
		
		xc = Electrical.xc(f, c);
		
		zc = Complex.getComplexPol(xc, -90.);
		zr = Complex.getComplexPol(r, 0.);
		zrl = Complex.getComplexPol(rl, 0.);
		
		z2 = Electrical.zParallel(zr, zrl);
		zt = Electrical.zSeries(zc, z2);
		
		a = Complex.div(z2, zt);
		phi = a.angle();
		adb = Electrical.db(a.abs());
		
	}	
}
