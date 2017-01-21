package topic;

import org.eclipse.swt.widgets.Composite;

import core.Ressource;
import electrical.Complex;
import electrical.Electrical;

public class LowPassRCFilter extends AbstractRCFilter{

	public LowPassRCFilter(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void loadImage() {
		
		Ressource ressource = Ressource.getIntanceOf();
		
		schematicNoLoad = ressource.getFilterImage(Ressource.TYPE_LOW_PASS_RC, false);
		schematiWithLoad = ressource.getFilterImage(Ressource.TYPE_LOW_PASS_RC, true);
	}
	
	@Override
	protected void recalculate(double f) {
		
		Complex zc;
		Complex zr;
		Complex zrl;		
		double rth;
		
		fcNoLoad = 1. / (2 * Math.PI * r * c);
		
		rth = 1 / (1 / r + 1 / rl);		
		fcWithLoad = 1. / (2 * Math.PI * rth * c);
		
		xc = Electrical.xc(f, c);
		
		zc = Electrical.zc(xc);
		zr = Electrical.zr(r);
		zrl = Electrical.zr(rl);
		
		z2 = Electrical.zParallel(zc, zrl);
		zt = Electrical.zSeries(zr, z2);
		
		a = Complex.div(z2, zt);
		phi = a.angle();
		adb = Electrical.db(a.abs());
		
	}
}
