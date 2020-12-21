package agh.cs.project;

import agh.cs.project.basics.Genotype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
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
    public void isGoodLengthGenotype1OfBabyAnimal(){
        Genotype exampleGenotype = new Genotype();
        Assertions.assertEquals(32, exampleGenotype.mixGenotypes(new Genotype()).getGenotype().size());
    }

    @Test
    public void isFullSetOfGens(){
        Genotype exampleGenotype = new Genotype();
        List<Integer> genotype =  exampleGenotype.mixGenotypes(new Genotype()).getGenotype();
        int[] intArray = new int[8];
        for(Integer gen : genotype){
            intArray[gen]++;
        }
        int minValue = intArray[0];
        for(int i=1;i<intArray.length;i++){
            if(intArray[i] < minValue){
                minValue = intArray[i];
            }
        }
        Assertions.assertNotEquals(0, minValue);
    }


    @Test
    public void isSortedGenotype0ArgumentConstructor(){
        Genotype exampleGenotype = new Genotype();
        boolean isSorted = exampleGenotype.getGenotype().stream().sorted().collect(Collectors.toList()).equals(exampleGenotype.getGenotype());
        Assertions.assertTrue(isSorted);
    }

}
