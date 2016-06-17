(ns bclj-interpreter.core)

(defn- read-file
  [file-name]
  (slurp file-name))

(defn- update-array
  [array pointer f]
  (let [array (if (get-in array [pointer])
                 array
                 (conj array 0))]
    (update-in array [pointer] f)))

(defn process
  [program]
  (loop [array [0]
         pointer 0
         program program
         loop-start []
         loop-end []
         output []]
    (if-not (seq program)
      {:array array
       :output (clojure.string/join output)}
      (case (str (first program))
        "+" (recur (update-array array pointer inc) pointer (rest program) loop-start loop-end output)
        "-" (recur (update-array array pointer dec) pointer (rest program) loop-start loop-end output)
        ">" (recur (update-array array (inc pointer) identity) (inc pointer) (rest program) loop-start loop-end output)
        "<" (recur (update-array array (dec pointer) identity) (dec pointer) (rest program) loop-start loop-end output)
        "." (recur array pointer (rest program) loop-start loop-end (conj output (char (get-in array [pointer]))))
        "*" (recur array pointer (rest program) loop-start loop-end (conj output (get-in array [pointer])))
        "[" (if (zero? (get-in array [pointer]))
              (recur array pointer (peek loop-end) loop-start (pop loop-end) output)
              (recur array pointer (rest program) (conj loop-start program) loop-start output))
        "]" (recur array pointer (peek loop-start) (pop loop-start) (conj loop-end (rest program)) output)
        (recur array pointer (rest program) loop-start loop-end output)))))

(defn- compute
  [array pointer program loop-start loop-end output]
  (if-not (seq program)
    {:array array
     :output (clojure.string/join output)}
    (case (str (first program))
      "+" (recur (update-array array pointer inc) pointer (rest program) loop-start loop-end output)
      "-" (recur (update-array array pointer dec) pointer (rest program) loop-start loop-end output)
      ">" (recur (update-array array (inc pointer) identity) (inc pointer) (rest program) loop-start loop-end output)
      "<" (recur (update-array array (dec pointer) identity) (dec pointer) (rest program) loop-start loop-end output)
      "." (recur array pointer (rest program) loop-start loop-end (conj output (char (get-in array [pointer]))))
      "*" (recur array pointer (rest program) loop-start loop-end (conj output (get-in array [pointer])))
      "[" (if (zero? (get-in array [pointer]))
            (recur array pointer (peek loop-end) loop-start (pop loop-end) output)
            (recur array pointer (rest program) (conj loop-start program) loop-start output))
      "]" (recur array pointer (peek loop-start) (pop loop-start) (conj loop-end (rest program)) output)
      (recur array pointer (rest program) loop-start loop-end output))))

(defn- generate
  [array pointer program loop-start loop-end output]
  (prn (when (seq program)
         {:array   array
          :pointer pointer
          :output  (clojure.string/join output)}))
  (lazy-seq
    (cons
      (when (seq program)
        {:array   array
         :pointer pointer
         :output  (clojure.string/join output)})
      (case (str (first program))
        "+" (generate (update-array array pointer inc) pointer (rest program) loop-start loop-end output)
        "-" (generate (update-array array pointer dec) pointer (rest program) loop-start loop-end output)
        ">" (generate (update-array array (inc pointer) identity) (inc pointer) (rest program) loop-start loop-end output)
        "<" (generate (update-array array (dec pointer) identity) (dec pointer) (rest program) loop-start loop-end output)
        "." (generate array pointer (rest program) loop-start loop-end (conj output (char (get-in array [pointer]))))
        "*" (generate array pointer (rest program) loop-start loop-end (conj output (get-in array [pointer])))
        "[" (if (zero? (get-in array [pointer]))
              (generate array pointer (peek loop-end) loop-start (pop loop-end) output)
              (generate array pointer (rest program) (conj loop-start program) loop-start output))
        "]" (generate array pointer (peek loop-start) (pop loop-start) (conj loop-end (rest program)) output)
        (generate array pointer (rest program) loop-start loop-end output)))))

(defn process
  [program]
  (let [array [0]
        pointer 0
        program program
        loop-start []
        loop-end []
        output []]
    (generate array pointer program loop-start loop-end output)))

(defn -main
  [& args]
  (let [result (-> (read-file "resources/program1.b")
                   (process)
                   (take-while (partial nil? not)))]
    (prn (:output result))
    (prn)
    (prn "Memory:")
    (prn (:array result))))