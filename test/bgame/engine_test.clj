(ns bgame.engine-test
  (:require [clojure.test :refer :all]
            [bgame.engine :refer :all]
            [bgame.model :refer :all]))

(deftest next-step-test
  (testing "all monsters moved"
    (let [m-init (initial-model example-init)
          [m-changed input] (next-step [m-init {}])]
      (is (not= m-init m-changed)))))


(deftest move-object-test
  (testing "one monsters moved"
    (let [m-init (initial-model example-init)
          monster-before (second (m-init :pray))
          monster-after (move-object monster-before)]
      (is (not= monster-after monster-after)))))

