package electrical;

public class Electrical {
	
	public final static String UNIT_AMPERE = "A";
	public final static String UNIT_DECIBEL = "dB";
	public final static String UNIT_FARAD = "F";
	public final static String UNIT_HERTZ = "Hz";
	//public final static String UNIT_OHM = "\u03a9";
	public final static String UNIT_OHM = "\u2126";
	public final static String UNIT_VOLT = "V";
	public final static String UNIT_DEGREE = "\u00b0";
	public final static String UNIT_HENRY = "H";

	public static double db(double a){
		
		return 20. * Math.log10(a);
	}
	
	public static double xc(double f, double c){
		
		return 1. / (2. * Math.PI * f * c);
	}
	
	public static double xl(double f, double l){
		
		return 2. * Math.PI * f * l;
	}
	
	public static double fr(double l, double c){
		
		return 1. / (2 * Math.PI * Math.sqrt(l * c));
	}
	
	public static double xAtFr(double l, double c){
		
		return Math.sqrt(l / c);
	}
	
	public static String formatPrecision(double value, int significantDigits){
		
		double absValue = Math.abs(value);
		
		String formatString = "%.2f";
			
		if(absValue >= 100.){
			
			formatString = "%.0f";
		}
		
		else if(absValue >= 10. && absValue < 100.){
			
			formatString = "%.1f";
		}
		
		else if(absValue >= 0. && absValue < 10.){
			
			formatString = "%.2f";
		}
		
		return String.format(formatString, value);
	}
	
	public static String formatEngineer(double value, int significantDigits){
		
		final String[] BIG_SUFFIX_ARRAY = new String[5];{
			BIG_SUFFIX_ARRAY[0] = "";
			BIG_SUFFIX_ARRAY[1] = "k";
			BIG_SUFFIX_ARRAY[2] = "M";
			BIG_SUFFIX_ARRAY[3] = "G";
			BIG_SUFFIX_ARRAY[4] = "T";
		}
		
		final String[] SMALL_SUFFIX_ARRAY = new String[5];{
			SMALL_SUFFIX_ARRAY[0] = "";
			SMALL_SUFFIX_ARRAY[1] = "m";
			//SMALL_SUFFIX_ARRAY[2] = "\u03bc";
			SMALL_SUFFIX_ARRAY[2] = "\u00b5";
			SMALL_SUFFIX_ARRAY[3] = "n";
			SMALL_SUFFIX_ARRAY[4] = "p";
		}
		
		String suffix = "";
		String valueAsString;

		if(Double.isInfinite(value)){
			
			if(value > 0.){
				valueAsString = "+\u221e";
			}
			else{
				valueAsString = "-\u221e";
			}
		}
		
		else if(Double.isFinite(value)){
		
			if(value != 0){
		
				int suffixIndex = 0;			
				
				if(Math.abs(value) >= 1.){
					
					while(Math.abs(value) >= 1000. && suffixIndex < BIG_SUFFIX_ARRAY.length - 1){
						
						value = value / 1000;
						suffixIndex++;
					}
					
					suffix = BIG_SUFFIX_ARRAY[suffixIndex];
				}
				
				else if(Math.abs(value) < 1.){
					
					while(Math.abs(value) <= 0.999 && suffixIndex < SMALL_SUFFIX_ARRAY.length - 1){
						
						value = value * 1000;
						suffixIndex++;
					}
					
					suffix = SMALL_SUFFIX_ARRAY[suffixIndex];
				}				
			}
			
			valueAsString = formatPrecision(value, 3) + suffix;
		}
		
		else{
			
			System.err.println("Cas spécial non pris en charge (genre NaN et infinity) : " + value);
			valueAsString = "?";
		}
		
		return valueAsString;
	}
	
	public static String formatEngineer(Complex c, int significantDigits) {
		
		String magnitudeAsString;
		String angleAsString ;
		
		if(c == null){
			
			magnitudeAsString = "undef";
			angleAsString = "?";					
		}
		
		else{
			
			magnitudeAsString = formatEngineer(c.abs(), significantDigits);
			angleAsString = String.format("%03.1f", c.angle());			
		}
		
		return "( " + magnitudeAsString + " \u2220 " + angleAsString + "° )";
	}
	
	
	//Impedance Stuff
	
	public static Complex zr(double r) {
		return Complex.getComplexPol(r, 0.);
	}
	
	public static Complex zc(double xc) {
		return Complex.getComplexPol(xc, -90.);
	}
	
	public static Complex zl(double xl) {
		return Complex.getComplexPol(xl, 90.);
	}
	
	public static Complex zSeries(Complex z1, Complex z2) {		
		return Complex.add(z1, z2);
	}
	
	public static Complex zParallel(Complex z1, Complex z2) {
		
		Complex y1 = Complex.inv(z1);
		Complex y2 = Complex.inv(z2);
		
		Complex yt = Complex.add(y1, y2);
		
		Complex zt = Complex.inv(yt);
		
		return zt;
	}
	
	public static boolean isMultiple(double a, double b){
		
		return Math.abs(Math.round(a / b) - (a / b)) < 0.00001;
	}
	
	// C'est classe est prévu pour un usage statique
	private Electrical() {

	}
}
