package agh.cs.project.basics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genotype {
    private ArrayList<Integer> genotype;
    private final Random random = new Random(42);
    //trzeba jakoś ogarnąć najczęściej pojawiający się genotyp

    public Genotype(){
        ArrayList<Integer> temporaryList = new ArrayList<Integer>();
        //we need to add at least 1 of all gens
        for(int i=0; i < 8; i++){
            temporaryList.add(i);
        }
        for(int i=0; i < 24; i++){
            temporaryList.add(random.nextInt(8));
        }
        //Collections.shuffle(temporaryList, new Random());
        Collections.sort(temporaryList);
        this.genotype = temporaryList;
    }
    public Genotype(ArrayList<Integer> genotype){
        this.genotype = genotype;
    }

    public Genotype mixGenotypes(Genotype parent){
        ArrayList<Integer> temporaryList = new ArrayList<Integer>();
        for(int i=0; i < 8; i++){
            temporaryList.add(i);
        }
        Collections.shuffle(parent.genotype, new Random());
        Collections.shuffle(this.genotype, new Random());
        int firstGroupGensSize = random.nextInt(30);
        int secondGroupGensSize = random.nextInt(31-firstGroupGensSize);
        for(int i = 0; i < firstGroupGensSize; i++){
            temporaryList.add(parent.genotype.get(i));
        }
        for(int i = firstGroupGensSize; i < firstGroupGensSize + secondGroupGensSize; i++){
            temporaryList.add(this.genotype.get(i));
        }
        int x = random.nextInt(2);
        if(x == 0){
            for(int i=firstGroupGensSize + secondGroupGensSize; i < 32; i++){
                temporaryList.add(this.genotype.get(i));
            }
        }
        else{
            for(int i=firstGroupGensSize + secondGroupGensSize; i < 32; i++){
                temporaryList.add(parent.genotype.get(i));
            }
        }
        Collections.sort(parent.genotype);
        Collections.sort(this.genotype);
        Collections.sort(temporaryList);
        return new Genotype(temporaryList);
    }
    public ArrayList<Integer> getGenotype(){
        return genotype;
    }
}
