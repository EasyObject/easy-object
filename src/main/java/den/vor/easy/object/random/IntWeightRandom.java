package den.vor.easy.object.random;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntWeightRandom {

    private final CustomRandom random = RandomFactory.getRandom();

    private int[] accumulatedWeights;
    private boolean isSingleValue;
    private final int totalWeight;

    public IntWeightRandom(List<Integer> weights){
        if(weights.isEmpty()){
            throw new IllegalArgumentException("Non-empty collection expected");
        }
        int size = weights.size();
        this.accumulatedWeights = new int[size];
        this.isSingleValue = size == 1;
        accumulatedWeights[0] = weights.get(0);
        for(int i = 1; i < weights.size(); ++i){
            accumulatedWeights[i] = accumulatedWeights[i - 1] + weights.get(i);
        }
        totalWeight = accumulatedWeights[accumulatedWeights.length - 1];
    }

    public IntWeightRandom(IntFunction<Integer> weightFunc, int count){
        this(IntStream.range(1, count + 1).mapToObj(weightFunc).collect(Collectors.toList()));
    }

    public int getNext(){
        if(isSingleValue){
            return 0;
        }
        int generated = random.nextInt(totalWeight);
        if (generated < accumulatedWeights[0]) {
            return 0;
        }
        return process(0, accumulatedWeights.length - 1, generated);
    }

    private int process(int left, int right, int generated) {
        if(left + 1 == right) {
            return right;
        }
        int current = (left + right) >> 1;
        if(accumulatedWeights[current] <= generated) {
            if (accumulatedWeights[current + 1] > generated) {
                return current + 1;
            }
            return process(current, right, generated);
        }
        return process(left, current, generated);
    }
}
