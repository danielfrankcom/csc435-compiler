package om.frankc.csc435.compiler.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class Environment<K, V> {

    public Environment() {
        beginScope();
    }

    private final LinkedList<Map<K, V>> mScopes = new LinkedList<>();

    public void beginScope() {
        final Map<K, V> scope = new HashMap<>();
        // Add to start to support iteration as LIFO.
        mScopes.addFirst(scope);
    }

    public void endScope() {
        assert mScopes.size() > 1;
        // Remove from start to support iteration as LIFO.
        mScopes.removeFirst();
    }

    public void put(K key, V value) {
        mScopes.peek().put(key, value);
    }

    public boolean containsKey(K key) {
        return mScopes.peek().containsKey(key);
    }

    public Optional<V> get(K key) {
        for (Map<K, V> scope : mScopes) {
            if (scope.containsKey(key)) {
                return Optional.of(scope.get(key));
            }
        }
        return Optional.empty();
    }

}
