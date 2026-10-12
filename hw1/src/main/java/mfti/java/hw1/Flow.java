package mfti.java.hw1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class can transform data in several ways.<br>
 * It has two types of operations - terminal and not terminal.<br>
 * In not terminal operations you operations are only stored in Flow, aren't executing.<br>
 * In terminal operations all of you stored operations are applied, but you can't continue your chain of operations.<br>
 * There are three ways of constructing Flow: List, varargs and third one is
 * init value, function for getting next value and end value.<br>
 * @param <T> - type of elements of Flow.
 */
public class Flow<T> {
    private Iterator<T> iterator;
    private List<Function> commands = new ArrayList<>();
    private List<NonTerminalOperationType> commandsType = new ArrayList<>();

    /**
     * Iterator implementation for the type of constructor with
     * init value, function for getting next value and end value.
     */
    private class CustomIteratorImpl<T> implements Iterator<T>{
        private T cur_val;
        private T new_val;
        private Function<T,T> next_fun;
        private T end_val;

        public CustomIteratorImpl(T init_val, Function<T,T> next_fun, T end_val) {
            cur_val = null;
            new_val = init_val;
            this.next_fun = next_fun;
            this.end_val = end_val;
        }

        public boolean hasNext() {
            return !end_val.equals(new_val);
        }

        public T next() {
            cur_val = new_val;
            new_val = next_fun.apply(cur_val);
            return cur_val;
        }
    }

    private enum NonTerminalOperationType {
        MAP, FILTER
    }

    public Flow(List<T> list) {
        iterator = list.iterator();
    }

    public Flow(T... elems) {
        List<T> storeList = new ArrayList<T>(0);
        storeList.addAll(Arrays.asList(elems));
        iterator = storeList.iterator();
    }

    public Flow(T init_val, Function<T,T> next_fun, T end_val) {
        iterator = new CustomIteratorImpl<T>(init_val, next_fun, end_val);
    }

    /**
     * Function maps list of values to another list of values using map function.<br>
     * Result list can have another type of values.<br>
     * Not terminal operation.
     */
    public <R> Flow<R> map(Function<T, R> fun) {
        commands.add(fun);
        commandsType.add(NonTerminalOperationType.MAP);
        return (Flow<R>) this;
    }

    /**
     * Function filters list using predicate function.<br>
     * Not terminal operation.
     */
    public Flow<T> filter(Function<T, Boolean> fun) {
        commands.add(fun);
        commandsType.add(NonTerminalOperationType.FILTER);
        return this;
    }

    /**
     * This function reduces list using function, that accept two values and return one value.<br>
     * Function is applied in a chain to the elements of the list.<br>
     * Return accumulated value.<br>
     * Terminal operation.
     */
    public T reduce(BinaryOperator<T> fun) throws NoSuchElementException {
        List<T> prelast_stage_list = toList();

        T accumulate = prelast_stage_list.getFirst();
        for (int i = 1; i < prelast_stage_list.size(); i++) {
            accumulate = fun.apply(accumulate, prelast_stage_list.get(i));
        }
        return accumulate;
    }

    /**
     * This function transform list to the new collection, using function for getting resulting empty collection
     * and function for inserting elements using gotten collection and next value as arguments.<br>
     * Terminal operation.
     */
    public <U> U collect(Supplier<U> gen_init_fun, BiConsumer<U, T> insert_next_fun){
        List<T> prelast_stage_list = toList();

        U res_collection = gen_init_fun.get();
        for (T elem: prelast_stage_list) {
            insert_next_fun.accept(res_collection, elem);
        }
        return res_collection;
    }

    /**
     * Helper function for applying all stored not terminal operations.<br>
     * Using iterator for getting next value.
     */
    private List<T> toList() {
        List list = new ArrayList();
        while(iterator.hasNext()) {
            Object elem = iterator.next();
            boolean checkElemFlag = true;
            for (int i = 0; i < commands.size(); i++) {
                Function f = commands.get(i);
                NonTerminalOperationType opType = commandsType.get(i);
                switch(opType) {
                    case MAP -> {
                        elem = f.apply(elem);
                    }
                    case FILTER -> {
                        checkElemFlag = (boolean) f.apply(elem);
                    }
                }
                if(!checkElemFlag) {
                    break;
                }

            }
            if (checkElemFlag) {
                list.add(elem);
            }

        }
        return list;
    }
}
