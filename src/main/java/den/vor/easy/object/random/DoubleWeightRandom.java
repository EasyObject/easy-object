package den.vor.easy.object.random;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DoubleWeightRandom {

    private final CustomRandom random = RandomFactory.getRandom();

    private double[] accumulatedWeights;
    private boolean isSingleValue;
    private final double lastWeight;
    private final int lastElement;

    public DoubleWeightRandom(List<? extends Number> weights){
        if(weights.isEmpty()){
            throw new RuntimeException("Not empty collection expected");
        }
        int size = weights.size();
        this.accumulatedWeights = new double[size];
        this.isSingleValue = size == 1;
        initializeWeights(weights, size);
        lastElement = accumulatedWeights.length - 1;
        lastWeight = accumulatedWeights[lastElement];
    }

    public DoubleWeightRandom(IntFunction<? extends Number> weightFunc, int count){
        this(IntStream.range(1, count + 1).mapToObj(weightFunc).collect(Collectors.toList()));
    }

    public int getNext(){
        if(isSingleValue){
            return 0;
        }

        double generated = random.nextDouble();

        return generated > lastWeight ?
            lastElement :
            process(0, lastElement, generated);
    }

    private int process(int left, int right, double generated) {
        if(left + 1 == right){
            return left;
        }
        int current = (left + right) / 2;
        if(accumulatedWeights[current] < generated){
            if(accumulatedWeights[current + 1] >= generated) {
                return current;
            }
            return process(current, right, generated);
        }
        else{
            return process(left, current, generated);
        }
    }

    private void initializeWeights(List<? extends Number> weights, int size) {
        double sum = weights.get(size - 1).doubleValue();
        for(int i = 1; i < weights.size(); ++i){
            double current = weights.get(i - 1).doubleValue();
            accumulatedWeights[i] = accumulatedWeights[i - 1] + current;
            sum += current;
        }
        for(int i = 0; i < accumulatedWeights.length; ++i){
            accumulatedWeights[i] /= sum;
        }
    }
}
