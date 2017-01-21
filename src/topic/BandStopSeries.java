package topic;

import org.eclipse.swt.widgets.Composite;

import core.Ressource;
import electrical.Complex;
import electrical.Electrical;

public class BandStopSeries extends AbstractRLCFilter {

	public BandStopSeries(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void loadImage() {
		
		Ressource ressource = Ressource.getIntanceOf();
		
		schematicNoLoad = ressource.getFilterImage(Ressource.TYPE_BAND_PASS_PAR, false);
		schematiWithLoad = ressource.getFilterImage(Ressource.TYPE_BAND_PASS_PAR, true);
	}

	@Override
	protected void setInitialValues() {
		r = 560.e0;
		c = 100.e-9;
		l = 330.e-3;
	}

	@Override
	protected void recalculate(double f) {
		
		Complex zc;
		Complex zr;
		Complex zl;
		Complex z1;
		
		xc = Electrical.xc(f, c);
		xl = Electrical.xl(f, l);
		
		zc = Electrical.zc(xc);
		zl = Electrical.zl(xl);
		zr = Electrical.zr(r);
		
		z1 = zr;
		z2 = Electrical.zSeries(zc, zl);
		
		zt = Electrical.zSeries(z1, z2);
		
		a = Complex.div(z2, zt);
		phi = a.angle();
		adb = Electrical.db(a.abs());
	}
}
