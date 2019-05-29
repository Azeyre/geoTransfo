package forme;

import java.util.Arrays;

import transforms.Composition;
import transforms.IComposition;
import transforms.mobile.MotifConcret;

public class Carre extends MotifConcret {
	
	private static final double X0 = 0.0;
    private static final double X1 = 1.0;
    private static final double Y0 = 0.0;
    private static final double Y1 = 1.0;


	public Carre(IComposition composition) {
		super(
                (Composition) composition,
                Arrays.asList(
                        X0, Y0,
                        X0, Y1,
                        X1, Y1,
                        X1, Y0,
                        X0, Y0
                )
        );
	}

}
