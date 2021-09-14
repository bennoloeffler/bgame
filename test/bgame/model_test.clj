(ns bgame.model-test
  (:require [clojure.test :refer :all])
  (:require [bgame.model :refer [initial-example-model]])
  (:import (bgame.vec V)))

(deftest initial-model-test
  (let [m (initial-example-model {:player-hunters 1
                                  :prays 100
                                  :game-size (V. 300 400)})]
    (testing "initial model has right number of players"
        (is (= 1 (-> m
                     :bgame.model/player-hunters
                     count))))
    (testing "initial model has right number of prays"
      (is (= 100 (-> m
                     :bgame.model/prays
                     count))))
    (testing "size right"
      (is (= (V. 300 400) (-> m :game-size))))))
