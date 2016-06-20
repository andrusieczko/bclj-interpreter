(ns bclj-interpreter.seq-navigator-test
  (:require [clojure.test :refer :all]
            [bclj-interpreter.seq-navigator :refer :all]))

(def counter (atom 0))
(defn generate-seq
  [x]
  (swap! counter inc)
  (lazy-seq
    (cons
      x
      (generate-seq (inc x)))))

(deftest test-navigator
  (let [my-lazy-seq (generate-seq 1)
        seq-wrapped (wrap-lazy-sequence my-lazy-seq)
        seq-after-navigation (-> seq-wrapped
                                 (next-step)
                                 (next-step)
                                 (next-step)
                                 (prev-step)
                                 (prev-step)
                                 (next-step)
                                 (next-step))]
    (is (= 3 (.value seq-after-navigation)))
    (is (= 5 @counter))
    (is (= [4 5 6] (take 3 (:lazy-sequence seq-after-navigation))))
    (is (= 7 @counter))))