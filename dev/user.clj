(ns user
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer (pprint)]
            [puget.printer :refer (cprint)]
            [clojure.repl :refer :all]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer (refresh refresh-all clear)]
            [debux.core :refer :all]
            [hashp.core :refer :all]
            [bels-test-runner :refer [call-current-tests]]
            [bgame.ui-quil :as ui])
  (:use tupelo.core))

; https://github.com/stuartsierra/component.repl

; see https://lambdaisland.com/blog/2018-02-09-reloading-woes

(def system nil)

(defn init
  "Constructs the current development system."
  []
  (alter-var-root #'system
    (constantly {:init true} #_(system/system))))

(defn start
  "Starts the current development system."
  []
  (println "STARTING...")
  (alter-var-root #'system ui/start))

(defn stop
  "Shuts down and destroys the current development system."
  []

  (alter-var-root #'system
    (fn [s] (if s (do (println "STOPPING...")
                      (bgame.ui-quil/stop s))

                  (println "nothing to stop...")))))
(defn go
  "Initializes the current development system and starts it running."
  []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh-all :after 'user/go)) ; refresh does it not with quil...


(defn tests []
   (refresh :after 'bels-test-runner/call-current-tests))


(comment
  (go)
  (stop)
  (start)
  (init)
  (reset)
  (tests)
  (refresh-all)
  (pprint system))

; RUNNING TESTs per WATCHER all the time
; lein bat-test auto

(comment
  (set! *print-length* 100)
  (cprint {:key1 'abc :key2 "qwertz" :key3 1.34})
  (pprint {:key1 'abc :key2 "qwertz" :key3 1.34})
  (/ 1 0)
  (pst)
  (bels-test-runner/call-current-tests)
  (run-bels-tests)
  (->> (all-ns) (filter #(re-find (re-pattern "datomic") (str %))))
  (map #(ns-name %) (all-ns))
  (dbg (->> (all-ns) (shuffle) (take 3) (map ns-name) sort (partition 4)))
  (/ 10 #p (/ (- 12 10) (+ 10 1)))

  (defn s []
    (constantly (do (println "again...") (+ 20 100))))

  ((s) 1 2 3))
