(ns bgame.model-test
  (:require [clojure.test :refer :all])
  (:require [bgame.model :refer [initial-model]])
  (:import (bgame.vec V)))

(deftest initial-model-test
  (let [m (initial-model {:monsters 15 :game-size (V. 100 100)})]
    (testing "initial model has right number of monsters"
        (is (= 15 (-> m
                      :monsters
                      count))))
    (testing "size right"
      (is (= (V. 100 100) (-> m :game-size))))))
