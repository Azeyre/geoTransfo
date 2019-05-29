package forme;

import java.util.Arrays;

import transforms.Composition;
import transforms.IComposition;
import transforms.mobile.MotifConcret;

public class Triangle extends MotifConcret {
	
	private static final double X0 = 0.0;
	private static final double X1 = 0.5;
    private static final double X2 = 1.0;
    private static final double Y0 = 0.0;
    private static final double Y1 = 1.0;


	public Triangle(IComposition composition) {
		super(
                (Composition) composition,
                Arrays.asList(
                        X0, Y0,
                        X1, Y1,
                        X2, Y0,
                        X0, Y0
                )
        );
	}

}
