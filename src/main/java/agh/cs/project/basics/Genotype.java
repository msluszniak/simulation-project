package agh.cs.project.basics;

import java.util.*;

public class Genotype {
    private final ArrayList<Integer> genotype;
    private final Random random = new Random();

    public Genotype() {
        ArrayList<Integer> temporaryList = new ArrayList<>();
        //musi być co najmniej po jednym genie każdego rodzaju
        for (int i = 0; i < 8; i++) {
            temporaryList.add(i);
        }
        for (int i = 0; i < 24; i++) {
            temporaryList.add(random.nextInt(8));
        }
        Collections.sort(temporaryList);
        this.genotype = temporaryList;
    }

    public Genotype(ArrayList<Integer> genotype) {
        this.genotype = genotype;
    }

//    public Genotype mixGenotypes(Genotype parent) {
//        ArrayList<Integer> temporaryList = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            temporaryList.add(i);
//        }
//        Collections.shuffle(parent.genotype, new Random());
//        Collections.shuffle(this.genotype, new Random());
//        int firstGroupGensSize = random.nextInt(22);
//        int secondGroupGensSize = random.nextInt(23 - firstGroupGensSize);
//        for (int i = 0; i < firstGroupGensSize; i++) {
//            temporaryList.add(parent.genotype.get(i));
//        }
//        for (int i = firstGroupGensSize; i < firstGroupGensSize + secondGroupGensSize; i++) {
//            temporaryList.add(this.genotype.get(i));
//        }
//        int x = random.nextInt(2);
//        if (x == 0) {
//            for (int i = firstGroupGensSize + secondGroupGensSize; i < 24; i++) {
//                temporaryList.add(this.genotype.get(i));
//            }
//        } else {
//            for (int i = firstGroupGensSize + secondGroupGensSize; i < 24; i++) {
//                temporaryList.add(parent.genotype.get(i));
//            }
//        }
//        Collections.sort(parent.genotype);
//        Collections.sort(this.genotype);
//        Collections.sort(temporaryList);
//        return new Genotype(temporaryList);
//    }

    public Genotype mixGenotypes(Genotype parent){
        ArrayList<Integer> temporaryList = new ArrayList<>();
        int x = random.nextInt(2);
        List<Integer> twoCuts = twoCuts();
        int firstCut = Collections.min(twoCuts);
        int secondCut = Collections.max(twoCuts);
        for(int i = 0; i < firstCut; i++){
            if(x == 0){
                temporaryList.add(this.genotype.get(i));
            }
            else{
                temporaryList.add(parent.getGenotype().get(i));
            }
        }

        for(int i = firstCut; i < secondCut; i++){
            if(x == 0){
                temporaryList.add(parent.getGenotype().get(i));
            }
            else{
                temporaryList.add(this.genotype.get(i));
            }
        }

        for(int i = secondCut; i < 32; i++) {
            if (x == 0) {
                temporaryList.add(this.genotype.get(i));
            } else {
                temporaryList.add(parent.getGenotype().get(i));
            }
        }
        correctGenotypeList(temporaryList);
        Collections.sort(temporaryList);
        return new Genotype(temporaryList);
    }

    public void correctGenotypeList(List<Integer> rawGenotype){
        int[] intArray = new int[8];
        for(Integer gen : rawGenotype){
            intArray[gen]++;
        }
        boolean flag = true;
        while(flag) {
            for (int i = 0; i < 8; i++) {
                if (intArray[i] == 0) {
                    int j = random.nextInt(32);
                    intArray[rawGenotype.get(j)]--;
                    rawGenotype.set(j, i);
                    intArray[i]++;
                }
            }
            if(getMin(intArray) > 0);
            flag = false;
        }
        //return rawGenotype;
    }

    public int getMin(int[] inputArray){
        int minValue = inputArray[0];
        for(int i=1;i<inputArray.length;i++){
            if(inputArray[i] < minValue){
                minValue = inputArray[i];
            }
        }
        return minValue;
    }


    public List<Integer> twoCuts(){
        List<Integer> pair = new ArrayList<>();
        int x = 1 + random.nextInt(31);
        int y = 1 + random.nextInt(31);
        if(x == y) return twoCuts();
        pair.add(x);
        pair.add(y);
        return pair;
    }

    public ArrayList<Integer> getGenotype() {
        return genotype;
    }
}
