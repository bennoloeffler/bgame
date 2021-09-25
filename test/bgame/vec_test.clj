(ns bgame.vec-test
  ;(:use tupelo.test)
  (:require [clojure.test :refer :all])
  (:require [bgame.vec :refer :all])
  (:import (bgame.vec V)))

(deftest v-distance-test
  (testing "zero"
    (is (== 0 (v-distance (V. 0 0) (V. 0 0))))))

(deftest v?-test
  (is (not (v? 9)))
  (is (v? (V. 0 0))))

(deftest v-test
  (is (= (V. 16 1) (v 1 2 17 3))))

(deftest v-minus-test
  (is (= (V. -16 -1) (v-minus (v 1 2 17 3))))
  (is (= (V. -17 -3) (v-minus (v 17 3)))))

