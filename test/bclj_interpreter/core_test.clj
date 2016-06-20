(ns bclj-interpreter.core-test
  (:require [clojure.test :refer :all]
            [bclj-interpreter.core :refer :all]))

(deftest test-add-3
  (let [program "+++"
        _ (prn (last (process program)))
        {:keys [array output]} (process program)]
    (is (= [3] array))
    (is (= "" output))))

(defn- make-number
  [n]
  (clojure.string/join (map (fn [_] "+") (range n))))

(deftest test-output-3
  (let [program (str (make-number 51) ".")
        {:keys [array output]} (process program)]
    (is (= [51] array))
    (is (= "3" output))))

(deftest test-subtract
  (let [program "+++--+"
        {:keys [array output]} (process program)]
    (is (= [2] array))
    (is (= "" output))))

(deftest test-move-right
  (let [program "+>++->+++>--"
        {:keys [array output]} (process program)]
    (is (= [1 1 3 -2] array))
    (is (= "" output))))

(deftest test-move-left
  (let [program "+>++-<+++>--"
        {:keys [array output]} (process program)]
    (is (= [4 -1] array))
    (is (= "" output))))

(deftest test-loops-clean-element
  (let [program "++++*[-]*"
        {:keys [array output]} (process program)]
    (is (= [0] array))
    (is (= "40" output))))

(deftest test-loops-x*y
  (let [program "++++[>+++<-]"
        {:keys [array output]} (process program)]
    (is (= [0 12] array))
    (is (= "" output))))

(deftest test-loops-im-awesome
  (let [program "+++++[>+++++[>+++>++>++++>+>++++>++++\n+>++++>++++>+
                 +++>++++>++++<<<<<<<<<<<\n-]>>-->++>+>>->>+++>++>++<
                 <<<<<<<<<<-\n]>>--.>-.>-.>++.>---.>-.>+.>.>+.>-.>+."
        {:keys [array output]} (process program)]
    (prn program)
    (is (= [0 0 73 39 109 32 97 119 101 115 111 109 101] array))
    (is (= "I'm awesome" output))))