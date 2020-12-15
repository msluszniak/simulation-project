package agh.cs.project;

import agh.cs.project.basics.Genotype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.stream.Collectors;


public class GenotypeTest {
    @Test
    public void isGoodLength0ArgumentConstructor(){
        Genotype exampleGenotype = new Genotype();
        Assertions.assertEquals(32, exampleGenotype.getGenotype().size());
    }

    @Test
    public void isGoodLengthGenotypeOfBabyAnimal(){
        Genotype exampleGenotype = new Genotype();
        Assertions.assertEquals(32, exampleGenotype.mixGenotypes(new Genotype()).getGenotype().size());
    }

    @Test
    public void isSortedGenotype0ArgumentConstructor(){
        Genotype exampleGenotype = new Genotype();
        boolean isSorted = exampleGenotype.getGenotype().stream().sorted().collect(Collectors.toList()).equals(exampleGenotype.getGenotype());
        Assertions.assertTrue(isSorted);
    }



}
