(ns bgame.model-test
  (:require [clojure.test :refer :all])
  (:require [bgame.model :as m]
            [clojure.spec.alpha :as s]
            [bgame.engine-test :as et]))

(deftest wrong-model-test
  (is (s/explain-data ::m/world et/test-model-err)))

(deftest right-model-test
  (is (nil? (s/explain-data ::m/world et/test-model))))


#_(deftest initial-model-test
    (let [m (m/initial-example-model {:player-hunters 1
                                      :auto-hunters   2
                                      :prays          100
                                      :game-size      (V. 300 400)})]
      (testing "initial model has right number of players"
        (is (= 1 (-> m
                     ::m/player-hunters
                     count))))
      (testing "initial model has right number of prays"
        (is (= 100 (-> m
                       ::m/prays
                       count))))
      (testing "size right"
        (is (= (V. 300 400) (-> m ::m/game-size))))))

#_(deftest id-range-test
    (is (= '(15 16 17) (take 3 (id-range 15)))))

