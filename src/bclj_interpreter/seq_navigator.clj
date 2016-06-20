(ns bclj-interpreter.seq-navigator)

(defprotocol SequenceNavigator
  (value [this])
  (next-step [this])
  (prev-step [this]))

(defrecord LazySequenceNavigator [lazy-sequence result]
  SequenceNavigator
  (value [this]
    (last result))
  (next-step [this]
    (map->LazySequenceNavigator {:lazy-sequence (next lazy-sequence)
                                 :result        (conj (:result this) (first lazy-sequence))}))
  (prev-step [this]
    (map->LazySequenceNavigator {:lazy-sequence (lazy-seq (cons (last (:result this)) lazy-sequence))
                                 :result        (vec (butlast (:result this)))})))
(defn wrap-lazy-sequence
  [s]
  (map->LazySequenceNavigator {:lazy-sequence s
                               :result        []}))