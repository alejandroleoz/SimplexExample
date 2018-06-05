package simplex;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class ApacheTest {

    @Test
    public void simplex() {

        // https://www.metodosimplex.com/ejemplo-del-metodo-simplex/

        // Forma de la ecuación: c1*x1 + c2*x2 + d = Z
        // El problema: 4*x1 + 3*x2 = Z

        // coeficientes del problema
        double c1 = 4;
        double c2 = 3;
        double[] coeficientes = {
                c1,
                c2
        };

        // La funcion a resolver
        LinearObjectiveFunction funcion = new LinearObjectiveFunction(coeficientes, 0);

        // restricciones de las variables
        Collection<LinearConstraint> restricciones = new ArrayList<LinearConstraint>();

        // x1 <= 800
        restricciones.add(new LinearConstraint(new double[]{1, 0}, Relationship.LEQ, 800));

        // x2 <= 700
        restricciones.add(new LinearConstraint(new double[]{0, 1}, Relationship.LEQ, 700));

        // x1 + x2 <= 800
        restricciones.add(new LinearConstraint(new double[]{1, 1}, Relationship.LEQ, 800));

        // 2*x1 + x2 <= 1000
        restricciones.add(new LinearConstraint(new double[]{2, 1}, Relationship.LEQ, 1000));

        SimplexSolver solver = new SimplexSolver();
        PointValuePair optSolution = solver.optimize(
                new MaxIter(100),
                funcion,
                new LinearConstraintSet(restricciones),
                GoalType.MAXIMIZE,
                new NonNegativeConstraint(true)
        );

        System.out.println("x1=" + optSolution.getPoint()[0]);
        System.out.println("x2=" + optSolution.getPoint()[1]);
        System.out.println("Z=" + optSolution.getValue());
    }
}
