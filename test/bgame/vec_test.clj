(ns bgame.vec-test
  (:require [clojure.test :refer :all])
  (:require [bgame.vec :refer :all])
  (:import (bgame.vec V)))

(deftest v-distance-test
  (testing "zero"
    (is (== 0 (v-distance (V. 0 0) (V. 0 0))))))

(deftest v?-test
  (is (not (v? 9)))
  (is (v? (V. 0 0))))

