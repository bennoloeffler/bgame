(ns bgame.engine-test
  (:use tupelo.core)
  (:require [clojure.test :refer :all]
            [bgame.engine :refer :all]
            [bgame.model :as m]
            [puget.printer :refer [cprint]]
            [hashp.core :refer :all])
  (:import (bgame.vec V)))


(def test-model
  {::m/player-hunters [{::m/id        1
                        ::m/position  (V. 1 1)
                        ::m/speed     (V. 1 1)
                        ::m/energy    0.8
                        ::m/invisible 0.7}]
   ::m/auto-hunters   [{::m/id        100
                        ::m/position  (V. 3 3)
                        ::m/speed     (V. 1 1)
                        ::m/energy    0.8
                        ::m/invisible 0.7}]
   ::m/prays          [{::m/id       11
                        ::m/position (V. 2 2)
                        ::m/speed    (V. 2 12)
                        ::m/energy   0.8
                        ::m/poisoned 0.6}
                       {::m/id       12
                        ::m/position (V. 199 99)
                        ::m/speed    (V. 5 5)
                        ::m/energy   0.8
                        ::m/poisoned 0.4}]
   ::m/game-size      (V. 200 100)})

(def test-model-err
  {::m/player-hunters [{::m/id        1
                        ::m/position  (V. 1 1)
                        ::m/speed     (V. 1 1)
                        ;::m/energy    0.8 // here is the bug
                        ::m/invisible 0.7}]
   ::m/auto-hunters   [{::m/id        100
                        ::m/position  (V. 3 3)
                        ::m/speed     (V. 1 1)
                        ::m/energy    0.8
                        ::m/invisible 0.7}]
   ::m/prays          [{::m/id       11
                        ::m/position (V. 2 2)
                        ::m/speed    (V. 12 2)
                        ::m/energy   0.8
                        ::m/poisoned 0.6}
                       {::m/id       12
                        ::m/position (V. 199 99)
                        ::m/speed    (V. 5 5)
                        ::m/energy   0.8
                        ::m/poisoned 0.4}]
   ::m/game-size      (V. 200 100)})


(deftest next-step-test
  (testing "objects moved - but not outside world"
    (let [m-init           test-model
          [m-changed _] (next-step [m-init {}])
          p-before         (m-init ::m/prays)
          p-after          (m-changed ::m/prays)
          not-out-of-world (second p-after)
          ;_            (cprint out-of-world)
          h-before         (m-init ::m/player-hunters)
          h-after          (m-changed ::m/player-hunters)
          ah-before        (m-init ::m/auto-hunters)
          ah-after         (m-changed ::m/auto-hunters)]
      (is (not= ((first p-before) ::m/position) ((first p-after) ::m/position)))
      (is (not= ((first h-before) ::m/position) ((first h-after) ::m/position)))
      (is (not= ((first ah-before) ::m/position) ((first ah-after) ::m/position)))
      (is (< (-> not-out-of-world ::m/position :x) 200)))))


(deftest move-object-test
  (testing "one hunter moved"
    (let [m-init        test-model
          hunter-before (first (m-init ::m/player-hunters))
          hunter-after  (move-object m-init {} hunter-before)]
      (is (not= (hunter-after ::m/position) (hunter-before ::m/position))))))


(deftest distance-test
  (testing "near?"
    (let [m-init      test-model
          p-before    (m-init ::m/prays)
          h-before    (m-init ::m/player-hunters)
          near-pray   (first p-before)
          near-hunter (first h-before)]
      (is (rel= (distance near-pray near-hunter) 1.414 :digits 3)))))

