(ns bgame.engine-test
  (:require [clojure.test :refer :all]
            [bgame.engine :refer :all]
            [bgame.model :refer :all]))

#_(deftest next-step-test
    (testing "all prays moved"
      (let [m-init (initial-example-model example-init)
            [m-changed input] (next-step [m-init {}])]
        (is (not= m-init m-changed)))))


(deftest move-object-test
  (testing "one hunter moved"
    (let [m-init (initial-example-model example-init)
          hunter-before (second (m-init :bgame.model/player-hunters))
          hunter-after (move-object hunter-before)]
      (is (not= hunter-after hunter-before)))))

